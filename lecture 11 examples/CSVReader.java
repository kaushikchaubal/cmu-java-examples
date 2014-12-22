import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * A simplified version of CSV Reader
 *
 * Subclass of a BufferedReader to handle a character stream
 * that consists of comma separated values (CSVs)
 *
 * Provides an additional instance method, readCSVLine(), that
 * parses lines into substrings.  The substrings are separated by
 * comma in the original input stream.  The readCSVLine() method
 * returns an array of references to Strings.  The Strings are
 * the values from the line that were separated by commas.  If
 * a value was surrounded by quotes, the quotes are removed.
 *
 * Limitations: Spaces before or after the commas are not removed.
 * In the first and last quote are removed from a value.  
 * Embedding commas in a quoted value is not handled properly.
 * (In this case, the commas will separate the values and the
 * quotes will not be removed from the ends of those values.
 *
 * Author: Jeffrey Eppinger (jle@cs.cmu.com)
 * Date:   October 2, 2007
 */
public class CSVReader extends BufferedReader {

	/**
	 * Initializes the class
	 * @param in the reader from which to read CSV lines
	 */
    public CSVReader(Reader in) {
        super(in);
    }

    /**
     * This is the only additional method.  It uses readLine from
     * the superclass to get a line but returns the comma separated
     * values as in an array of strings. 
     * @return an array of Strings containing the values At the end of the file, readCSVLine returns null (just as
     *readLine does).
     */
    public String[] readCSVLine() throws IOException {

        // Get a line by calling the superclass's readLine method
        String line = super.readLine();

        // If we're at the end of the file, readLine returns null
        // so we return null.
        if (line == null) return null;

        // Count up the number of commas
	  int commaCount = 0;
	  for (int i=0; i<line.length(); i++) {
            if (line.charAt(i) == ',') commaCount = commaCount + 1;
        }

        // Allocate an array of the necessary size to return the strings
        String[] values = new String[commaCount+1];


        // In a loop, set beginIndex and endIndex to the start and end
        // positions of each argment and then use the substring method
        // to create strings for each of the comma separate values

        // Start beginIndex at the beginning of the String, position 0
        int beginIndex = 0;

        for (int i=0; i<commaCount; i++) {

            // set endIndex to the position of the (next) comma
            int endIndex = line.indexOf(',',beginIndex);

            // if the argument begins and ends with quotes, remove them

            if (line.charAt(beginIndex) == '"' && line.charAt(endIndex-1) == '"') {

                // If we made it here, we have quotes around our string.
                // Add/substract one from the start/end of the args
                // to substring to get the value.  (See else comment
                // below for details on how this works.)

                values[i] = line.substring(beginIndex+1,endIndex-1);

            } else {

                // If we name it here, we don't have quotes around
                // our string.  Take the substring of this line
                // from the beginIndex to the endIndex.  The substring
                // method called on a String will return the portion
                // of the String starting with the beginIndex and up
                // to but not including the endIndex.

                values[i] = line.substring(beginIndex,endIndex);
            }

            // Set beginIndex to the position character after the
            // comma.  (Remember, endIndex was set to the position
            // of the comma.)
            beginIndex = endIndex + 1;
        }

        // handle the value that's after the last comma
        if (line.charAt(beginIndex) == '"' && line.charAt(line.length()-1) == '"') {
            values[commaCount] = line.substring(beginIndex+1,line.length()-1);
        } else {
            values[commaCount] = line.substring(beginIndex,line.length());
        }

        return values;
    }
}
