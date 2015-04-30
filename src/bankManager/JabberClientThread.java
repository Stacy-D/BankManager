package bankManager;

import java.net.*;
import java.util.logging.Logger;
import java.io.*;

import panels.BankBranch;

class JabberClientThread extends Thread {
	private static final Logger LOG = Logger.getLogger(JabberClientThread.class.getName());
      
   public JabberClientThread(InetAddress addr, int port) {
      threadcount++;
      try {
         socket = new Socket(addr,port); 
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
         try {
            socket.close();
         }
         catch (IOException e) {
             LOG.warning("Socket wasn`t closed");
         }
      }
   
   private Socket socket;
   private static int counter = 0;
   private int id = counter++;
   private static int threadcount = 0;
}