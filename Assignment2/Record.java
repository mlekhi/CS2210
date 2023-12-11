public class Record {
    // declaring variables for record key and data to be stored in record
    private Key theKey;
    private String data;

    // constructor which initializes a new Record object with the specified parameters
    public Record(Key k, String theData) {
        // initializes record key with given key
        this.theKey = k;
        // intializes record data with given data
        this.data = theData;
    }

    // returns record key
    public Key getKey() {
        return theKey;
    }

    // returns data of record
    public String getDataItem() {
        return data;
    }
}
