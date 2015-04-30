package bankManager;

import java.io.*;
import java.net.*;

public class ServeOneClient extends Thread{

	public ServeOneClient(Socket s) throws IOException{
		socket = s;
		start();
	}
	
	public void run(){
		// тут будуть події що робить сервер
			try{
				socket.close();
			}
			catch(IOException e){
				System.err.println("Сокет не закрито ...");
			}
		}
	
	private Socket socket;
}
