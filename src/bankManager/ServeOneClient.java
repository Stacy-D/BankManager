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
		while(true)
		{
			String currentLine = inRead.readLine();
			if(currentLine.equals("END_OF_SESSION")) break;
			if(currentLine.startsWith("Addclient")) addClient(currentLine);
			if(currentLine.startsWith("Withdraw")) withdrawAction(currentLine);
			if(currentLine.startsWith("Addmoney")) addMoney(currentLine);
			if(currentLine.startsWith("Getinfo")) getInfo(currentLine);
		
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
	private void getInfo(String currentLine) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Server accepts "add money" call
	 * @param currentLine
	 */
	private void addMoney(String currentLine) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method to do withdraw action
	 * @param currentLine
	 */
	private void withdrawAction(String currentLine) {
		// TODO Auto-generated method stub
		
	}

	private void addClient(String command) {
	String name = command.substring(command.indexOf("NM")+2, command.indexOf("ID"));
	String id = command.substring(command.indexOf("ID")+2, command.indexOf("PASS"));
	String password = command.substring(command.indexOf("PASS")+3);
	int money = 0;
	System.out.println(new Client(name,id,password,money).toString());
	outStr.println("Client added");
	outStr.flush();
	}

	private Socket socket;
	private PrintWriter outStr;
	private BufferedReader inRead;
}
