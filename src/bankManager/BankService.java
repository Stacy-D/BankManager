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
	
	private static int clientMoney;
	private static int clientLimit;
	
	private static final int MAX_LIMIT = 150000;
	
	BankService() {

	}
	
	/**
	 * create sql table
	 */
	
	public static void createDatastore() {
		 try {
	            Class.forName("org.sqlite.JDBC");
	            connection = DriverManager.getConnection("jdbc:sqlite: Bank.db");
	            PreparedStatement st = connection.prepareStatement("create table if not exists 'Bank' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'clientId' INTEGER,'password' text, 'money' INTEGER,'cardNumber' INTEGER, 'moneyLimit' INTEGER );");
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
	
	/**
	 * add client to sql
	 * @param aClient
	 */
	
	public static void addClientToDatastore(Client aClient) {
		if (aClient != null ) {
			try{
	        	String name = aClient.getName();
	        	String password = aClient.getPassword();
	        	String clientId = aClient.getId();
	        	int cardNumber = aClient.getCardNumber();
	        	int money = aClient.getMoneyOnBanlAccount();
	        	int limit = 150000;
	          
	        	PreparedStatement statement = connection.prepareStatement("INSERT INTO Bank(name, password,"
	        			+ " clientId, money, cardNumber, moneyLimit) VALUES (?,?,?,?,?,?)");
	            statement.setString(1, name);
	            statement.setString(2, password);
	            statement.setString(3, clientId);
	            statement.setInt(4, money);
	            statement.setInt(5, cardNumber);
	            statement.setInt(6, limit);
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
	 * 
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
	    			int id = resSet.getInt("id");
	    			 name = resSet.getString("name");
	    			String password = resSet.getString("password");
	    			int clientId = resSet.getInt("clientId");
	    			clientMoney = resSet.getInt("money");
	    			int cardNumber = resSet.getInt("cardNumber");
	    			int limit = resSet.getInt("moneyLimit");
	    			nameList.add(name);
	    			passwordList.add(password);
	    		}
	    		resSet.close();
	    		statement.close();
	    		for(int i = 0; i < nameList.size(); i++) {
	    			for (int j = 0; j < passwordList.size(); j++) {
	    				if (nameList.get(i).equals(aName) && passwordList.get(j).equals(aPassword)) {
		    				System.out.println("OK");
		    				System.out.println("Client money: "+clientMoney);
		    				return true;
		    			} else {
		    				System.out.println("Not");
		    			}
	    			}
	    			
	    		}
	    		return false;
	    }
	 public static boolean isClientPresent(String name, String pass)
	 {
		 return false;
	 }
	    
	 /**
	  * 
	  * @param aCardNumber
	  * @param aPassword
	  * @return
	  * @throws SQLException
	  */
	 
	 public static boolean findByCardNumber(int aCardNumber, String aPassword) throws SQLException {
		 Statement statement = null;
	    	int cardNumber  = 0;
	    	ArrayList<Integer> cardNumberList = new ArrayList<Integer>();
	    	ArrayList<String> passwordList = new ArrayList<String>();
	    		statement = connection.createStatement();
	    		ResultSet resSet = statement.executeQuery("SELECT * FROM Bank;");
	    		while (resSet.next()) {
	    			int id = resSet.getInt("id");
	    			String name = resSet.getString("name");
	    			String password = resSet.getString("password");
	    			int clientId = resSet.getInt("clientId");
	    			clientMoney = resSet.getInt("money");
	    			 cardNumber = resSet.getInt("cardNumber");
	    			 int dayLimit = resSet.getInt("moneyLimit");
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
	  * 
	  * @param name
	  * @param password
	  * @param money
	  * @param currentSum
	  * @return
	  * @throws SQLException
	  */
	 
	 public  static boolean withdrawByName(String name, String password, int money) throws SQLException {
		 if (findByName(name, password)) {
			 if (money <= MAX_LIMIT && clientMoney >= money) {
				 int withdraw = clientMoney - money;
				 clientLimit -= withdraw;
				 PreparedStatement statement = connection.prepareStatement("UPDATE Bank set money, moneyLimit"
				 		+ " = ? WHERE name = ?");
				 statement.setInt(1, withdraw);
				 statement.setInt(2, clientLimit);
				 statement.setString(3, name);
				 statement.executeUpdate();
				 
			 }
			 return true;
		 }
		 return false;
	 }
	 
	 public static boolean withdrawByCard(int aCardNumber, String password, int money) throws SQLException {
		 if (findByCardNumber(aCardNumber, password)) {
			 if (money <= MAX_LIMIT && clientMoney >= money) {
				 int withdraw = clientMoney - money;
				 clientLimit -= withdraw;
				 PreparedStatement statement = connection.prepareStatement("UPDATE Bank set money, moneyLimit"
				 		+ " = ? WHERE cardNumber = ?");
				 statement.setInt(1, withdraw);
				 statement.setInt(2, clientLimit);
				 statement.setInt(3, aCardNumber);
				 statement.executeUpdate();
				 
			 }
			 return true;
		 }
		 return false;
	 }
	 
	
	 
	 /**
	  * 
	  * @param aName
	  * @param aPassword
	  * @param aMoney
	  * @return
	  * @throws SQLException
	  */
	 
	 public static boolean addMoneyByName(String aName, String aPassword, int aMoney) throws SQLException {
		 System.out.println("Trying to add "+aMoney);
		 if (findByName(aName, aPassword)) {
				 int addMoney = clientMoney + aMoney;
				 System.out.println("New sum: "+addMoney);
				 PreparedStatement statement = connection.prepareStatement("UPDATE Bank set money"
				 		+ " = ? WHERE name = ?");
				 statement.setInt(1, addMoney);
				 statement.setString(2, aName);
				 statement.executeUpdate();
			 return true;
		 }
	    	return false;
	 }
	 /**
	  * 
	  * @param aCardNumber
	  * @param aPassword
	  * @param aMoney
	  * @return
	  * @throws SQLException
	  */
	 
	 public static boolean addMoneyByCardNumber(int aCardNumber, String aPassword, int aMoney) throws SQLException {
		 if (findByCardNumber(aCardNumber, aPassword)) {
			 int addMoney = clientMoney + aMoney;
			 PreparedStatement statement = connection.prepareStatement("UPDATE Bank set money"
			 		+ " = ? WHERE name = ?");
			 statement.setInt(1, addMoney);
			 statement.setInt(2, aCardNumber);
			 statement.executeUpdate();}
		 return true;
		 
	 }
	
	
	 /**
	  * close client deposit in a bank
	  * @param id
	  * @param aClient
	  * @throws SQLException
	  */
	 
	public static boolean removeFromDatastore(int id, Client aClient) throws SQLException {
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

	public static boolean addAccessByName(String aName, String aPassword) throws SQLException {
		if (findByName(aName, aPassword)) {
			return true;
		}
		return false;
	}
	 public static String getInfo(String nameSearch, String pass) throws SQLException
	 {
		 Statement statement = connection.createStatement();
		 int money = 0;
		 int limit = 0;
		 boolean find = false;
		ResultSet resSet = statement.executeQuery("SELECT * FROM Bank;");
		while (resSet.next()) {
			if(nameSearch.equals(resSet.getString("name")) && pass.equals(resSet.getString("password"))){
			money = resSet.getInt("money");
			limit = resSet.getInt("moneyLimit");
			find = true;
			break;
			}
		}
		if(find) return "OKBAL"+money+"LIM"+limit;
		return "F";
		 
	 }
	 
		 /**
		  * 
		  * @param password
		  * @return id or -1 if client wasn`t find
		  */
		  	 public static int findClient(String name)
		  	 {
		  		try
				 {
					 Statement st = connection.createStatement();
		             ResultSet res = st.executeQuery("SELECT * FROM Bank WHERE name=\""+name+"\";");
		             if(res.next()){
		            return res.getInt("id");	
		             }
				 }
				 catch(SQLException ex)
				 {
					 ex.printStackTrace();
				 }
		  		 return -1;
		  	 }

}
