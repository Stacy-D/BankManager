package BankMeneger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

/**
 * it is the Client class
 * @author natalia
 */

public class Client {
	
	private String name;
	private int id;
	private String password;
	private int cardNumber;
	private int moneyOnBanlAccount;
	private int moneyLimit;
	
	private Connection connection;
	private ArrayList<Transaction> transactions;
	
	private static final int MAX_DAY_LIMIT = 15000;  // MAX_DAY_LIMIT, which you can withdraw
	
	Client(String aName, int anId, String aPassword, int aMoneyOnBanlAccount) {
		name = aName;
		id = anId;
		password = aPassword;
		moneyOnBanlAccount = aMoneyOnBanlAccount;
	}

	/**
	 * @return the name
	 */
	
	public String getName() {
		return name;
	}

	/**
	 * @return the id
	 */
	
	public int getId() {
		return id;
	}

	/**
	 * @return the password
	 */
	
	public String getPassword() {
		return password;
	}
	
	/**
	 * @return the cardNumber
	 */
	public int getCardNumber() {
		return cardNumber;
	}
	
	/**
	 * @return the moneyOnBanlAccount
	 */
	public int getMoneyOnBanlAccount() {
		return moneyOnBanlAccount;
	}
	
	public void setMoneyOnBanlAccount(int aMoney) {
		moneyOnBanlAccount = aMoney;
	}

	/**
	 * set the cardNumber
	 */
	
	public void setCardNumber(int number) {
		this.cardNumber = number;
	}
	
	
	public void createDatastore(Client aClient) {
		 try {
	            Class.forName("org.sqlite.JDBC");
	            connection = DriverManager.getConnection("jdbc:sqlite: Bank");
	            PreparedStatement st = connection.prepareStatement("create table if not exists 'Bank' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'clientId' INTEGER,'password' INTEGER, 'money' INTEGER,'cardNumber' INTEGER);");
	            int result = st.executeUpdate();
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
	        	int clientId = aClient.getId();
	        	int cardNumber = aClient.getCardNumber();
	        	int money = aClient.getMoneyOnBanlAccount();
	          
	        	PreparedStatement statement = connection.prepareStatement("INSERT INTO Bank(name, password, clientId, money, cardNumber) VALUES (?,?,?,?,?)");
	            statement.setString(1, name);
	            statement.setString(2, password);
	            statement.setInt(3, clientId);
	            statement.setInt(4, money);
	            statement.setInt(5, cardNumber);

	            int result = statement.executeUpdate();

	            statement.close();
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
	
	public void removeFromDatastore(int clientId) {
		PreparedStatement statement = null;
    	try {
    		statement = connection.prepareStatement("DELETE FROM Bank WHERE clientId = ?");
    		statement.setInt(3, clientId);
    		statement.executeUpdate();
    		moneyOnBanlAccount = 0;
    		System.out.println("The client with id " + clientId + " was deleted");
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
	 * check if can do a transaction
	 */
	
	private boolean canWithdraw(int moneyToWithdraw) {
		if (moneyToWithdraw <= MAX_DAY_LIMIT) {
			double sum = 0;
			Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		      
	        int currentDay = localCalendar.get(Calendar.DATE);

			for (int i = 0; i < transactions.size(); i++) {
				Transaction theTransaction = transactions.get(i);
				if (theTransaction.isSuccessful()) {
					int theDay = theTransaction.getOperationDay();
					if (theDay == currentDay) {
						sum += theTransaction.getRemovedMoney();
					} else {
						break;
					}
				}
			}
			if (sum <= MAX_DAY_LIMIT) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * calculate transactions
	 * @param aTransaction
	 * @return
	 */
	
	public boolean addTransaction(Transaction aTransaction) {
		for (int i = transactions.size()-2; i >= 0 ; i--) {
			transactions.set(i+1, transactions.get(i)); 
		}
		transactions.set(0, aTransaction);
		return true;
	}
	
	/**
	 * withdraw money
	 */
	
	public int withdrawMoney(int moneyToWithdraw) {
		if (canWithdraw(moneyToWithdraw) && moneyOnBanlAccount >= moneyToWithdraw) {
			boolean looked = true;
			Transaction theTransaction = new Transaction(moneyToWithdraw);
			moneyOnBanlAccount -= moneyToWithdraw;
			theTransaction.setSuccessful(true);
			addTransaction(theTransaction);
			looked = false;
		}
		return moneyOnBanlAccount;
	}
	
}
