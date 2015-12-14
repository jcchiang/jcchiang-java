import org.json.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SurveyParser {
	
	public static void main(String[] args) {
		System.out.println(parseFile("resources/r1_responses_rankings/aambre.json"));		
	}
	
	public static ArrayList<SevenValue> parseFile(String filePath) {
		BufferedReader br = null;
		ArrayList<SevenValue> list = new ArrayList<SevenValue>();
		
		StringBuffer sBuffer = new StringBuffer();
		//Get file from resources folder
		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(Paths.get(filePath).toString()));
				
			while ((sCurrentLine = br.readLine()) != null) {
				sBuffer.append(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject obj = new JSONObject(sBuffer.toString());
		
		for(int curNum=1; curNum<=7; curNum++){
			//System.out.println("=== Question " + curNum + " ===");
			String arrayName = new String("q_") + curNum;
			JSONArray arr = obj.getJSONArray(arrayName);
			
			for(int i=0; i<arr.length(); i++){
				String name = arr.getString(i);
				int score = 4-i;
				//System.out.println(score + ":" + name);
				
				if(curNum == 1){
					list.add(new SevenValue(name));
				}
				for(int j=0; j<list.size(); j++){
					if(list.get(j).name.contains(name)){
						list.get(j).setValue(curNum, score);
						break;
					}
				}
			}
		}
		
/*
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
*/
		/*
		for(int j=0; j<list.size(); j++){
			list.get(j).print();
		}
		*/
		return list;

	  }
}
