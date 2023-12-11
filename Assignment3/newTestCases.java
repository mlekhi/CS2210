import java.util.*;

/* Program for testing the Graph methods. */
public class newTestCases {

    public static void main(String[] args) {

        // ... Existing test cases from your code ...

        Graph G;
        GraphNode u, v;
        GraphEdge uv;
        Iterator<GraphEdge> neighbours;
        int numNodes = 14;
        GraphNode[] V = new GraphNode[numNodes];
        boolean failed;

        // Test 7: Test Edge Insertion with Duplicate Edges
        try {
            G = new Graph(2);
            u = new GraphNode(0);
            v = new GraphNode(1);
            G.insertEdge(u, v, 1, "door");
            G.insertEdge(u, v, 1, "door"); // Inserting a duplicate edge
            System.out.println("    Test 7 failed: Duplicate edges should not be allowed.");
        } catch (GraphException e) {
            System.out.println("    Test 7 passed");
        }

        // Test 8: Test Edge Insertion with Self-loop
        try {
            G = new Graph(1);
            u = new GraphNode(0);
            G.insertEdge(u, u, 1, "loop"); // Self-loop
            System.out.println("    Test 8 failed: Self-loop edges should not be allowed.");
        } catch (GraphException e) {
            System.out.println("    Test 8 passed");
        }

        // Test 9: Test `getNode` for Negative Node Indices
        try {
            G = new Graph(1);
            u = G.getNode(-1); // Negative index
            System.out.println("    Test 9 failed: Method getNode must throw an exception for negative indices.");
        } catch (GraphException e) {
            System.out.println("    Test 9 passed");
        }

        // Test 10: Test `areAdjacent` for Non-existing Nodes
        try {
            G = new Graph(1);
            u = new GraphNode(0);
            v = new GraphNode(100); // Non-existing node
            boolean adjacent = G.areAdjacent(u, v);
            System.out.println("    Test 10 failed: Method areAdjacent must throw an exception for non-existing nodes.");
        } catch (GraphException e) {
            System.out.println("    Test 10 passed");
        }

        // Test 11: Test `incidentEdges` on a Node with No Edges
        try {
            G = new Graph(2);
            u = G.getNode(1); // Get an existing node with no edges
            neighbours = G.incidentEdges(u);
            if (neighbours.hasNext()) {
                System.out.println("    Test 11 failed: Node with no edges should have an empty iterator.");
            } else {
                System.out.println("    Test 11 passed");
            }
        } catch (GraphException e) {
            System.out.println("    Test 11 failed: Unexpected exception.");
        }
        // ... Rest of the existing test cases ...

    }
}
