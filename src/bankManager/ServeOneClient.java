package bankManager;

import java.io.*;
import java.net.*;

public class ServeOneClient extends Thread{

	public ServeOneClient(Socket s) throws IOException{
		try{
		socket = s;
		inRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outStr = new PrintWriter(socket.getOutputStream());
		}
		catch (IOException ex)
		{
			socket.close();
			ex.printStackTrace();
		}
		start();
	}
	
	public void run(){
		try{
			outStr.println("Hello, I`m the bank server");
			System.out.println("Here in serve");
			outStr.flush();
		while(true)
		{
			String currentLine = inRead.readLine();
			System.out.println(currentLine);
			if(currentLine!=null){
			if(currentLine.equals("END_OF_SESSION")){ 
				break;}
			if(currentLine.startsWith("Addclient")) addClient(currentLine);
			if(currentLine.startsWith("Withdraw")) withdrawAction(currentLine);
			if(currentLine.startsWith("Addmoney")) addMoney(currentLine);
			if(currentLine.startsWith("Getinfo")) getInfo(currentLine);
			}
		}
		}
		catch(IOException ex)
		{
			System.err.println("IO Exception");
		}
		finally{
			try{
				socket.close();
			}
			catch(IOException e){
				System.err.println("Сокет не закрито ...");
			}
		}
	}
	/**
	 * Server accepts "get Info" call
	 * @param currentLine
	 */
	private void getInfo(String command) {
		String name = command.substring(command.indexOf("NM")+2, command.indexOf("PASS"));
		String password = command.substring(command.indexOf("PASS")+3);
		// TODO
		// Here use method to find cliend and his info
		int balance = 0;
		int limit = 0;
		outStr.println("RESPGetinfoRESOKBAL"+balance+"LIM"+limit);
		//another possibility when not found (or password do not match) outStr.println("REJECTED");
		outStr.flush();
		
		
	}

	/**
	 * Server accepts "add money" call
	 * @param currentLine
	 */
	private void addMoney(String command) {
		String name = command.substring(command.indexOf("NM")+2, command.indexOf("PASS"));
		String password = command.substring(command.indexOf("PASS")+3,command.indexOf("ADD"));
		try{
			int add = Integer.valueOf(command.indexOf("ADD")+3);
			//TODO here add money to user account if it was found
			outStr.println("RESPAddmoneyRESOK");
			// or outStr.println("REJECTED");
			outStr.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	/**
	 * Method to do withdraw action
	 * @param currentLine
	 */
	private void withdrawAction(String command) {
		String name = command.substring(command.indexOf("NM")+2, command.indexOf("PASS"));
		String password = command.substring(command.indexOf("PASS")+3,command.indexOf("MINUS"));
		try{
			int add = Integer.valueOf(command.indexOf("MINUS")+5);
			//TODO here add money to user account if it was found
			outStr.println("RESPWithdrawRESOK");
			// or outStr.println("REJECTED");
			outStr.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}

	private void addClient(String command) {
	String name = command.substring(command.indexOf("NM")+2, command.indexOf("ID"));
	String id = command.substring(command.indexOf("ID")+2, command.indexOf("PASS"));
	String password = command.substring(command.indexOf("PASS")+3);
	int money = 0;
	// TODO
	// replace this with addition to database
	System.out.println(new Client(name,id,password,money).toString());
	// info about successful action
	Client temp = new Client(name,id,password,money);
	outStr.println("RESPAddclientRESOKCARD0000111122220000");
	outStr.flush();
	}

	private Socket socket;
	private PrintWriter outStr;
	private BufferedReader inRead;
}
