//
// StockQuote Class Example
//
// Author: Jeffrey Eppinger (eppinger@cmu.edu)
// Date:     February 3, 2000
// Modified: January 8, 2002
//

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class StockQuote implements Runnable  {
    private String  ticker;       // The ticker for the stock
    private String  price;        // The stock price
    private String  change;       // Today's delta in the stock price
    private String  date;         // The date of the stock quote
    private String  time;         // The time of the stock quote
    private String  name;         // The name of the stock
    private boolean keepRunning = true;  // Support for threadding (see below)

    private URL url;          // The URL used to get the stock quote

    private static int quoteCount = 0;   // Count of the number of quotes we've done


    //
    // An internal routine that reads the URL and stuff the data into
    // instance variables.
    //

    private void readURLData() throws IOException {
        InputStream is = url.openStream();
        InputStreamReader isr = new InputStreamReader(is);
        CSVReader c = new CSVReader(isr);
        String[] values = c.readCSVLine();
        if (values.length < 5) throw new IOException("Format error reading line, only " +
                                                     values.length +
                                                     " items!");
        ticker = values[0];
        price  = values[1];
        change = values[4];
        date   = values[2];
        time   = values[3];
        name   = values[5];

        quoteCount = quoteCount + 1;

        c.close();
        isr.close();
        is.close();
    }



    // Internal constants used to construct URL string in constructor

    private final String urlHeader = "http://quote.yahoo.com/d/quotes.csv?s=";
    private final String urlTrailer = "&f=sl1d1t1c1nohgv&e=.csv";


    //
    // The constructor...takes a ticker
    //

    public StockQuote(String ticker) {
        String urlString = urlHeader + ticker + urlTrailer;

        try {
            url = new java.net.URL(urlString);
        } catch (IOException e) {
            System.out.println("IOException" + e);
            System.exit(0);
        }
    }


    //
    // An instance method to provide a current quote for the stock, as a string
    // The string include the ticker, price change and quote date/time
    //

    public String currentQuote(){
        try {
            readURLData();
        } catch (IOException e) {
            System.out.println("IOException" + e);
            System.exit(0);
        }

        String answer = ticker + ' ' + price + ' ' +
           change + " at " + date + ' ' + time;

        return answer;
    }


    //
    // This method returns a current price for the ticker
    //

    public float getPrice() {
        try {
            readURLData();
        } catch (IOException e) {
            System.out.println("IOException");
            System.exit(0);
        }
        return Float.parseFloat(price);
    }


    //
    // This method returns a name for the ticker
    //

    public String getName() {
        if (name == null) {
            try {
                readURLData();
            } catch (IOException e) {
                System.out.println("IOException");
                System.exit(0);
            }
        }

        return name;
    }



    //
    // This method returns a change in the price for the ticker
    //

    public String getChange() {
        if (name == null) {
            try {
                readURLData();
            } catch (IOException e) {
                System.out.println("IOException");
                System.exit(0);
            }
        }

        return change;
    }



    //
    // Accessor method for quoteCount
    //

    public static int getQuoteCount() {
        return quoteCount;
    }


    //
    // Support for printing current quotes from background thread
    //

    public void run() {
        while (keepRunning) {
            String quote = currentQuote();
            if (keepRunning) {
                System.out.println(quote);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {};
        }
    }


    public void stopRunning() {
        keepRunning = false;
    }

    private static class CSVReader {
    	private BufferedReader br;

        public CSVReader(Reader in) {
        	if (in instanceof BufferedReader) {
        		br = (BufferedReader) in;
        	} else {
        		br = new BufferedReader(in);
        	}
        }
        
        public void close() throws IOException {
        	br.close();
        }

        public String[] readCSVLine() throws IOException {
            String x = br.readLine();

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
}