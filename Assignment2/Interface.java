// import statements for functionality
import java.io.File;
import java.util.Scanner;

public class Interface {
    // main class
    public static void main(String[] args) {
        // check for if the user provides an argument with a file for the dictionary
        if (args.length == 0) {
            // printing error message and stopping if there is no input file
            System.out.println("The file name must be given as an argument");
            return;
        }

        // creating dictionary variable to store dictionary content to be extracted from file
        BSTDictionary dictionary = new BSTDictionary();
        // creating variables to store info to go in bst nodes in dictionary
        String l;
        String label;
        String data;
        int type;

        // declaring the scanner variable 
        Scanner scanner = null;

        // try loop to go through the file contents 
        try {
            // initializing the scanner to read the filename specified 
            scanner = new Scanner(new File(args[0]));

            // creating a while loop that will run for the entirety of the file's contents
            while (scanner.hasNextLine()) {
                // setting the label as the first line of the command
                label = scanner.nextLine();
            
                // checking if there is another line after the label
                if (scanner.hasNextLine()) {
                    // assigning the entire line content to l to later be analyzed
                    l = scanner.nextLine();
        
                    if (!l.isEmpty()) {
                        // case where data is a translation of label to French
                        if (l.charAt(0) == '/') {
                            // creating a substring without the first character '/'
                            data = l.substring(1);
                            // assigning type to 2 to indicate translation
                            type = 2;
                        // case where data is the name of a sound file
                        } else if (l.charAt(0) == '-') {
                            // creating a substring without the first character '-'
                            data = l.substring(1);
                            // assigning type to 3 to indicate sound
                            type = 3;
                        // case where data is the name of a music file
                        } else if (l.charAt(0) == '+') {
                            // creating a substring without the first character '+'
                            data = l.substring(1);
                            // assigning type to 4 to indicate music
                            type = 4;
                        // case where data is the name of a voice file
                        } else if (l.charAt(0) == '*') {
                            // creating a substring without the first character '*'
                            data = l.substring(1);
                            // assigning type to 5 to indicate voice
                            type = 5;
                        // case where data is the name of an image file
                        } else if (l.endsWith(".jpg")) {
                            data = l;
                            // assigning type to 6 to indicate image
                            type = 6;
                        // case where data is the name of an animated image file
                        } else if (l.endsWith(".gif")) {
                            data = l;
                            // assigning type to 7 to indicate animated image
                            type = 7;
                        // case where data is the URL of a webpage
                        } else if (l.endsWith(".html")) {
                            data = l;
                            // assigning type to 8 to indicate URL
                            type = 8;
                        // case where data is a string containing a definition of label
                        } else {
                            data = l;
                            // assigning type to 1 to indicate definition
                            type = 1;
                        }

                        // adding record containing key with given information to the dictionary
                        dictionary.put(new Record(new Key(label, type), data));
                    }
                }
            }
        // catching for any issues with file reading
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
        // closing scanner
            scanner.close();
        }

        // while loop to collect and process user commands
        while (true) {
            // using stringreader as specified in the assignment
            StringReader keyboard = new StringReader();
            // collecting user input
            String line = keyboard.read("Enter next command: ");

            if (line.equals("exit")) {
                // exiting the loop and ending the main program
                break;
            } else if (line.startsWith("define ")) {
                // running line starting with define command through define helper function
                define(line, dictionary);
            } else if (line.startsWith("translate ")) {
                // running line starting with translate command through translate helper function
                translate(line, dictionary);
            } else if (line.startsWith("sound ")) {
                // running line starting with sound command through audio helper function
                audio(line, dictionary, 3);
            } else if (line.startsWith("play ")) {
                // running line starting with play command through audio helper function
                audio(line, dictionary, 4);
            } else if (line.startsWith("say ")) {
                // running line starting with say command through audio helper function
                audio(line, dictionary, 5);
            } else if (line.startsWith("show ")) {
                // running line starting with show command through view helper function
                view(line, dictionary, 6);
            } else if (line.startsWith("animate ")) {
                // running line starting with animate command through view helper function
                view(line, dictionary, 7);
            } else if (line.startsWith("browse ")) {
                // running line starting with browse command through browse helper function
                browse(line, dictionary);
            } else if (line.startsWith("delete ")) {
                // running line starting with delete command through delete helper function
                delete(line, dictionary);
            } else if (line.startsWith("add ")) {
                // running line starting with add command through add helper function
                add(line, dictionary);
            } else if (line.startsWith("list ")) {
                // running word following list command through list helper function
                list(line.substring(5), dictionary);
            } else if (line.startsWith("first")) {
                // running first helper function
                first(dictionary);
            } else if (line.startsWith("last")) {
                // running last helper function
                last(dictionary);
            } else {
                // notifying users that their command was not valid
                System.out.println("Invalid command.");
            }
        }
    }

