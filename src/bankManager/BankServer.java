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
		static final int PORT = 8080;
		
		public static void main(String[] args) throws IOException{
			ServerSocket s = new ServerSocket(PORT);
			System.out.println("Bank server in action.");
			
			try{
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

