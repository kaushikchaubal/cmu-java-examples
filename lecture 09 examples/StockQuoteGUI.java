import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class StockQuoteGUI implements ActionListener, Runnable {


    // Instance variables that reference GUI objects used when action occurs

    private JButton      addButton;
    private JTextField   tickerField;
    private JTextArea    quoteArea;


    // Instance variables to hold ticker list
    ArrayList<CachingStockQuote> quoteList = new ArrayList<CachingStockQuote>();


    // Instance variables used internally to format output

    private DecimalFormat df = new DecimalFormat("#,###,##0.00");

    // The main() method calls the constructor to set up and display the GUI.

    public static void main(String[] args) {
        new StockQuoteGUI();
    }

    public void run() {
        while (true) {
        	synchronized (quoteList) {
        		if (quoteList.size() > 0) {
        			quoteArea.setText(refreshQuotes());
        		}
        	}
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                // do nothing...we woke up early...keep going
            }
        }
    }

    // Constructor that sets up and displays Swing GUI

    public StockQuoteGUI() {
        JFrame frame = new JFrame("A Swing Application to Obtain and Display a List of StockQuotes");
        frame.setSize(730,530);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The panel in which everything goes
        JPanel pane = new JPanel();

        // The fonts
        Font textFont = new Font("Courier New", Font.BOLD, 14);
        Font buttonFont = new Font("Helvetica", Font.BOLD, 12);
        Font labelFont = new Font("Helvetica", Font.BOLD, 14);

        JLabel label;

        // Label for Ticker
        label = new JLabel("New Ticker:");
        label.setFont(labelFont);
        pane.add(label);

        // TextField for Ticker
        tickerField = new JTextField(10);
        tickerField.setFont(textFont);
        tickerField.addActionListener(this);
        pane.add(tickerField);

        // Add Button
        addButton = new JButton("Add Ticker to List");
        addButton.setFont(buttonFont);
        addButton.addActionListener(this);
        pane.add(addButton);

        // Text area for detail and errors
        quoteArea = new JTextArea(25,80);
        //quoteArea.setEditable(false);
        quoteArea.setFont(textFont);

        // Scroll pane for detail area
        JScrollPane scroller = new JScrollPane(quoteArea);
        pane.add(scroller);
        
        frame.setContentPane(pane);
        frame.setVisible(true);

        Thread refreshThread = new Thread(this);
        refreshThread.start();
    }


    // actionPerformed
    //
    // The method gets called when a button is clicked

    public void actionPerformed(ActionEvent e) {
        if (tickerField.getText().length() > 0) {
        	synchronized (quoteList) {
	            if (quoteList.size() == 0) quoteArea.setText(header);
	            CachingStockQuote sq = new CachingStockQuote(tickerField.getText());
	            quoteList.add(sq);
        	}
            quoteArea.append(tickerField.getText().toUpperCase()+'\n');
            tickerField.setText(null);
        }
    }


    // addNewTicker
    //
    // Adds the ticker to the list and then updates the display

    private String formatOneQuote(CachingStockQuote sq) {
        StringBuffer answer = new StringBuffer();

        appendAndPad(answer,sq.getTicker(),8);

        appendAndPad(answer,sq.getName(),17);
        padAndAppend(answer,df.format(sq.getPrice()),9);
        padAndAppend(answer,df.format(sq.getChange()),9);
        padAndAppend(answer,sq.getTime(),9);
        padAndAppend(answer,df.format(sq.getDividends()/sq.getPrice()*100),9);
        answer.append('%');
        padAndAppend(answer,df.format((3*sq.getDividends()+sq.getEarningsTTM()+
                                         sq.getEstEarnCurYr()+sq.getEstEarnNextYr())/sq.getPrice()/3*100),9);
        answer.append('%');

        answer.append('\n');
        return answer.toString();
    }


    // padAndAppend
    //
    // Method to add spaces to a string buffer and then adds the given string.
    // It's used for alignment in the detail area.
    // It takes appends the necessary number of spaces so that after the 
    // string is appended, the total number of characters (padding + string)
    // will take up the width given (unless the string was longer than that
    // to start with).

    private void padAndAppend(StringBuffer b, String s, int width) {
        for (int i=s.length(); i<width; i++) {
           b.append(' ');
        }
        b.append(s);
    }


    private void appendAndPad(StringBuffer b, String s, int width) {
        b.append(s);
        for (int i=s.length(); i<width; i++) {
           b.append(' ');
        }
    }



    private final String header = "Ticker        Name           Price    Change   Time      Yield    Factor\n";



    // refreshQuotes
    //
    // Method that runs through the tickers on the quoteList and gets
    // new quotes for them.  It returns a formatted string using the
    // header String variable for the first line and calls oneQuote to
    // produce the subsequent lines for each ticker on the quoteList.

    private String refreshQuotes() {
        StringBuffer answer = new StringBuffer(header);
        
        CachingStockQuote.refresh();

        for (int i=0; i<quoteList.size(); i++) {
            answer.append(formatOneQuote((CachingStockQuote)quoteList.get(i)));
        }

        return answer.toString();
    }

}