    // define helper function
    private static void define(String command, BSTDictionary dictionary) {
        // splitting line up into words
        String[] parts = command.split(" ");

        // checking if the command has # of input words
        if (parts.length == 2) {
            // isolating word following the command
            String word = parts[1];
            // using get function to search for key with type 1 (for define) and given word
            Record search = dictionary.get(new Key(word, 1));

            // checking if search was conclusive or not
            if (search == null) {
                // printing that record isn't in the dictionary
                System.out.println("The word " + word + " is not in the ordered dictionary");
            } else {
                // printing data attribute of matching record
                System.out.println(search.getDataItem());
            }
        } else {
            System.out.println("Incorrect command input format");
        }
    }

    // translate helper function
    private static void translate(String command, BSTDictionary dictionary) {
        // splitting line up into words
        String[] parts = command.split(" ");
    
        // checking if the command has # of input words
        if (parts.length == 2) {
            // isolating word following the command
            String word = parts[1];
            // checking the dictionary for a record with type 2 and the given word
            Record search = dictionary.get(new Key(word, 2));
    
            // checking if there is a translation
            if (search == null) {
                System.out.println("There is no definition for the word " + word);
            } else {
                // printing the data attribute of matching record
                System.out.println(search.getDataItem());
            }
        } else {
            System.out.println("Incorrect command input format");
        }
    }
    
    // helper function for all commands requiring audio
    private static void audio(String command, BSTDictionary dictionary, int type) {
        // splitting line up into words
        String[] parts = command.split(" ");

        // checking if the command has # of input words
        if (parts.length == 2) {
            // isolating word following the command
            String word = parts[1];
            // checking the dictionary for a record matching the type that the command specified and the given word
            // type = (3 for sound, 4 for play, 5 for say)
            Record search = dictionary.get(new Key(word, type));

            // checking if the word maps to a sound file
            if (search != null) {
                // trying to use the soundplayer to play the file specified in the data attribute
                try {
                    SoundPlayer player = new SoundPlayer();
                    player.play(search.getDataItem());
                } catch (MultimediaException e) {
                    // printing exception if there is an error
                    System.out.println(e.getMessage());
                }
            } else {
                // printing the correct error message depending on type if file can't be found
                if (type == 3) {
                    System.out.println("There is no sound file for " + word);
                } else if (type == 4) {
                    System.out.println("There is no music file for " + word);
                } else if (type == 5) {
                    System.out.println("There is no voice file for " + word);
                } else {
                    System.out.println("There is no file for " + word);
                }
            }
        } else {
            System.out.println("Incorrect command input format");
        }
    }
    
    // helper function for all commands requiring audio
    private static void view(String command, BSTDictionary dictionary, int type) {
        // splitting line up into words
        String[] parts = command.split(" ");

        // checking if the command has # of input words
        if (parts.length == 2) {
            // isolating word following the command
            String word = parts[1];
            // checking the dictionary for a record matching the type that the command specified and the given word
            // type = (6 for show, 7 for animate)
            Record search = dictionary.get(new Key(word, type));

            // checking if the word maps to an image file
            if (search != null) {
                // trying to use the pictureviewer to view the file specified in the data attribute
                try {
                    PictureViewer viewer = new PictureViewer();
                    viewer.show(search.getDataItem());
                } catch (MultimediaException e) {
                    // printing exception if there is an error
                    System.out.println(e.getMessage());
                }
            } else {
                // printing the correct error message depending on type if file can't be found
                if (type == 6) {
                    System.out.println("There is no image file for " + word);
                } else if (type == 7) {
                    System.out.println("There is no animated image file for " + word);
                } else {
                    System.out.println("There is no file for " + word);
                }
            }
        } else {
            System.out.println("Incorrect command input format");
        }
    }

