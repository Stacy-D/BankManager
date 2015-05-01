package bankManager;

import java.net.*;
import java.util.logging.Logger;
import java.io.*;

import panels.BankBranch;

public class JabberClientThread extends Thread {
	private static final Logger LOG = Logger.getLogger(JabberClientThread.class.getName());
      
   public JabberClientThread(InetAddress addr, int port) {
      threadcount++;
      try {
         socket = new Socket(addr,port); 
         os = new PrintStream(socket.getOutputStream());
         in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         start();
         }
      catch (IOException e) {
         try {
            socket.close();
         }
         catch (IOException e2) {
            LOG.warning("Socket wasn`t closed");
         }
      }
   }
   
   public static int threadCount() {
	      return threadcount;
   }
	   
   public void run() {
	   //main action here
	   BankBranch.main(null);
	   System.out.println("I'm here");
	   //addClient(null,null,0,"hj");
	   getInfo("fad","few","efw");
	   os.println("END_OF_SESSION");
	   os.flush();
         try {
            socket.close();
         }
         catch (IOException e) {
             LOG.warning("Socket wasn`t closed");
         }
      }
   public void addClient(String firstName, String lastName, int id, String password)
   {
	   String pass = EncryptPassword.encrypt(password);
	   os.println("Addclient"+"NM"+firstName+" "+ lastName + "ID"+id+"PASS"+pass);
	   os.flush();   
	   try {
		   while(true){
				String resp =in.readLine();
				if(resp.startsWith("RESP"))
				{
					//TODO
					// client was added ->notify
					break;
				}
				if(resp.startsWith("REJECTED"))
				{
					// notify that there was a problem with addition
					break;
				}
		   }}
	 catch (IOException e) {
		e.printStackTrace();
	}
   }
   public void getInfo(String lastName, String firstName, String password)
   {
	   String pass = EncryptPassword.encrypt(password);
	   os.println("Getinfo"+"NM"+firstName+" "+ lastName + "PASS"+pass);
	   os.flush();
	   try {
		   while(true){
			String resp =in.readLine();
			if(resp.startsWith("RESP"))
			{
				//TODO
				// use the info from server here
				break;
			}
			if(resp.startsWith("REJECTED"))
			{
				// notify that there was a problem with query
				break;
			}
		   }
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
   public void addMoney(String lastName, String firstName, String password,int money)
   {
	   String pass = EncryptPassword.encrypt(password);
	   os.println("Addmoney"+"NM"+firstName+" "+ lastName + "PASS"+pass+"ADD"+money);
	   os.flush();
	   try {
		   while(true){
			String resp =in.readLine();
			if(resp.startsWith("RESP"))
			{
				//TODO
				// use the info from server here
				break;
			}
			if(resp.startsWith("REJECTED"))
			{
				// notify that there was a problem with query
				break;
			}
		   }
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
   public void withdrawMoney(String lastName, String firstName, String password,int withdraw)
   {
	   String pass = EncryptPassword.encrypt(password);
	   os.println("Withdraw"+"NM"+firstName+" "+ lastName + "PASS"+pass+"MINUS"+withdraw);
	   os.flush();
	   try {
		   while(true){
			String resp =in.readLine();
			if(resp.startsWith("RESP"))
			{
				//TODO
				// use the info from server here
				break;
			}
			if(resp.startsWith("REJECTED"))
			{
				// notify that there was a problem with query
				break;
			}
		   }
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
   private String stop = "End of session";
   private BufferedReader in;
   private Socket socket;
   private PrintStream os;
   private static int counter = 0;
   private int id = counter++;
   private static int threadcount = 0;
}