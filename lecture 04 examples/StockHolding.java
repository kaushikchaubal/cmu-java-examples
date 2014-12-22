public class StockHolding {
    String ticker;
    int    shares;
    String name;
    float  price;

    // This is the constructor method
    public StockHolding(String newTicker, int newShares) {
    	ticker = newTicker;
    	shares = newShares;

    	StockQuote sq = new StockQuote(ticker);
    	price = sq.getPrice();
    	name  = sq.getName();
    }
}
