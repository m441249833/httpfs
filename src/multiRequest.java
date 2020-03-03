import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class multiRequest extends Thread{

	private Socket socket;
	private String serverFilePath;
	public multiRequest(Socket socket, String serverFilePath) {
		this.socket = socket;
		this.serverFilePath=serverFilePath;
		start();
		
		
	}
	
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String request = br.readLine();
			String requestContent = br.readLine();
			String response = processRequest(request,socket,requestContent);		
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.println("Status:200 "+socket.getLocalAddress()+"\r\n");
			pw.println(response);
			pw.flush();
			br.close();
		}catch (IOException e){
			System.out.println("Access denied.");
		}
	}
	
	private String processRequest(String request, Socket socket,String requestContent){
		String response = "";
		String filePath = "";
		if (request.toLowerCase().startsWith("get")) {
			filePath = serverFilePath.concat(request.substring(3).trim());
			System.out.println("Command GET "+request.substring(3).trim()+" received.");
			File file = new File(filePath);
			//System.out.println(file.isDirectory());
			try {
				if (file.isDirectory()) {
					File[] files = file.listFiles();
					for (File f:files) {
						response +=f.getName()+"\n";
					}
					
				}else if (file.isFile() && file.exists()) {
					String line = null;
					BufferedReader br = new BufferedReader(new FileReader(file));
					while ((line = br.readLine()) != null) {
						response += line+"\n";
					}
					
				}else {
					throw new FileNotFoundException();
				}
			}catch(FileNotFoundException e) {
				response = "404 file not found.";
			}catch(IOException e) {
				response = "Access denied.";
			}
			
		}else if(request.toLowerCase().startsWith("post")) {
			filePath = serverFilePath+request.substring(4).trim();
			System.out.println("Command POST "+request.substring(4).trim()+" received.");
			//TODO server post method
			File file = new File(filePath);
			try {
				FileWriter fw = new FileWriter(file);
				System.out.println("Writing to file "+file.getAbsolutePath());
				fw.write(requestContent);
				fw.close();
				response = "File created on server successfully.";
				
			}catch(IOException e) {
				response = "Access denied.";
			}
			
		}else {
			System.out.println("Incorrect GET/POST cmd.");
			System.exit(1);
		}
			

		return response;
	}
	
}
