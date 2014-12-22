public class StockHolding12 implements Comparable<StockHolding12> {
    private String ticker;
    private int    shares;
    private String name;
    private float  price;

    // This is the constructor method
    public StockHolding12(String ticker, int shares) {
    	this.ticker = ticker;
    	this.shares = shares;

    	StockQuote sq = new StockQuote(ticker);
    	price = sq.getPrice();
    	name  = sq.getName();
    }

	public String getTicker() {
		return ticker;
	}

	public int getShares() {
		return shares;
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public String toString() {
		return String.format("%1$-5s   %2$,10d   $%3$,15.2f    ( %4$-17s )", ticker, shares, shares*price, name);
	}
	
	public int compareTo(StockHolding12 other) {
		return ticker.compareTo(other.ticker);
	}
}
