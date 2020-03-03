import java.io.File;

public class test {

	public static void main(String[] args) {
		
		String filePath = "serverfiles/";
		File file = new File(filePath);
		
		System.out.println(file.isDirectory());
		
		String s1= "get /";
		String s2=s1.substring(3).trim();
		System.out.println(s2);
	}
}
