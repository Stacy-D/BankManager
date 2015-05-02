package bankManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Bank server, which will deal with database
 * @author Stacy
 *
 */
public class BankServer {
	private static int currentCardNumber = 0000;
	private static final String FIRPRTCARD = "1111 2222 3333 ";
	
		
		public static void startServer(int port) throws IOException{
			ServerSocket s = new ServerSocket(Integer.valueOf(port));
			System.out.println("Bank server in action."); 
			try{
				BankService.createDatastore();
				while (true){
					Socket socket = s.accept();
					try{
						new ServeOneClient(socket);
					}catch (IOException e){
						socket.close();
					}
				}
			}finally{
				s.close();
			}
		}

		public static int getNextCard()
		{
			currentCardNumber++;
			return currentCardNumber;
		}
		public static String currentCartNumber()
		{
			String current = String.valueOf(currentCardNumber);
			if(current.length()!=4)
			{
				if(current.length() == 1) current = "000"+current;
				else if (current.length() == 2) current = "00"+ current;
				else current = "0"+current;
			}
			return FIRPRTCARD+current;
		}
		
	}

