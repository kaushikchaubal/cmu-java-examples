public class StockHoldingSortTest {
    public static void main(String[] args) {
        StockHolding[] ha = new StockHolding[5];
        ha[0] = new StockHolding("IBM",100);
        ha[1] = new StockHolding("MSFT",200);
        ha[2] = new StockHolding("CSCO",300);
        ha[3] = new StockHolding("DELL",400);
        ha[4] = new StockHolding("EMC",500);
        
        for (int i=0; i<ha.length; i++) {
            for (int j=i+1; j<ha.length; j++) {
                if (ha[i].price * ha[i].shares > ha[j].price * ha[j].shares) {
                	StockHolding temp = ha[i];
                	ha[i] = ha[j];
                	ha[j] = temp;
                }
            }
        }

        for (int i=0; i<ha.length; i++) {
            System.out.printf("%5s, shares=%6d, price=$%7.2f, value=$%,10.2f\n",
            		ha[i].ticker,
                    ha[i].shares,ha[i].price,
                    ha[i].price*ha[i].shares);
        }
    }
}
