public class BSTNode {
    // declaring variables for the right & left children, and parent
    private BSTNode rightChild;
    private BSTNode leftChild;
    private BSTNode parent;
    private Record item;

    // class constructor
    public BSTNode(Record item) {
        // setting to given item
        this.item = item;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    // returns the Record object stored in this node
    public Record getRecord() {
        return item;
    }

    // stores the given record in this node
    public void setRecord(Record d) {
        this.item = d;
    }

    // returns left child
    public BSTNode getLeftChild() {
        return leftChild;
    }

    // returns right child
    public BSTNode getRightChild() {
        return rightChild;
    }

    // returns parent node
    public BSTNode getParent() {
        return parent;
    }

    // sets left child to specified value
    public void setLeftChild(BSTNode u) {
        this.leftChild = u;
    }

    // sets right child to specified value
    public void setRightChild(BSTNode u) {
        this.rightChild = u;
    }

    // sets parent to specified value
    public void setParent(BSTNode u) {
        this.parent = u;
    }
    
    // returns true if this node is a leaf; false otherwise
    public boolean isLeaf() {
        // node is a leaf if both children are null
        return rightChild == null && leftChild == null;
    }
}
