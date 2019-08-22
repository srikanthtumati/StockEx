import java.sql.Timestamp;

/**
 * This represents the Stock object
 * @author Mark Chin
 * @version 1.0
 * @since 1.0
 */
public class Stock implements Comparable<Stock>{
	String ticker;
	double shares;
	double buyPrice;
	Timestamp buyTime;

	/**
	 * creates the Stock object
	 * @param ticker the stock ticker
	 * @param shares the number of stocks
	 * @param buyPrice the buy price of the stock
	 * @param buyTime the buy time of the stock
	 */
	public Stock(String ticker, double shares, double buyPrice, Timestamp buyTime) {
		this.ticker = ticker;
		this.shares = shares;
		this.buyPrice = buyPrice;
		this.buyTime = buyTime;
	}


	/**
	 * removes the number of shares the stock has
	 * @param shares the number of shares to remove
	 * @return true if successful and false otherwise
	 */
	public boolean sell(double shares) {
		if(shares > this.shares) {
			return false;
		}
		this.shares -= shares;
		return true;
	}
	public void buy(double shares) {
		this.shares += shares;
	}


	/**
	 * equals method to compare two stock objects
	 * @param o the stock object to compare
	 * @return true if equal and false otherwise
	 */
	public boolean equals(Object o){
		if (this == o){
			return true;
		}
		if(o == null || o.getClass()!= this.getClass())
			return false;
		Stock stock = (Stock) o;
		return (stock.ticker.equals(this.ticker) && stock.shares == this.shares && stock.buyPrice == this.buyPrice && stock.buyTime.equals(this.buyTime) );
	}

	/**
	 * calculates the value of the stock
	 * @return
	 */
	public double calculateValue() {
		return buyPrice*shares;
	}

	/**
	 * compares two stocks
	 * @param stock the stock object to compare
	 * @return -1 if this stock goes first, 1 if the other stock goes first and 0 if equal
	 */
	public int compareTo(Stock stock) {
		//ascending order
		if (this.buyPrice < stock.buyPrice){
			return -1;
		}
		else if (this.buyPrice > stock.buyPrice){
			return 1;
		}
		else{
			return 0;
		}

		//descending order
		//return compareQuantity - this.quantity;

	}

}
