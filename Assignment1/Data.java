/**
 * This class represents records that will be stored in the HashDictionary 
 * Its methods return the configuration and score data of the object
 * @author Maya Lekhi
 */
public class Data {
	/**
	 * Configuration details string
	 */
    private String configuration;
	/**
	 * Score value
	 */
    private int score;

	/**
	 * Constructor assigns the configuration and score value for players based on input
	 * @param configuration	the player's game configuration details
	 * @param score			the player's score value
	 */
    public Data(String configuration, int score) {
        this.configuration = configuration;
        this.score = score;
    }

	/**
	 * Accessor method to return configuration details
	 * @return configuration details
	 */
    public String getConfiguration() {
        return configuration;
    }

	/**
	 * Accessor method to return player's score
	 * @return score value
	 */
    public int getScore() {
        return score;
    }
}