    // browse helper function
    private static void browse(String command, BSTDictionary dictionary) {
        // splitting line up into words
        String[] parts = command.split(" ");
    
        // checking if the command has # of input words
        if (parts.length == 2) {
            // isolating word following the command
            String word = parts[1];
            // checking the dictionary for a record with type 8 and the given word
            Record search = dictionary.get(new Key(word, 8));
    
            // checking if the word maps to a URL
            if (search != null) {
                // trying to use the showHTML to view the URL specified in the data attribute 
                try {
                    ShowHTML urlView = new ShowHTML();
                    urlView.show(search.getDataItem());
                } catch (Exception e) {
                    // printing exception if there's an error
                    System.out.println(e.getMessage());
                }
            } else {
                // printing error message if search comes up inconclusive
                System.out.println("There is no webpage called " + word);
            }
        } else {
            System.out.println("Incorrect command input format");
        }
    }    

    // delete helper command
    public static void delete(String command, BSTDictionary dictionary) {
        // splitting line up into words
        String[] parts = command.split(" ");

        // checking if the command has # of input words
        if (parts.length == 3) {
            // isolating word w
            String w = parts[1];
            // isolating (and converting to int) type k
            int k = Integer.parseInt(parts[2]);

            // trying to use remove function in dictionary based on key with given word and type
            try {
                dictionary.remove(new Key(w, k));
                System.out.println("Deleted.");
            } catch (DictionaryException e) {
                // printing error if there is no matching record to the given key
                System.out.println("No record in the ordered dictionary has key (" + w + "," + k + ").");
            }
        } else {
            System.out.println("Incorrect command input format");
        }
    }

    // add helper command
    public static void add(String command, BSTDictionary dictionary) {
        // splitting line up into words
        String[] parts = command.split(" ");

        // checking if the command has # of input words
        if (parts.length == 4) {
            // isolating word w
            String w = parts[1];
            // isolating (and converting to int) type t
            int t = Integer.parseInt(parts[2]);
            // isolating data c
            String c = parts[3];

            // trying to use add function in dictionary based on record with given data + with key with given word and type
            try {
                dictionary.put(new Record(new Key(w, t), c));
                System.out.println("Added!");
            } catch (DictionaryException e) {
                // printing error if there already exists record with duplicate key information
                System.out.println("A record with the given key (" + w + "," + t + ") is already in the ordered dictionary.");
            }
        } else {
            System.out.println("Incorrect command input format");
        }
    }

    // list helper command
    private static void list(String prefix, BSTDictionary dictionary) {
        StringBuilder list = new StringBuilder();
        boolean firstMatch = true;
        boolean match = false;
        // finding smallest record in dictionary
        Record smallest = dictionary.smallest();

        while (smallest != null) {
            // iterating over dictionary starting from smallest entry matching the prefix
            if (smallest.getKey().getLabel().startsWith(prefix)) {
                // adding a comma between entries 
                if (!firstMatch) {
                    list.append(", ");
                }
                match = true;
                firstMatch = false;
                // adding the label of the match to the list of matching results
                list.append(smallest.getKey().getLabel());
            } else if (match) {
                // breaking the loop if label doesn't start with prefix and words that have the prefix were found
                break;
            }
            // updating the smallest variable to be its successor
            smallest = dictionary.successor(smallest.getKey());
        }

        // printing resulting list of matches if there are matches
        if (match) {
            System.out.println(list);
        } else {
            // printing error message if there are no matches
            System.out.println("No label attributes in the ordered dictionary start with prefix " + prefix + ".");
        }
    }
    
    // first helper command
    private static void first(BSTDictionary dictionary) {
        // checking if dictionary has a smallest
        if (dictionary.smallest() == null) {
            System.out.println("Ordered dictionary is empty.");
        } else {
            // printing dictionary's smallest label, type, and data
            System.out.println(dictionary.smallest().getKey().getLabel() + ", " + dictionary.smallest().getKey().getType() + ", " + dictionary.smallest().getDataItem());
        }
    }

    // last helper command
    private static void last(BSTDictionary dictionary) {
        // checking if dictionary has a largest
        if (dictionary.largest() == null) {
            System.out.println("Ordered dictionary is empty.");
        } else {
            // printing dictionary's largest label, type, and data
            System.out.println(dictionary.largest().getKey().getLabel() + ", " + dictionary.largest().getKey().getType() + ", " + dictionary.largest().getDataItem());
        }
    }
}