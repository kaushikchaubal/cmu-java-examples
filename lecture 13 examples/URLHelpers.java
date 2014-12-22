import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.*;

public class URLHelpers {

    private URLHelpers() {
        // A private constructor prevents this class from accidently being
        // instantiated.  (All the methods are static.)
    }


    // 
    /**
     * A method that reads the first megabyte from a given URL and returns it as a String.  If there is an error reading the URL, the String returned
    // starts with "Error: " followed by a description of the error.
     */
    public static String urlToString(String urlString) throws IOException {
        URL url = new URL(urlString);
        InputStream is = null;
        try {
            is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            StringBuffer b = new StringBuffer();
            boolean eof = false;

            while (!eof && b.length() < 1000000) {
                String line = br.readLine();
                if (line == null) {
                    eof = true;
                } else {
                    b.append(line);
                    b.append('\n');
                }
            }

            br.close();
            return b.toString();
        } catch (IOException e) {
        	if (is != null) try { is.close(); } catch (IOException e2) { /* Ignore */ }
        	throw e;
        }
    }



    static Pattern hrefPat      = Pattern.compile("href[ \\s]*=[ \\s]*",Pattern.CASE_INSENSITIVE);
    static Pattern quotePat     = Pattern.compile("\"");
    static Pattern singQuotePat = Pattern.compile("'");
    static Pattern separatorPat = Pattern.compile("[^a-zA-Z0-9/:.~%?&=#]");


    // A method that will return a list of the URLs referenced (after "href=")
    // in the htmlDocument.  You must provide the urlString so that relative
    // hrefs can be resolved to absolute URLs.

    public static ArrayList<String> extractURLs(String urlString, String htmlDoc) {
        ArrayList<String> answer = new ArrayList<String>();
        String root = urlRoot(urlString);
        String current = urlCurrentDirectory(urlString);

        String a[] = hrefPat.split(htmlDoc);
        for (int i=1; i<a.length; i++) {
            String newURL;
            if (a[i].charAt(0) == '"') {
                String a2[] = quotePat.split(a[i],3);
                newURL = a2[1];
            } else if (a[i].charAt(0) == '\'') {
                String a2[] = singQuotePat.split(a[i],3);
                newURL = a2[1];
            } else {
                String a2[] = separatorPat.split(a[i],2);
                newURL = a2[0];
            }
            String lcURL = newURL.toLowerCase();
            if (!lcURL.startsWith("file:") && !lcURL.startsWith("mailto") && !lcURL.startsWith("javascript:")) {
                if (newURL.length() > 0 && newURL.charAt(0) == '/') {
                    newURL = root + newURL;
                } else if (!lcURL.startsWith("http://")) {
                    newURL = current + '/' + newURL;
                }
                if (newURL.length() > 0) answer.add(newURL);
            }
        }

        return answer;
    }

    private static String urlRoot(String u) {
        int slash = u.indexOf('/',7);
        if (slash == -1) return u;
        return u.substring(0,slash);
    }

    private static String urlCurrentDirectory(String u) {
        int slash = u.lastIndexOf('/');
        if (slash == u.length()-1) return u.substring(0,u.length()-1);
        if (slash < 7) return u;
        return u.substring(0,slash);
    }
    
}
