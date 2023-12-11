public class Key {
    // declaring key label value and type value
    private String label;
    private int type;

    // constructor which initializes a new Key object with the specified parameters
    public Key(String theLabel, int theType) {
        this.type = theType;
        // label must be converted to lowercase before being stored in instance variable
        this.label = theLabel.toLowerCase();
    }

    // returns string instance stored in label
    public String getLabel() {
        return label;
    }

    // returns int instance stored in type
    public int getType() {
        return type;
    }

    // compares key object with other specified key object
    public int compareTo(Key k) {
        // returning 0 if key labels and types match
        if (this.label.compareTo(k.getLabel()) == 0 && this.type == k.getType()) {
            return 0;
        // case 1: returning -1 if specified key object's label comparison with this key object is smaller than 0
        // case 2: returning -1 if label comparison matches BUT int type of key object is smaller than that of the specified key
        } else if (this.label.compareTo(k.getLabel()) < 0 || (this.label.compareTo(k.getLabel()) == 0 && this.type < k.getType())) {
            return -1;
        // retuning 1 otherwise
        } else {
            return 1; 
        } 
    }
}