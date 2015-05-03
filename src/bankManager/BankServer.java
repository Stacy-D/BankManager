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

		
	}

