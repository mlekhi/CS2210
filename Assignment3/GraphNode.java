public class GraphNode {
    // declaring variables for the class
    int name;
    boolean mark;

    // constructor
    public GraphNode(int name) {
        this.name = name;
        this.mark = false;
    }    

    // setting mark to the specified input
    public void mark(boolean mark) {
        this.mark = mark; 
    }
    
    // getter method for mark variable
    public boolean isMarked() {
        return mark;
    }

    // getter method for name variable
    public int getName() {
        return name;
    }
}

