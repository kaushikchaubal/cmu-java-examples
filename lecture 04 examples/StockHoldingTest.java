public class StockHoldingTest {
    public static void main(String[] args) {
        StockHolding h = new StockHolding(args[0],
                                          Integer.parseInt(args[1]));
        System.out.println(h.ticker + ", " +
                           h.shares + ", $" + h.price + " (" +
                           h.name + ")");
    }
}
