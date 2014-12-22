import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StockHoldingSort {
	
    public static void main(String[] args) {
    	List<StockHolding12> list = loadFile(args[0]);
    	
		Collections.sort(list);
		
		for (StockHolding12 h : list) {
			System.out.println(h);
		}
    }
    
    
    public static List<StockHolding12> loadFile(String fileName) {
    	
    	List<StockHolding12> answer = new ArrayList<StockHolding12>();
    	
    	try {
            CSVReader  c = new CSVReader(new FileReader(fileName));

            boolean eof     = false;
            int     lineNum = 0;

            while (!eof) {
                String[] values = c.readCSVLine();
                if (values == null) {
                    eof = true;
                } else {
                    lineNum = lineNum + 1;

                    if (values.length != 2) {
                    	throw new AssertionError("line " + lineNum + ": Incorrect number of values: "+values.length);
                    }

                    String ticker = values[0];
                    
                    int shares;
                    try {
                    	shares = Integer.parseInt(values[1]);
                    } catch (NumberFormatException e) {
                    	throw new AssertionError("line " + lineNum + ": shares not an integer: "+values[1]);
                    }
                    
                    answer.add(new StockHolding12(ticker,shares));
               }
            }

            c.close();
            return answer;
            
    	} catch (IOException e) {
    		throw new AssertionError(e);
    	}
    }
}
