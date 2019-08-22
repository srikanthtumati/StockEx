import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that handles all operations with the database
 *
 * @author Srikanth Tumati
 * @version 1.0
 * @since 1.0
 */
public class DBHandler {

    private Connection connection;

    /**
     * Initializes the DBHandler object.
     */
    public DBHandler(){
        String url = "jdbc:mysql://localhost:3306/stocks?verifyServerCertificate=false&useSSL=true&serverTimezone=EST";
//        String url = "jdbc:mysql://localhost:3306/stocks?useSSL=false";
        connection = null;
        try
        {
           String username;
           String password;
           try (BufferedReader br = new BufferedReader(new FileReader("SQLCredentials.txt"))) {
               username = br.readLine();
               password = br.readLine();
           }
            try {
                connection = DriverManager.getConnection(url, username, password);
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * closes the connection with the database
     */
    public void connectionClose(){
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException ex) {
                for (Throwable t : ex)
                    System.out.println(t.getMessage());
                System.out.println("Closing connection unsuccesful!");
            }
        }
    }

    /**
     * adds the stock to the database
     * @param stockTicker the ticker for the stock
     * @param numOfStocks the number of stocks
     * @param currentPrice the current price fo the stock
     * @return true if successful and false otherwise
     */
    public boolean addStock(String stockTicker, int numOfStocks, double currentPrice){
        String query = "INSERT INTO userStocks VALUES (?,?,?,?);";
        try (PreparedStatement stat = connection.prepareStatement(query)) {
            stat.setString(2, stockTicker);
            stat.setDouble(3, currentPrice);
            java.util.Date date = new java.util.Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
            String currentDateTime = format.format(date);
            stat.setString(1, currentDateTime);
            stat.setInt(4, numOfStocks);
            int recordUpdate = stat.executeUpdate();
            System.out.println(recordUpdate + " rows successfully added into database!");
            return true;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * gets the stocks currently in the database
     * @return a list of all stocks in the user's portfolio
     */
    public ArrayList<StockTuple<Timestamp, String, Double, Integer>> getStocks(){
        ArrayList<StockTuple<Timestamp, String, Double, Integer>> stockData = new ArrayList<>();
        String query = "SELECT * from userStocks;";
        try (PreparedStatement stat = connection.prepareStatement(query)) {
            ResultSet resultSet = stat.executeQuery();
            while (resultSet.next()){
                Timestamp timestamp = resultSet.getTimestamp(1);
                java.util.Date date = timestamp;
                stockData.add(new StockTuple<>(resultSet.getTimestamp(1), resultSet.getString(2), resultSet.getDouble(3),  resultSet.getInt(4)));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return stockData;
    }


    /**
     * removes the stock from the database
     * @param boughtPrice the price the user bought the stock for
     * @return true if successful and false otherwise
     */
    public boolean removeStock(double boughtPrice){
        String query = "DELETE FROM userStocks where boughtPrice = ?;";
        try (PreparedStatement stat = connection.prepareStatement(query)) {
            stat.setDouble(1, boughtPrice);
            int recordUpdate = stat.executeUpdate();
            System.out.println(recordUpdate + " row successfully removed from database!");
            return true;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * updates the database if the user sells a partial amount of stocks
     * @param boughtPrice the price the user bought the stock for
     * @param numOfStocks the number of stocks
     * @return true if successful and false otherwise
     */
    public boolean updateStock(Double boughtPrice, int numOfStocks){
        String query = "UPDATE userStocks set numStocks = ? where boughtPrice = ?;";
        try (PreparedStatement stat = connection.prepareStatement(query)) {
            stat.setInt(1, numOfStocks);
            stat.setDouble(2, boughtPrice);
            int recordUpdate = stat.executeUpdate();
            System.out.println(recordUpdate + " row successfully removed from database!");
            return true;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * updates the transaction history and stores the updates in the database
     * @param ticker the stock ticker
     * @param action the action (either buying or selling)
     * @param numOfStocks the number of stocks
     * @param buyPrice the buy price of the stock
     * @param sellPrice the sell price of the stock
     * @return true if successful and false otherwise
     */
    public boolean updateTransactionHistory(String ticker, String action, int numOfStocks, double buyPrice, double sellPrice){
            String query = "INSERT INTO transactionHistory VALUES (?,?,?,?,?,?);";
            try (PreparedStatement stat = connection.prepareStatement(query)) {
                stat.setString(2, action);
                stat.setString(3, ticker);
                java.util.Date date = new java.util.Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
                String currentDateTime = format.format(date);
                stat.setString(1, currentDateTime);
                stat.setInt(4, numOfStocks);
                stat.setDouble(5, buyPrice);
                stat.setDouble(6, sellPrice);
                int recordUpdate = stat.executeUpdate();
                System.out.println(recordUpdate + " rows successfully added into database!");
                return true;
            }
            catch(SQLException ex){
                ex.printStackTrace();
                return false;
            }


    }

    /**
     * gets a list of all the transaction history from the database
     * @return a list of all the transaction history
     */
    public ArrayList<TransactionHistoryTuple<Timestamp, String, String, Integer, Double, Double>> getTransactionHistory(){
        ArrayList<TransactionHistoryTuple<Timestamp, String, String, Integer, Double, Double>> stockData = new ArrayList<>();
        String query = "SELECT * from transactionHistory;";
        try (PreparedStatement stat = connection.prepareStatement(query)) {
            ResultSet resultSet = stat.executeQuery();
            while (resultSet.next()){
                stockData.add(new TransactionHistoryTuple<>(resultSet.getTimestamp(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4),resultSet.getDouble(5),  resultSet.getDouble(6)));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return stockData;
    }

}
