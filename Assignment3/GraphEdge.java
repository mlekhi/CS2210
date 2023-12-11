public class GraphEdge {
    // declaring variables for the class
    GraphNode firstEndpoint;
    GraphNode secondEndpoint;
    int type;
    String label;

    // constructor
    public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
        this.firstEndpoint = u;
        this.secondEndpoint = v;
        this.type = type;
        this.label = label;
    }

    // getter method for first endpoint
    public GraphNode firstEndpoint() {
        return firstEndpoint;
    }

    // getter method for second endpoint
    public GraphNode secondEndpoint() {
        return secondEndpoint;
    }

    // getter method for type
    public int getType() {
        return type;
    }

    // setter method to change type
    public void setType(int newType) {
        this.type = newType;
    }

    // getter method for label of edge
    public String getLabel() {
        return label;
    }

    // setter method to change label
    public void setLabel(String newLabel) {
        this.label = newLabel;
    }
}