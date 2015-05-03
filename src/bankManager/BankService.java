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
	private Client client;
	
	private static  int clientMoney;
	private static int clientLimit;
	
	private static final int MAX_LIMIT = 15000;
	
	BankService() {
		connection = null;
		clientMoney = 0;
		clientLimit = 15000;
	}
	
	/**
	 * create sql table
	 */
	
	public static void createDatastore() {
		 try {
	            Class.forName("org.sqlite.JDBC");
	            connection = DriverManager.getConnection("jdbc:sqlite: Bank");
	            PreparedStatement st = connection.prepareStatement("create table if not exists 'Bank' "
	            		+ "('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'clientId' INTEGER,"
	            		+ "'password' text, 'money' INTEGER,'cardNumber' INTEGER, 'moneyLimit' INTEGER);");
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
	 * @throws SQLException 
	 */
	
	public static void addClientToDatastore(Client aClient) throws SQLException {
		if (aClient != null && !findByName(aClient.getName(), aClient.getPassword()) 
				&& !findByCardNumber(aClient.getCardNumber(), aClient.getPassword())) {
			try {
	        	String name = aClient.getName();
	        	String password = aClient.getPassword();
	        	String clientId = aClient.getId();
	        	int cardNumber = aClient.getCardNumber();
	        	int money = aClient.getMoneyOnBanlAccount();
	        	PreparedStatement statement = connection.prepareStatement("INSERT INTO Bank(name, password,"
	        			+ " clientId, money, cardNumber, moneyLimit) VALUES (?,?,?,?,?,?)");
	            statement.setString(1, name);
	            statement.setString(2, password);
	            statement.setString(3, clientId);
	            statement.setInt(4, money);
	            statement.setInt(5, cardNumber);
	            statement.setInt(6, clientLimit);
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
	    			clientMoney = resSet.getInt("money");
	    			int cardNumber = resSet.getInt("cardNumber");
	    			int limit = resSet.getInt("moneyLimit");
	    			nameList.add(name);
	    			passwordList.add(password);
	    			System.out.println("id: " + id);
	    			System.out.println("name: " + name);
	    			System.out.println("password: " + password);
	    			System.out.println("client id: " + clientId);
	    			System.out.println("client money: " + clientMoney);
	    			System.out.println("card number: " + cardNumber);
	    			System.out.println("limit: " + limit);
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
	    			 int limit = resSet.getInt("moneyLimit");
	    			 cardNumberList.add(cardNumber);
	    			 passwordList.add(password);
	    			 System.out.println("id: " + id);
		    			System.out.println("name: " + name);
		    			System.out.println("password: " + password);
		    			System.out.println("client id: " + clientId);
		    			System.out.println("client money: " + clientMoney);
		    			System.out.println("card number: " + cardNumber);
		    			System.out.println("limit: " + limit);
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
				 int result = clientLimit - withdraw;
				 PreparedStatement statement = connection.prepareStatement("UPDATE Bank SET money = ?, moneyLimit = ?");
				 statement.setInt(1, withdraw);
				 statement.setInt(2, result);
				 statement.executeUpdate();
				 System.out.println("money res: " + withdraw);
				 System.out.println("money limit: " + result);
			 }
			 return true;
		 } else {
			 System.out.println("You haven't enougth money to withdrow");
			 return false;
		 }
		 
	 }
	 
	 public static boolean withdrawByCard(int aCardNumber, String password, int money) throws SQLException {
		 if (findByCardNumber(aCardNumber, password)) {
			 if (money <= MAX_LIMIT && clientMoney >= money) {
				 int withdraw = clientMoney - money;
				 clientLimit -= withdraw;
				 PreparedStatement statement = connection.prepareStatement("UPDATE Bank SET money = ?, moneyLimit = ?");
				 statement.setInt(1, withdraw);
				 statement.setInt(2, clientLimit);
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
		 if (findByName(aName, aPassword)) {
				 int addMoney = clientMoney + aMoney;
				 PreparedStatement statement = connection.prepareStatement("UPDATE Bank SET money"
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
			 PreparedStatement statement = connection.prepareStatement("UPDATE Bank SET money"
			 		+ " = ? WHERE name = ?");
			 statement.setInt(1, addMoney);
			 statement.setInt(2, aCardNumber);
			 statement.executeUpdate();
		 return true;
	 }
    	return false;
	    }
	 
	
	
	 /**
	  * close client deposit in a bank
	  * @param id
	  * @param aClient
	  * @throws SQLException
	  */
	 
	public static boolean removeFromDatastoreByName(String aClientName , Client aClient) throws SQLException {
		PreparedStatement statement = null;
		int clientMoney = aClient.getMoneyOnBanlAccount();
		if (clientMoney <= MAX_LIMIT) {
    		statement = connection.prepareStatement("DELETE FROM Bank WHERE id = ?");
    		statement.setString(1, aClientName);
    		statement.executeUpdate();
    		clientMoney = 0;
    		System.out.println("The client with client name " + aClientName + " was deleted");
    		statement.close();
    		return true;
    	} 
		return false;
    }
	
	/**
	  * close client deposit in a bank
	  * @param id
	  * @param aClient
	  * @throws SQLException
	  */
	 
	public static boolean removeFromDatastoreByCardNumber(int aClientCardNumber , Client aClient) throws SQLException {
		PreparedStatement statement = null;
		int clientMoney = aClient.getMoneyOnBanlAccount();
		if (clientMoney <= MAX_LIMIT) {
   		statement = connection.prepareStatement("DELETE FROM Bank WHERE cardNumber = ?");
   		statement.setInt(1, aClientCardNumber);
   		statement.executeUpdate();
   		clientMoney = 0;
   		System.out.println("The client with client card number " + aClientCardNumber + " was deleted");
   		statement.close();
   		return true;
   	} 
		return false;
   }
	
	/**
	 * 
	 */
	
	public static void showAllClients(){
        try{
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM Bank");
            while (res.next()) {
                String name = res.getString("name");
                System.out.println (res.getShort("id")+" "+name);
            }
            res.close();
            st.close();
            System.out.println("Hi");
        }catch(SQLException e){
            System.out.println("Wrong SQL");
            e.printStackTrace();
        }
    }

	
}
