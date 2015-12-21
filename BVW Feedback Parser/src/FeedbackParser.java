import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.*;

public class FeedbackParser {
	
	public static void main(String[] args) {
		ArrayList<OneFeedbackSurvey> aList = parseFile("resources/r1_responses_written/aambre.json");
		for(int j=0; j<aList.size(); j++){
			System.out.println(aList.get(j).getHTML());
		}
	}
	
	public static ArrayList<OneFeedbackSurvey> parseFile(String filePath) {
		BufferedReader br = null;
		ArrayList<OneFeedbackSurvey> list = new ArrayList<OneFeedbackSurvey>();
		//Get file from resources folder
		
		StringBuffer sBuffer = new StringBuffer();
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
		
		//System.out.println(JSONObject.getNames(obj)[0]);
		
		for(int i=0; i<obj.length(); i++){
			String arrayName = JSONObject.getNames(obj)[i];
			JSONArray arr = obj.getJSONArray(arrayName);
			
			String[] splittedString = arrayName.split("_");
			
			int ID = Integer.valueOf(splittedString[1]);
			String name = splittedString[2];
			System.out.println("name:" + name);
			
			OneFeedbackSurvey aSurvey = null;
			// Check duplication or not
			for(int cursor=0; cursor<list.size(); cursor++){
				if(list.get(cursor).name.contains(name)){
					aSurvey = list.get(cursor);
					break;
				}
			}
			if(aSurvey == null){
				aSurvey = new OneFeedbackSurvey(name);
				//needSave = true;
				list.add(aSurvey);
			}
			
			// Add Strings
			for(int j=0; j<arr.length(); j++){
				if(ID % 2 == 1){
					// GOOD POINTS
					aSurvey.addGJ(arr.getString(j));
				}
				else{
					// IMPROVEMENT POINTS
					aSurvey.addImprove(arr.getString(j));	
				}
			}
		}
		
		return list;
	}
}
