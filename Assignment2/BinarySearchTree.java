public class BinarySearchTree {
    // declaring root variable for BST
    private BSTNode root;

    // constructor for the class that creates a leaf node as the root of the tree
    public BinarySearchTree() {
        this.root = new BSTNode(null);
    }

    // returns the root node of this binary search tree
    public BSTNode getRoot() {
        return root;
    }

    // returns the node storing the given key
    public BSTNode get(BSTNode r, Key k) {
        // checking if r is a leaf
        if (r.isLeaf()) {
            // returning r because it's the only node
            return r;
        }
        // returning r if its key matches with k
        if (r.getRecord().getKey().compareTo(k) == 0) {
            return r;
        // moving to left child if k is smaller than root r
        } else if (r.getRecord().getKey().compareTo(k) > 0) {
            // recursively searching the left child of r if it has a left child
            if (r.getLeftChild() != null) {
                return get(r.getLeftChild(), k);
            } else {
                return r;
            } 
        // moving to right child if k is larger than root r
        } else {
            // recursively searching the right child of r if it has a left child
            if (r.getRightChild() != null) {
                return get(r.getRightChild(), k);
            } else {
                return r;
            } 
        }
    }

    // adds the record to the binary search tree with root r
    public void insert(BSTNode r, Record d) throws DictionaryException {
        // creating new root with record if root is null
        if (root == null) {
            root = new BSTNode(d);
            root.setLeftChild(new BSTNode(null));
            root.setRightChild(new BSTNode(null));
            return;
        }
        // replacing r with record if it's a leaf
        if (r.isLeaf() || r == null) {
            r.setRecord(d);
            r.setLeftChild(new BSTNode(null));
            r.setRightChild(new BSTNode(null));
            return;
        }

        // comparing d's key to r
        int compare = d.getKey().compareTo(r.getRecord().getKey());

        // throwing exception if theyre the same; duplicate records
        if (compare == 0) {
            throw new DictionaryException("Duplicate record in tree");
        } else if (compare > 0) {
            // inserting directly into right subtree if right child is a leaf
            if (r.getRightChild().isLeaf()) {
                r.setRightChild(new BSTNode(d));
                r.getRightChild().setRightChild(new BSTNode(null));
                r.getRightChild().setLeftChild(new BSTNode(null));
                r.getRightChild().setParent(r);
            } else {
                // recursively running insert to insert into the right subtree
                insert(r.getRightChild(), d);
            }
        } else {
            // inserting directly into left subtree if right child is a leaf
            if (r.getLeftChild().isLeaf()) {
                r.setLeftChild(new BSTNode(d));
                r.getLeftChild().setRightChild(new BSTNode(null));
                r.getLeftChild().setLeftChild(new BSTNode(null));
                r.getLeftChild().setParent(r);
            } else {
                // recursively running insert to insert into the left subtree
                insert(r.getLeftChild(), d);
            }
        }
    }

    // deletes node with the given key from the tree with root r
    public void remove(BSTNode r, Key k) throws DictionaryException {
        // checking tree contents and returning exception if tree is empty
        if (r == null || r.isLeaf()) {
            throw new DictionaryException("Empty tree");
        }

        // using finder helper function to get the exact location of r
        BSTNode node = finder(r, k);

        // throwing exception if finder returned null indicating key could not be found
        if (node == null || node.isLeaf()) {
            throw new DictionaryException("Key not found in the tree");
        }

        // checking if node has two children
        if (!node.getLeftChild().isLeaf() && !node.getRightChild().isLeaf()) {
            // finding successor
            BSTNode successor = smallest(node.getRightChild());

            // switching records of node and successor
            Record temporary = node.getRecord();
            node.setRecord(successor.getRecord());
            successor.setRecord(temporary);

            // checking left and right children; finding if non leaf child
            BSTNode child = successor.getLeftChild().isLeaf() ? successor.getRightChild() : successor.getLeftChild();

            // removing successor fully from the tree; updating child and parent relationships
            if (successor.getParent() == null) {
                    this.root = child;
            } else if (successor == successor.getParent().getLeftChild()) {
                successor.getParent().setLeftChild(child);
            } else {
                successor.getParent().setRightChild(child);
            }
            if (child != null && !child.isLeaf()) {
                child.setParent(successor.getParent());
            }
        }
        // case where node has no or one children
        else {
            // finding if any child with no leaf
            BSTNode child = node.getLeftChild().isLeaf() ? node.getRightChild() : node.getLeftChild();

            // removing node fully from the tree; updating child and parent relationships
            if (node.getParent() == null) {
                this.root = child;
            } else if (node == node.getParent().getLeftChild()) {
                node.getParent().setLeftChild(child);
            } else {
                node.getParent().setRightChild(child);
            }
            if (child != null && !child.isLeaf()) {
                child.setParent(node.getParent());
            }
        }
    }

    // helper function to find key k in tree
    private BSTNode finder(BSTNode current, Key k) {
        // cycling through tree
        while (current != null && !current.isLeaf() && current.getRecord().getKey().compareTo(k) != 0) {
            // using comparison to guide movement in tree
            if (k.compareTo(current.getRecord().getKey()) > 0) {
                current = current.getRightChild();
            } else {
                current = current.getLeftChild();
            }
        }
        return current;
    }

    // returns the node storing the successor of the given key in the tree with root r
    public BSTNode successor(BSTNode r, Key k) {
        // finds node with given key in tree
        BSTNode p = get(r, k);
        
        // checks if node is not a leaf, nor is its right child
        if (!p.isLeaf() && !p.getRightChild().isLeaf()) {
            // finding the smallest node among the nodes right children
            // returns null if the successor does not exist
            return smallest(p.getRightChild());
        } else {
            // moving one step up the tree
            p = p.getParent();
            // running a loop to move another step up until node with key larger than given is found
            while (p != null && p.getRecord().getKey().compareTo(k) <= 0) {
                p = p.getParent();
            }
            // returning suceessor of key
            return p;
        }
    }
    
    // returns the node storing the predecessor of the given key in the tree with root r
    public BSTNode predecessor(BSTNode r, Key k) {
        // finds node with given key in tree
        BSTNode p = get(r, k);
        
        // checks if node is not a leaf, nor is its left child
        if (!p.isLeaf() && !p.getLeftChild().isLeaf()) {
            // finding the largest node among the nodes left children
            // returns null if the successor does not exist
            return largest(p.getLeftChild());
        } else {
            // moving one step up the tree
            p = p.getParent();
            // running a loop to move another step up until node with key smaller than given is found
            while (p != null && p.getRecord().getKey().compareTo(k) >= 0) {
                p = p.getParent();
            }
            // returning predecessor of key
            return p;
        }
    }
    
    // returns the node with the smallest key in tree with root r
    public BSTNode smallest(BSTNode r) {
        // cycling through left subtree for smallest
        BSTNode node = r;
        while (!node.getLeftChild().isLeaf() && node != null) {
            node = node.getLeftChild();
        }
        return node;
    }
    
    // returns the node with the largest key in tree with root r
    public BSTNode largest(BSTNode r) {
        // cycling through right subtree for smallest
        BSTNode node = r;
        while (!node.getRightChild().isLeaf() && node != null) {
            node = node.getRightChild();
        }
        return node;
    }
}