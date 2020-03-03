import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class httpfs {
	
	private static String serverFilePath = "serverfiles";
	private static int port = 8080;
	private static boolean debugging;
	
	public static void main(String[] args) throws IOException {
		
				
		
		ServerSocket serverSocket = new ServerSocket(4444);
		Socket socket;
		
		try {
			for (int i= 0; i<args.length;i++) {
				if (args[i].toLowerCase().equals("-v")) {
					debugging = true;
				}
				if (args[i].toLowerCase().equals("-p")) {
					port = Integer.parseInt(args[i+1]);
				}
				if (args[i].toLowerCase().equals("-d")) {
					serverFilePath = args[i+1];
				}
			}
		}catch(Exception e){
			System.err.println("Incorrect server cmd.");
		}
		
		while(true) {
			socket = serverSocket.accept();
			System.out.println("client connected.");
			
			new multiRequest(socket,serverFilePath);
			
		}
		
	}
}












