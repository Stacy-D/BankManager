/**
 * 
 */
package bankManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author natalia
 *
 */
public class BankService {
	
	private Connection connection;
	
	BankService() {
		
	}
	
	/* TODO
	 * This part of code needs to be moved to another class
	 *
	 */
	public void createDatastore() {
		 try {
	            Class.forName("org.sqlite.JDBC");
	            connection = DriverManager.getConnection("jdbc:sqlite: Bank");
	            PreparedStatement st = connection.prepareStatement("create table if not exists 'Bank' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'clientId' INTEGER,'password' INTEGER, 'money' INTEGER,'cardNumber' INTEGER);");
	            int result = st.executeUpdate();
	        	System.out.println("Operation done successfully");

	        } catch (ClassNotFoundException e) {
	            System.out.println(" JDBC");
	            e.printStackTrace();
	            System.exit(0);
	        } catch (SQLException e) {
	            System.out.println("Wrong SQL");
	            e.printStackTrace();
	        }    
	}
	
	public void addClientToDatastore(Client aClient) {
		if (aClient != null && !isClientInDatastore()) {
			try{
	        	String name = aClient.getName();
	        	String password = aClient.getPassword();
	        	String clientId = aClient.getId();
	        	int cardNumber = aClient.getCardNumber();
	        	int money = aClient.getMoneyOnBanlAccount();
	          
	        	PreparedStatement statement = connection.prepareStatement("INSERT INTO Bank(name, password, clientId, money, cardNumber) VALUES (?,?,?,?,?)");
	            statement.setString(1, name);
	            statement.setString(2, password);
	            statement.setString(3, clientId);
	            statement.setInt(4, money);
	            statement.setInt(5, cardNumber);

	            int result = statement.executeUpdate();

	            statement.close();
	        	System.out.println("Operation done successfully");

	        }catch (SQLException e) {
	            System.out.println("Wrong SQL");
	            e.printStackTrace();
	        }
		}
	}
	
	public boolean isClientInDatastore() {
		Statement statement = null;
    	try {
    		statement = connection.createStatement();
    		ResultSet resSet = statement.executeQuery("SELECT * FROM Bank;");
    		while (resSet.next()) {
    			int id = resSet.getInt("clientId");
    			String name = resSet.getString("name");
    			System.out.println("clientId: " + id);
    			System.out.println("Name: " + name);
    			System.out.println("");
    		}
    		resSet.close();
    		statement.close();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	System.out.println("Operation done successfully");
		return true;
	}
	
	public void removeFromDatastore(int id) {
		PreparedStatement statement = null;
    	try {
    		statement = connection.prepareStatement("DELETE FROM Bank WHERE id = ?");
    		statement.setInt(1, id);
    		statement.executeUpdate();
    		System.out.println("The client with id " + id + " was deleted");
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} finally {
    		if (statement != null) {
    			try {
    				statement.close();
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		}
    	}
	}
	
	
	
	
}
