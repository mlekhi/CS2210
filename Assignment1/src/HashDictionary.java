import java.util.LinkedList;

/**
 * This class represents a hash dictionary for the configuration information. 
 * @author Maya Lekhi
 */
public class HashDictionary implements DictionaryADT {
	/**
	 * An array of linked lists (hash table) to store configuration data
	 */
    private LinkedList<Data>[] table;
	/**
	 * Size of the hash dictionary
	 */
    private int size;

	/**
	 * Constructor creates a hash dictionary with a specified size
	 * @param size	Size of the hash dictionary
	 */
    public HashDictionary(int size) {
        this.size = size;
        table = new LinkedList[size];
        // initializing the array with empty linked lists to create a hash table
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<Data>();
        }
    }

	/**
	 * Adds a record configuration if it is unique to the dictionary
	 * @param record	Data to be added to the dictionary
	 * @return Returns 0 if there is no collision produced by the record, returns 1 if not.
	 * @throws DictionaryException if record.getConfiguration() is already in the dictionary
	 */
    public int put(Data record) throws DictionaryException {
    	// finds hash index for the configuration
        int index = this.hash(record.getConfiguration());
        // uses the index to retrieve the linked list at that spot in the array 
        LinkedList<Data> bucket = table[index];

        // iterating through the bucket 
        for (int i = 0; i < bucket.size(); i++) {
        	// checking if the record matches existing records
            Data existingRecord = bucket.get(i);
            if (existingRecord.getConfiguration().equals(record.getConfiguration())) {
            	// throwing a dictionary exception if a duplicate configuration exists
                throw new DictionaryException(); 
            }
        }

        // adding the record to the linked list if a duplicate does not  exist
        bucket.add(record);
        // returning values according to whether or not adding the record produces a collision
        if (bucket.size() > 1) {
            return 1;
        } else {
            return 0;
        }
    }

	/**
	 * Method removes the record with the given config from the dictionary
	 * @param config	configuration information of the record to remove
	 * @throws DictionaryException if no record in the hash table stores config
	 */
    public void remove(String config) throws DictionaryException {
    	// finding index of the given configuration
        int index = hash(config);
        // getting the linked list at the index of the configuration
        LinkedList<Data> bucket = table[index];

        // iterating through the bucket 
        for (int i = 0; i < bucket.size(); i++) {
        	// checking if the record matches what we are looking for
            Data record = bucket.get(i);
            if (record.getConfiguration().equals(config)) {
                // removing the record 
                bucket.remove(record);
                return;
            }
        }
        // throwing a dictionary exception if the record is not found
        throw new DictionaryException();
    }

	/**
	 * Method returning the score in the configuration record
	 * @param config	configuration information of the record to retrieve 
	 * @return score stored in the record with key config, or -1 if not there
	 */
    public int get(String config) {
    	// finding the index of the given configuration
        int index = hash(config);
        // getting the linked list at the index of the configuration
        LinkedList<Data> bucket = table[index];
        
        // iterating through the bucket 
        for (int i = 0; i < bucket.size(); i++) {
        	// checking if the record matches what we are looking for
            Data record = bucket.get(i);
            // returning the information associated with the specific key
            if (record.getConfiguration().equals(config)) {
                return record.getScore();
            }
        }

        // returning -1 if the key is not found
        return -1;
    }

	/**
	 * Returns the number of dictionary objects
	 * @return number of Data objects stored in the dictionary
	 */
    public int numRecords() {
    	// initializing an integer to hold the record count number
        int count = 0;
        // iterating through the linked list
        for (int i = 0; i < table.length; i++) {
            LinkedList<Data> bucket = table[i];
            // incrementing the count accordingly
            count += bucket.size();
        }
        // returning the count of the records
        return count;
    }

	/**
	 * Helper function to hash the key strings
	 * @param key	key string containing configuration information	
	 * @return hash value of configuration details
	 */
    private int hash(String key) {
    	// initializing the hash value at zero
        int hashValue = 0;
        // choosing a prime multiplier constant
        int a = 11;
        // iterating through the key values
        for (int i = 0; i < key.length(); i++) {
        	// updating the hash value using multiplication and mod for each input character
            hashValue = (hashValue * a + key.charAt(i)) % size;
        }
        return hashValue;
    }
}
