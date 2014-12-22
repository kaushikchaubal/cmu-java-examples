public class StockHoldingTest2 {
    public static void main(String[] args) {
        StockHolding[] ha = new StockHolding[5];
        ha[0] = new StockHolding("IBM",100);
        ha[1] = new StockHolding("MSFT",200);
        ha[2] = new StockHolding("CSCO",300);
        ha[3] = new StockHolding("DELL",400);
        ha[4] = new StockHolding("EMC",500);

        for (int i=0; i<ha.length; i++) {
            System.out.println(ha[i].ticker + ", " +
                    ha[i].shares + ", $" + ha[i].price + " (" +
                    ha[i].name + ")");
        }
    }
}
