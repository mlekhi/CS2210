public class BSTDictionary implements BSTDictionaryADT {
    // declaring tree variable for dictionary
    private BinarySearchTree tree;

    // constructor for the class
    public BSTDictionary() {
        // initializing with an empty binary search tree
        this.tree = new BinarySearchTree();
    }

    // returns record with given key
    public Record get(Key k) {
        // returning null if record is not in dictionary
        if (tree.get(tree.getRoot(), k) == null) {
            return null;
        } else {
            // returning record with the given key from the tree
            return tree.get(tree.getRoot(), k).getRecord();
        }
    }
    
    // adding specified record to the tree; throws dictionary exception record with same key is already there 
    public void put(Record d) throws DictionaryException {
        tree.insert(tree.getRoot(), d);
    }

    // remooving specified record from the tree; throws dictionary exception if not in tree 
    public void remove(Key k) throws DictionaryException {
        tree.remove(tree.getRoot(), k);
    }
 
    // returns successor
    public Record successor(Key k) {
        // returning null if k has no successor
        if (tree.successor(tree.getRoot(), k) == null) {
            return null;
        } else {
            // returning record of found successor
            return tree.successor(tree.getRoot(), k).getRecord();
        }
    }

    // returns predecessor
    public Record predecessor(Key k) {
        // returning null if k has no predecessor
        if (tree.predecessor(tree.getRoot(), k) == null) {
            return null;
        } else {
            // returning record of found predecessor
            return tree.predecessor(tree.getRoot(), k).getRecord();
        }
    }

    // finds record with smallest key in dictionary
    public Record smallest() {
        // returns null if dict empty
        if (tree.smallest(tree.getRoot()) == null) {
            return null;
        } else {
            // returns smallest record
            return tree.smallest(tree.getRoot()).getRecord();
        }
    }

    // finds record with largest key in dictionary
    public Record largest() {
        // returns null if dict empty
        if (tree.largest(tree.getRoot()) == null) {
            return null;
        } else {
            // returns largest record
            return tree.largest(tree.getRoot()).getRecord();
        }
    }
}
