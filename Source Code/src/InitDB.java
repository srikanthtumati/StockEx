import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InitDB {

    private static Connection connection;


    public static void main(String[] args){
        connection = null;
        String url = "jdbc:mysql://localhost:3306?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC";
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter SQL root username: ");
            String username = sc.nextLine();
            System.out.print("Enter SQL root password: ");
            String password = sc.nextLine();
            System.out.println("Attempting to connect!");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully connected!");
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        String query0 = "CREATE DATABASE if not exists stocks;";
        String query1 = "use stocks;";
        String query2 = "CREATE TABLE userStocks (boughtDT DATETIME PRIMARY KEY, name VARCHAR(5), boughtPrice DECIMAL(7,2),  numStocks INT);";
        String query3 = "CREATE TABLE transactionHistory ( actionDT DATETIME PRIMARY KEY, action VARCHAR(1), stockTicker VARCHAR(5), numStocks INT, boughtPrice DECIMAL(7,2), sellPrice DECIMAL(7,2));\n";
        try( PreparedStatement q0 = connection.prepareStatement(query0)){
            q0.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        try( PreparedStatement q1 = connection.prepareStatement(query1)){
            q1.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        try( PreparedStatement q2 = connection.prepareStatement(query2)){
            q2.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        try( PreparedStatement q3 = connection.prepareStatement(query3)){
            q3.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
