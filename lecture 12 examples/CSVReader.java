import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * An updated version of CSV Reader
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
 * Improvements in new version:
 *    - Correct handling of embedded commas in quotes
 *    - Removal of spaces before and after commas
 *
 * Author: Jeffrey Eppinger (jle@cs.cmu.com)
 * Date:   September 30, 2010
 */
public class CSVReader extends BufferedReader {

    public CSVReader(Reader in) {
        super(in);
    }

    /**
     * This is the only additional method.  It uses readLine from
     * the superclass to get a line but returns the comma separated
     * values as in an array of strings. 
     * @return an array of Strings containing the values At the end of the file, readCSVLine returns null (just as
     * readLine does).
     */
    public String[] readCSVLine() throws IOException {
        String x = super.readLine();

        if (x == null) return null;
        
        StringBuilder line  = new StringBuilder(x.trim());
        List<String> answer = new ArrayList<String>();
        
        while (line.length() > 0) {
            if (line.charAt(0) == '"') {
            	int pos = line.substring(1).indexOf("\",");
            	if (pos == -1) {
            		if (line.charAt(line.length()-1) == '"') {
            			answer.add(line.substring(1,line.length()-2));
            		} else {
                		answer.add(line.substring(1));
            		}
            		break;
            	}
            	answer.add(line.substring(1,pos+1));
            	line.delete(0,pos+3);
            } else {
            	int pos = line.indexOf(",");
            	if (pos == -1) {
            		answer.add(line.toString().trim());
            		break;
            	}
            	answer.add(line.substring(0,pos).trim());
            	line.delete(0,pos+1);
            }
        }

        return answer.toArray(new String[answer.size()]);
    }
}
