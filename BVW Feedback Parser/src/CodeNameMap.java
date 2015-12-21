import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public class CodeNameMap {
	String currentDirectory;
	HashMap<String, String> namemap;
	
	public CodeNameMap(){
		currentDirectory = System.getProperty("user.dir");
		namemap = new HashMap<String, String>();
		
		GenerateNameMap();
	}
	
	void GenerateNameMap(){
		String filepath = "resources/codename.csv";
		//File file = new File(currentDirectory + "/" + filepath);

		BufferedReader br = null;
		//Get file from resources folder
		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(Paths.get(currentDirectory + "/" + filepath).toString()));
				
			while ((sCurrentLine = br.readLine()) != null) {
				String[] namepair = sCurrentLine.split(",");
				namemap.put(namepair[0], namepair[1]);
				//System.out.println(namepair[0] + " = " + namepair[1]);
				//sBuffer.append(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//public static void main(String[] args){
	//	CodeNameMap test = new CodeNameMap();
	//}
	
	public String GetCodeName(String realName){
		return namemap.get(realName);
	}
}
