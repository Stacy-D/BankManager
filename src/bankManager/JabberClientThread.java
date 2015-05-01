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
	   addClient(null,null,0,"hj");
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
		   
		System.out.println(in.readLine());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   public void getInfo(String lastName, String firstName, String password)
   {
	   String pass = EncryptPassword.encrypt(password);
	   os.println("Getinfo"+"NM"+firstName+" "+ lastName + "PASS"+password);
	   os.flush();
   }
   public void addMoney(String lastName, String firstName, String password,int money)
   {
	   String pass = EncryptPassword.encrypt(password);
	   os.println("Addmoney"+"NM"+firstName+" "+ lastName + "PASS"+password+"ADD"+money);
	   os.flush();
   }
   public void withdrawMoney(String lastName, String firstName, String password,int withdraw)
   {
	   String pass = EncryptPassword.encrypt(password);
	   os.println("Withdraw"+"NM"+firstName+" "+ lastName + "PASS"+password+"MINUS"+withdraw);
	   os.flush();
   }
   private String stop = "End of session";
   private BufferedReader in;
   private Socket socket;
   private PrintStream os;
   private static int counter = 0;
   private int id = counter++;
   private static int threadcount = 0;
}