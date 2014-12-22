import java.net.URL;
import java.util.HashMap;
import java.io.*;

public class CachingStockQuote {
    private static int quoteCount = 0;   // Count of the number of quotes we've done
    private static HashMap<String,QuoteElement> quoteTable = new HashMap<String,QuoteElement>();
    private static boolean updatingQuotes = false;
    private static Object  updateLock = new Object();
    private static URL url;              // The URL used to get the stock quote

    private static void readURLData() throws IOException {
        if (updatingQuotes) {
           synchronized (updateLock) {
               // wait until update is done
           }
           // now return...no point in updating again!
           return;
        }

        synchronized (updateLock) {
            updatingQuotes = true;

            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            CSVReader c = new CSVReader(isr);

            String values[] = c.readCSVLine();
            while (values != null) {
                if (values.length < 10) throw new IOException("Format error reading line, only " +
                                                              values.length + " items!");

                QuoteElement qe  = new QuoteElement();
                qe.ticker        = values[0];
                qe.price         = values[1];
                qe.change        = values[4];
                qe.date          = values[2];
                qe.time          = values[3];
                qe.name          = values[5];
                qe.dividends     = values[6];
                qe.earningsTTM   = values[7];
                qe.estEarnCurYr  = values[8];
                qe.estEarnNextYr = values[9];

                synchronized(quoteTable) {
                	quoteTable.put(qe.ticker,qe);
                }

                quoteCount = quoteCount + 1;

                values = c.readCSVLine();
            }

            c.close();
            updatingQuotes = false;
        }
     }

    // Internal constants used to construct URL string in constructor

    private final String urlHeader = "http://quote.yahoo.com/d/quotes.csv?s=";
    private final String urlTrailer = "&f=sl1d1t1c1ndee7e8&e=.csv";

    // Vector used to keep the list of tickers
    private static StringBuffer tickerList = new StringBuffer();

    private String ticker = null;        // Instance variable ticker for the stock..used to search table.

    /**
     * Constructor that takes the ticker for which we want quotes
     * @param ticker the companies ticker (one to five letters in length)
     */
    public CachingStockQuote(String ticker) {
        this.ticker = ticker.toUpperCase();

        synchronized (updateLock) {
            if (tickerList.length() > 0) tickerList.append('+');
            tickerList.append(this.ticker);
            String urlString = urlHeader + tickerList.toString() + urlTrailer;

            try {
                url = new java.net.URL(urlString);
            } catch (IOException e) {
            	e.printStackTrace();
                System.exit(0);
            }
        }
    }

    /**
     * Refreshes the quotes for all tickers (for all StockQuote objects
     */
    public static void refresh() {
        try {
            readURLData();
        } catch (IOException e) {
        	e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Get a current quote for our ticker
     * @return a current quote formatted in a String suitable for printing or null if not available
     */
    public String currentQuote(){
        refresh();
        QuoteElement qe = lookup();
        if (qe == null) return null;

        String answer = qe.ticker + ' ' + qe.price + ' ' +
           qe.change + " at " + qe.date + ' ' + qe.time;

        return answer;
    }

    /**
     * Gets our ticker, the ticker that was passed in to the constructor when creating this instance
     * @return our ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Gets the name of the company for our ticker.
     * @return the company name or null if it's not available
     */
    public String getName() {
        QuoteElement qe = lookup();
        if (qe == null) return null;
        return qe.name;
    }

    /**
     * Gets the current price of the ticker for the trading date of this quote or Double.NaN it not available
     */
    public double getPrice() {
        QuoteElement qe = lookup();
        if (qe == null) return Double.NaN;

        try {
            return Double.parseDouble(qe.price);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    /**
     * Gets the change in price for our ticker.
     * @return the current price change for the trading date of this quote or Double.NaN if not available
     */
    public double getChange() {
        QuoteElement qe = lookup();
        if (qe == null) return Double.NaN;

        try {
            return Double.parseDouble(qe.change);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }
 
   /**
     * Gets the date this quote was received
     * @return the date formatted like this: 9/21/2001
     */
    public String getDate() {
        QuoteElement qe = lookup();
        if (qe == null) return null;
        return qe.date;
    }

    /**
     * Gets the dividends per share for our ticker
     * @return dividends per share or Double.NaN if they are not available
     */
    public double getDividends() {
        QuoteElement qe = lookup();
        if (qe == null) return Double.NaN;

        try {
            if (qe.dividends.equals("N/A")) return 0.0d;
            return Double.parseDouble(qe.dividends);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

   /**
     * Returns the time this quote was retrieved
     * @return the time formatted like this: 9:51am
     */
    public String getTime() {
        QuoteElement qe = lookup();
        if (qe == null) return null;
        return qe.time;
    }

   /**
     * Gets earnings per share for the trailing 12 months
     * @return earnings per share or Double.NaN if they are not available
     */
    public double getEarningsTTM() {
        QuoteElement qe = lookup();
        if (qe == null) return Double.NaN;

        try {
            return Double.parseDouble(qe.earningsTTM);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    /**
     * Gets estimated earnings per share for the company's current fiscal year
     * @return estimated earnings per share or Double.NaN if they are not available
     */
    public double getEstEarnCurYr() {
        QuoteElement qe = lookup();
        if (qe == null) return Double.NaN;

        try {
            return Double.parseDouble(qe.estEarnCurYr);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    /**
     * Returns estimated earnings per share for the company's next fiscal year
     * @return estimated earnings per share or Double.NaN if they are not available
     */
    public double getEstEarnNextYr() {
        QuoteElement qe = lookup();
        if (qe == null) return Double.NaN;

        try {
            return Double.parseDouble(qe.estEarnNextYr);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }


    //
    // Accessor method for quoteCount
    //

    public static int getQuoteCount() {
        return quoteCount;
    }

    private QuoteElement lookup() {
    	synchronized (quoteTable) {
    		QuoteElement answer = quoteTable.get(ticker);
    		if (answer != null) return answer;
    	}
        
    	// Couldn't find a QuoteElement for our ticket
    	///...let's refresh and try once more.
        refresh();
        synchronized (quoteTable) {
        	QuoteElement answer = quoteTable.get(ticker);
        	return answer;
        }
    }

    private static class QuoteElement {
        String  ticker = null;     // The ticker for the stock
        String  price  = null;     // The stock price
        String  change = null;     // Today's delta in the stock price
        String  date   = null;     // The date of the stock quote
        String  time   = null;     // The time of the stock quote
        String  name   = null;     // The name of the stock
        String  dividends = null;  // Annual dividend's per share
        String  earningsTTM   = null;  // Annual earnings per share (trailing 12 months)
        String  estEarnCurYr  = null;  // Estimated earnings per share for the current year
        String  estEarnNextYr = null;  // Estimated earnings per share for the next year
    }
    
    private static class CSVReader extends BufferedReader {
        public CSVReader(Reader in) {
            super(in);
        }

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
}
