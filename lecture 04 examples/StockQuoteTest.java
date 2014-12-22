public class StockQuoteTest {
  public static void main(String args[]) {
    StockQuote sq = new StockQuote(args[0]);
    System.out.println("      " +
            sq.getName() + "   " +
            sq.currentQuote() );
  }
}
