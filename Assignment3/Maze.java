import java.io.*;
import java.util.*;

// maze class
public class Maze {
    // graph for maze representation

    private Graph mazeGraph;
    // nodes to save entrance and exit values
    private GraphNode entrance;
    private GraphNode exit;

    // variables to save the first four inputs
    private int coins;
    private int width;
    private int length;
    private int scale;

    // constructor to read input file & build graph
    public Maze(String inputFile) throws MazeException {
        // setting up a reader to read the input file
        BufferedReader reader = null;

        try {
            // initializing reader with input file
            reader = new BufferedReader(new FileReader(inputFile));

            // saving scale factor, width, length, coins
            this.scale = Integer.parseInt(reader.readLine());
            this.width = Integer.parseInt(reader.readLine());
            this.length = Integer.parseInt(reader.readLine());
            this.coins = Integer.parseInt(reader.readLine());

            // initializing a character array to take in input that will set up the maze properties
            char[][] charArray = new char[length * 2][width * 2];

            // reading each character input and adding it to the character array
            for (int row = 0; row < (length * 2 - 1); row++) {
                String line = reader.readLine();
                for (int col = 0; col < (width * 2 - 1); col++) {
                    charArray[row][col] = line.charAt(col);
                }
            }

            // using build maze helper function
            buildMaze(charArray);

            // trying to close the reader
            reader.close();

        } catch (Exception e) {
            // throwing maze exception if there was an issue building the maze
            throw new MazeException(e.getMessage());
        }
    }

    // helper function to read input from char array and build maze
    private void buildMaze(char[][] charArray) throws Exception {
        try {
            // creating a new graph for the maze
            mazeGraph = new Graph(length * width);

            // looping through character array
            for (int row = 0; row < (length * 2 - 1); row++) {
                for (int col = 0; col < (width * 2 - 1); col++) {
                    // checking if there is an entrance denoted
                    if (charArray[row][col] == 's') {
                        // adding entrance node to graph and setting entrance variable
                        GraphNode entranceNode = mazeGraph.getNode((row / 2) * width + (col / 2));
                        this.entrance = entranceNode;
                    // checking if there is an exit denoted
                    } else if (charArray[row][col] == 'x') {
                        // adding exit node to graph and setting exit variable
                        GraphNode exitNode = mazeGraph.getNode((row / 2) * width + (col / 2));
                        this.exit = exitNode;
                    // checking if there is a corridor denoted
                    } else if (charArray[row][col] == 'c') {
                        // if input text row is even, setting edge to be connected horizontally as it is between rooms
                        if (row % 2 == 0) {
                            addMazeEdge(row, col, 0, "corridor", true);
                        } else {
                            addMazeEdge(row, col, 0, "corridor", false);
                        }
                    // checking if there is a door denoted
                    } else if (Character.isDigit(charArray[row][col])) {
                        // if input text row is even, setting edge to be connected horizontally as it is between rooms
                        if (row % 2 == 0) {
                            addMazeEdge(row, col, Character.getNumericValue(charArray[row][col]), "door", true);
                        } else {
                            addMazeEdge(row, col, Character.getNumericValue(charArray[row][col]), "door", false);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // catching any exceptions
            throw new Exception(e.getMessage());
        }
    }

    // helper function to establish maze edges 
    private void addMazeEdge(int row, int col, int points, String label, boolean horizontal) throws GraphException {
        try {
            // setting the edge between the current node and one right of it if horizontal edge must be made
            if (horizontal) {
                GraphNode currentNode = mazeGraph.getNode((row / 2) * width + (col / 2));
                GraphNode nextNode = mazeGraph.getNode((row/ 2) * width + ((col + 1) / 2));
                mazeGraph.insertEdge(currentNode, nextNode, points, label);
            // setting the edge between the current node and one below it if vertical edge must be made
            } else {
                GraphNode currentNode = mazeGraph.getNode((row / 2) * width + (col / 2));
                GraphNode nextNode = mazeGraph.getNode(((row + 1)/ 2) * width + (col / 2));
                mazeGraph.insertEdge(currentNode, nextNode, points, label);
            }
        } catch(GraphException e) {
            // throwing graph exception if insert edge goes wrong
            throw new GraphException(e.getMessage());
        }
    }

    // returning reference to graph object representing maze
    public Graph getGraph() throws MazeException {
        if (mazeGraph == null) {
            // throwing exception if graph is null
            throw new MazeException("graph is null");
        }

        // returning graph
        return mazeGraph;
    }

    // finding nodes of path from entrance to exit
    public Iterator solve() {
        // making stack to store the nodes that have been visited in the current path
        Stack<GraphNode> currentPath = new Stack<>();

        // trying the depth first search helper function
        try {
            if (dfs(this.entrance, this.exit, currentPath)) {
                return currentPath.iterator();
            }
        } catch (GraphException e) {
            // catching graph exceptions from helper function
            System.out.print("incident edges cannot be found");
        }
    
        // no path is found
        return null;
    }

    // depth first search helper function
    private boolean dfs(GraphNode current, GraphNode exit, Stack<GraphNode> currentPath) throws GraphException {
        // checking if node has already been visited
        if (current.isMarked()) {
            return false;
        }    
        
        // adding current node to the path and marking it as visited
        currentPath.push(current);
        current.mark(true);

        // checking if the exit has been reached
        if (current.equals(exit)) {
            return true;
        }

        // getting all incident edges for current node
        Iterator<GraphEdge> edges = mazeGraph.incidentEdges(current);

        // iterating over edges
        while (edges != null && edges.hasNext()) {
            GraphEdge edge = edges.next();

            // finding the neighbour node to the current node
            GraphNode neighbour;
            // finding which endpoint the neighbour lies at 
            if (edge.secondEndpoint().equals(current)) {
                neighbour = edge.firstEndpoint();
            } else {
                neighbour = edge.secondEndpoint();
            }

            // checking if the neighbour hasn't been visited and we have enough money to pass the edge to the neighbour
            if (!neighbour.isMarked() && passability(edge)) {
                // removing coins based on door cost
                int originalCoins = this.coins;
                this.coins = this.coins - edge.getType();

                // recursively calling dfs helper
                if (dfs(neighbour, exit, currentPath)) {
                    return true;
                } else {
                    // resetting coins value if we backtrack
                    this.coins = originalCoins;
                }
            }
        }
        
        // backtracking if current path is taking us to a roadblock
        currentPath.pop();
        current.mark(false);
        return false;
    }

    // helper function to check if edges can be passed with current coins
    private boolean passability(GraphEdge edge) {
        // getting type/coin cost of edge
        int edgeType = edge.getType();

        // checking if there's enough coins to pass the edge
        if (edgeType >= 0 && this.coins >= edgeType) {
            return true;
        }

        // returning false if not enough coins
        return false;
    }
}
