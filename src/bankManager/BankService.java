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
import java.util.ArrayList;
import java.util.Properties;



/**
 * @author natalia
 *
 */
public class BankService {
	
	private static Connection connection;
	private static final int MAX_LIMIT = 15000;

	
	BankService() {
		
	}
	
	public static void createDatastore() {
		 try {
	            Class.forName("org.sqlite.JDBC");
	            connection = DriverManager.getConnection("jdbc:sqlite: Bank");
	            PreparedStatement st = connection.prepareStatement("create table if not exists 'Bank' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'clientId' text,'password' text, 'money' INTEGER,'cardNumber' INTEGER);");
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
	
	public static void addClientToDatastore(Client aClient) {
		if (aClient != null ) {
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
	
	/**
	 * check if is client in bank database by name
	 * @param aName
	 * @param aPassword
	 * @return
	 * @throws SQLException
	 */
	
	
	 public static boolean findByName(String aName, String aPassword) throws SQLException {
	    	Statement statement = null;
	    	String name  = null;
	    	ArrayList<String> nameList = new ArrayList<String>();
	    	ArrayList<String> passwordList = new ArrayList<String>();
	    		statement = connection.createStatement();
	    		ResultSet resSet = statement.executeQuery("SELECT * FROM Bank;");
	    		while (resSet.next()) {
	    			 name = resSet.getString("name");
	    			String password = resSet.getString("password");
	    			nameList.add(name);
	    			passwordList.add(password);
	    		}
	    		resSet.close();
	    		statement.close();
	    		for(int i = 0; i < nameList.size(); i++) {
	    			for (int j = 0; j < passwordList.size(); j++) {
	    				if (nameList.get(i).equals(aName) && passwordList.get(j).equals(aPassword)) {
		    				System.out.println("OK");
		    				return true;
		    			} else {
		    				System.out.println("Not");
		    			}
	    			}
	    			
	    		}
	    		return false;
	    }
	    
	 /**
	  *  check if is client in bank database by card number
	  * @param aCardNumber
	  * @param aPassword
	  * @return
	  * @throws SQLException
	  */
	 
	 public boolean findByCardNumber(int aCardNumber, String aPassword) throws SQLException {
		 Statement statement = null;
	    	int cardNumber  = 0;
	    	ArrayList<Integer> cardNumberList = new ArrayList<Integer>();
	    	ArrayList<String> passwordList = new ArrayList<String>();
	    		statement = connection.createStatement();
	    		ResultSet resSet = statement.executeQuery("SELECT * FROM Bank;");
	    		while (resSet.next()) {
	    			String password = resSet.getString("password");
	    			 cardNumber = resSet.getInt("cardNumber");
	    			 cardNumberList.add(cardNumber);
	    			 passwordList.add(password);
	    		}
	    		resSet.close();
	    		statement.close();
	    		for (int i = 0; i < cardNumberList.size(); i++) {
	    			for (int j = 0; j < passwordList.size(); j++) {
		    			if (cardNumberList.get(i) == aCardNumber && passwordList.get(j).equals(aPassword)) {
		    				System.out.println("OK");
			    			return true;
		    			} else {
			    			System.out.println("Not");
		    			}
	    			}
	    		}
				return false; 
	    }
	
	 /**
	  * add money after we check is client in bank database by name
	  * @param aCardNumber
	  * @param aPassword
	  * @param aMoney
	  * @return
	  * @throws SQLException
	  */
	 
	 public boolean addMoneyByName(String aName, String aPassword, int aMoney) throws SQLException {
	    	Statement statement = null;
	    	String name  = null;
	    	int money = 0;
	    	ArrayList<String> nameList = new ArrayList<String>();
	    	ArrayList<String> passwordList = new ArrayList<String>();
	    		statement = connection.createStatement();
	    		ResultSet resSet = statement.executeQuery("SELECT * FROM Bank;");
	    		while (resSet.next()) {
	    			int id = resSet.getInt("id");
	    			 name = resSet.getString("name");
	    			String password = resSet.getString("password");
	    			int clientId = resSet.getInt("clientId");
	    			money = resSet.getInt("money");
	    			int cardNumber = resSet.getInt("cardNumber");
	    			nameList.add(name);
	    			passwordList.add(password);
	    		}
	    		resSet.close();
	    		statement.close();
	    		for(int i = 0; i < nameList.size(); i++) {
	    			for (int j = 0; j < passwordList.size(); j++) {
	    				if (nameList.get(i).equals(aName) && passwordList.get(j).equals(aPassword)) {
		    				System.out.println("OK");
		    				money += aMoney;
		    				return true;
		    			} 
	    			}
	    			
	    		}
	    		return false;
	    }
	    
	 /**
	  * add money after we check is client in bank database by card number
	  * @param aCardNumber
	  * @param aPassword
	  * @param aMoney
	  * @return
	  * @throws SQLException
	  */
	 
	 public boolean addMoneyByCardNumber(int aCardNumber, String aPassword, int aMoney) throws SQLException {
		 Statement statement = null;
	    	int cardNumber  = 0;
	    	int money = 0;
	    	ArrayList<Integer> cardNumberList = new ArrayList<Integer>();
	    	ArrayList<String> passwordList = new ArrayList<String>();
	    		statement = connection.createStatement();
	    		ResultSet resSet = statement.executeQuery("SELECT * FROM Bank;");
	    		while (resSet.next()) {
	    			int id = resSet.getInt("id");
	    			String name = resSet.getString("name");
	    			String password = resSet.getString("password");
	    			int clientId = resSet.getInt("clientId");
	    			money = resSet.getInt("money");
	    			cardNumber = resSet.getInt("cardNumber");
	    			cardNumberList.add(cardNumber);
	    			passwordList.add(password);
	    		}
	    		resSet.close();
	    		statement.close();
	    		for (int i = 0; i < cardNumberList.size(); i++) {
	    			for (int j = 0; j < passwordList.size(); j++) {
		    			if (cardNumberList.get(i) == aCardNumber && passwordList.get(j).equals(aPassword)) {
		    				money += aMoney;
			    			return true;
		    			} 
	    			}
	    		}
				return false; 
	    }
	 
	
	 /**
	  * close client deposit in a bank
	  * @param id
	  * @param aClient
	  * @throws SQLException
	  */
	 
	public boolean removeFromDatastore(int id, Client aClient) throws SQLException {
		PreparedStatement statement = null;
		int clientMoney = aClient.getMoneyOnBanlAccount();
		if (clientMoney <= MAX_LIMIT) {
    		statement = connection.prepareStatement("DELETE FROM Bank WHERE id = ?");
    		statement.setInt(1, id);
    		statement.executeUpdate();
    		clientMoney = 0;
    		System.out.println("The client with id " + id + " was deleted");
    		statement.close();
    		return true;
    	} 
		return false;
    }
}
