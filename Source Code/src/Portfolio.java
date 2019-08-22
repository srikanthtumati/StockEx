import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
/**
*Portfolio handles a list of Stocks which represent a client's current shares of their stocks
 * @author Mark Chin
 * @version 1.0
 * @since 1.0
*
*/
public class Portfolio {
	public ArrayList<Stock> stocks;
	static DBHandler dbHandler = new DBHandler();;

    /**
     * initializes the portfolio
     */
	public Portfolio() {
		stocks = new ArrayList<>();
		this.initPortfolio();
	}

    /**
     * initializes the portfolio by adding values from the database
     */
	public void initPortfolio(){
		ArrayList<StockTuple<Timestamp, String, Double, Integer>> stockData = dbHandler.getStocks();
		for (StockTuple<Timestamp, String, Double, Integer> stock: stockData){
			stocks.add(new Stock(stock.getVal2(), stock.getVal4(), stock.getVal3(), stock.getVal1()));
		}
	}

    /**
     * places a "buy order" and adds the stock to the portfolio and database
     * @param ticker the ticker we are adding
     * @param shares the number of stocks we bought
     * @throws Exception when unable to connect
     */
	public void placeBuyOrder(String ticker, double shares) throws Exception{
		java.util.Date date = new java.util.Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		String currentDateTime = format.format(date);
		double currentPrice = DataScrape.getCurrentPrice(ticker);
		Stock stock = new Stock(ticker, shares, currentPrice, new Timestamp(System.currentTimeMillis()));
		dbHandler.addStock(ticker, (int)shares, currentPrice);
		dbHandler.updateTransactionHistory(ticker, "B", (int)shares, currentPrice, -1.0);
		stocks.add(stock);
//			}
//		}
	}

    /**
     * places a "buy order" and adds the stock to the portfolio and database
     * @param ticker the ticker we are adding
     * @param shares the number of stocks we bought
     * @throws Exception when unable to connect
     */
	public boolean placeSellOrder(String ticker, double shares) {
		ArrayList<Stock> stocksForTicker = new ArrayList<>();
		double stockCount = 0.0;
		for (Stock stock: stocks){
			if (stock.ticker.equals(ticker)){
				stocksForTicker.add(stock);
				stockCount += stock.shares;
			}
		}
		if (stocksForTicker.size() == 0 || stockCount < shares){
			return false;
		}
		Collections.sort(stocksForTicker);
		double sellShares = shares;
		while(sellShares > 0.0){
			for(Stock stock: stocksForTicker) {
				double sold;
				double numOfStocks = stock.shares;
				if (sellShares - numOfStocks >= 0){
					sellShares -= numOfStocks;
					stocks.remove(stock);
					dbHandler.removeStock(stock.buyPrice);
					sold = stock.shares;
				}
				else{
					sold = sellShares;
					stock.shares -= sellShares;
					sellShares = 0.0;
					dbHandler.updateStock(stock.buyPrice, (int)stock.shares);
				}
			}
		}
		try {
			dbHandler.updateTransactionHistory(ticker, "S", (int)shares, -1.0, DataScrape.getCurrentPrice(ticker));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}



//
//		if(stock != null) {
//			boolean success = stock.sell(shares);
//			if(!success) {
//				System.out.println("You don't own that many shares!");
//				return false;
//			}
//			if (stock.shares == 0){
//				stocks.remove(stock.ticker);
//			}
//		}
//		else {
//			return false;
//		}
//		return true;
		return true;
	}
	/**
	*@return ArrayList<Stock> containing portfolio stocks.
	*
	*/
	public ArrayList<Stock> getPortfolio(){
		return this.stocks;
	}
	/**
	*@return double sum of all stocks in portfolio.
	*
	*/
	public double getPortfolioValue(){
		double sum = 0.0;
		for (Stock stock : stocks) {
		    sum+= stock.calculateValue();
		}
		return sum;
	}
}
