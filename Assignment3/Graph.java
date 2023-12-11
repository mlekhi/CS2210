import java.util.*;

// graph class
public class Graph implements GraphADT {
    // creating an adjacency list
    private Map<GraphNode, List<GraphEdge>> adjacency;
    // map to store nodes of graph
    private Map<Integer, GraphNode> nodes;

    // creating empty graph, n nodes
    public Graph(int n) {
        // intializing both hash maps
        adjacency = new HashMap<>();
        nodes = new HashMap<>();

        // creating n nodes
        for (int i = 0; i < n; i++) {
            // making a new node with the given number
            GraphNode newNode = new GraphNode(i);
            // putting the node in the adjacency list and map for nodes
            adjacency.put(newNode, new ArrayList<>());
            nodes.put(i, newNode);
        }
    }

    // adds edge to graph connecting u and v
    public void insertEdge(GraphNode u, GraphNode v, int type, String label) throws GraphException {
        // checking if adjacency list has u and v nodes
        if (!adjacency.containsKey(v) || !adjacency.containsKey(u)) {
            throw new GraphException("node(s) not found");
        }
        // creating new edge with given information connecting u and v
        GraphEdge edge = new GraphEdge(u, v, type, label);

        // adding the edge connection to both u and v's adjacency list
        adjacency.get(u).add(edge);
        adjacency.get(v).add(edge);
    }

    // getting node with given name
    public GraphNode getNode(int name) throws GraphException {
        // checking if node has a key that matches name
        if (nodes.containsKey(name)) {
            return nodes.get(name);
        } else {
            // throwing graph exception if node with the given name is not found
            throw new GraphException("node not found");
        }
    }

    // getting all edges incident of u 
    public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
        // checking if node's key is stored in adjacency list
        if (adjacency.containsKey(u)) {
            // getting the iterator for u's incident edges
            return adjacency.get(u).iterator();
        } else {
            // throwing graph exception if node is not found in adjacency list
            throw new GraphException("node not found");
        }
    }

    // getting the edge connecting u and v
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
        // checking if adjacency list has u and v nodes
        if (!adjacency.containsKey(v) || !adjacency.containsKey(u)) {
            throw new GraphException("node(s) not found");
        }

        // finding and looping through all edges incident to u
        List<GraphEdge> incidents = adjacency.get(u);
        for (int i = 0; i < incidents.size(); i++) {
            // getting the edges one by one
            GraphEdge edge = incidents.get(i);
            // finding u's edge with an endpoint connecting to v & returning it
            if (edge.firstEndpoint().equals(v) || edge.secondEndpoint().equals(v)) {
                return edge;
            }
        }

        // throwing an exception if there is no edge between u and v
        throw new GraphException("edge not found");
    }

    // checks if nodes u and v are adjacent
    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
        // checking if adjacency list has u and v nodes
        if (!adjacency.containsKey(v) || !adjacency.containsKey(u)) {
            throw new GraphException("node(s) not found");
        }

        try {
            if (getEdge(u, v) != null) {
                return true;
            } else {
                return false;
            }
        } catch (GraphException e) {
            return false;
        }
    }
}
