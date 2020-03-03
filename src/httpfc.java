import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class httpfc {
	public static void main(String[] args) throws IOException {
		
		if (args.length != 2) {System.out.println("wrong cmmand."); System.exit(1);}
		String cmd = "";
		cmd = args[0]+" "+args[1];
		try {
			Socket socket = new Socket("localhost",4444);		
			String requestContent=getContent();
			sendToServer(cmd,socket,requestContent);
		}catch(ConnectException e) {
			System.err.println("Server connection issue.");
		}
		
	}
	
	
	private static void sendToServer(String cmd,Socket socket, String requestContent) throws IOException {
		PrintWriter pw = new PrintWriter(socket.getOutputStream());
		System.out.println("Sending request to server:");
		System.out.println("--------------------------");
		System.out.println("command:");
		System.out.println(cmd);
		if (cmd.toLowerCase().startsWith("post")) {
			System.out.println("Request body:");
			System.out.println(requestContent);
		}		
		System.out.println("--------------------------");
		pw.println(cmd);
		pw.println(requestContent);
		pw.flush();
		BufferedReader br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String response = "";	
		String line ="";
		while ((line = br.readLine()) != null) {
			response+=line+"\r\n";
		}
		System.out.println(response);		
		
	}
	
	private static String getContent() throws IOException {
		String content="";
		BufferedReader br = new BufferedReader(new FileReader("clientfiles/request_content.txt"));
		String line="";
		while ((line = br.readLine())!=null) {
			content+=line;
		}
		br.close();
		return content;
	}
	
	
	
	
	
	
	
	
	
	

}
