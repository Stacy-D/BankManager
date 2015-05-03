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
	
	BankService() {
		
	}
	
	/* TODO
	 * This part of code needs to be moved to another class
	 *
	 */
	public static void createDatastore() {
		 try {
	            Class.forName("org.sqlite.JDBC");
	            connection = DriverManager.getConnection("jdbc:sqlite: Bank");
	            PreparedStatement st = connection.prepareStatement("create table if not exists 'Bank' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'clientId' text,'password' text, 'money' INTEGER,'cardNumber' INTEGER,'limit' INTEGER);");
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
	          
	        	PreparedStatement statement = connection.prepareStatement("INSERT INTO Bank(name, password, clientId, money, cardNumber,limit) VALUES (?,?,?,?,?,?)");
	            statement.setString(1, name);
	            statement.setString(2, password);
	            statement.setString(3, clientId);
	            statement.setInt(4, money);
	            statement.setInt(5, cardNumber);
	            statement.setInt(6, 150000);

	            int result = statement.executeUpdate();

	            statement.close();
	        	System.out.println("Operation done successfully");
	        	
	        }catch (SQLException e) {
	            System.out.println("Wrong SQL");
	            e.printStackTrace();
	        }
		}
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
			limit = resSet.getInt("limit");
			find = true;
			break;
			}
		}
		if(find) return "OKBAL"+money+"LIM"+limit;
		return "F";
		 
	 }
	
	 public boolean findByName(String aName, String aPassword) throws SQLException {
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
	    
	 
	 public boolean findByCardNumber(int aCardNumber, String aPassword, int aMoneyToChange) throws SQLException {
		 Statement statement = null;
	    	int cardNumber  = 0;
	    	int  money = 0;
	    	String password = null;
	    	ArrayList<Integer> cardNumberList = new ArrayList<Integer>();
	    	ArrayList<String> passwordList = new ArrayList<String>();
	    		statement = connection.createStatement();
	    		ResultSet resSet = statement.executeQuery("SELECT * FROM Bank;");
	    		while (resSet.next()) {
	    			password = resSet.getString("password");
	    			 cardNumber = resSet.getInt("cardNumber");
	    			money = resSet.getInt("money");
		    			
	    			 cardNumberList.add(cardNumber);
	    			 passwordList.add(password);
	    		}
	    		resSet.close();
	    		statement.close();
	    		for (int i = 0; i < cardNumberList.size(); i++) {
	    			for (int j = 0; j < passwordList.size(); j++) {
		    			if (cardNumberList.get(i) == aCardNumber && passwordList.get(j).equals(aPassword)) {
		    				System.out.println("OK");
		    				updateStatus(money, cardNumber, password, aMoneyToChange);
			    			return true;
		    			} else {
			    			System.out.println("Not");
		    			}
	    			}
	    		}
				return false; 
	    }
	
	 private boolean updateStatus(int aMoney, int aCard, String aPassword, int currentMoney) throws SQLException {
		 if (addAccessWithCardNumber(aCard, aPassword)) {
			 int addedMoney = aMoney + currentMoney;
			 PreparedStatement ps = connection.prepareStatement(
				      "UPDATE Bank SET money = ? WHERE cardNumber = ? ");
			 ps.setInt(1, addedMoney);
			 ps.setInt(2, aCard);
			 ps.executeUpdate();
			 ps.close();
			 System.out.println("OK");
			 return true;
		}
		return false;
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
	
	/**
	 * access client to enter to his/her account in ATM
	 * @param aCardNumber
	 * @param aPassword
	 * @return
	 * @throws SQLException 
	 */
	
	public boolean addAccessWithCardNumber(int aCardNumber, String aPassword) throws SQLException {
		if (findByCardNumber(aCardNumber, aPassword)) {
			return true;
		}
		return false;
	}
}
