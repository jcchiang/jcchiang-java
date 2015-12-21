import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FeedbackGenration {
	String currentDirectory;
	String roundDirectory;
	
	HashMap<String, ArrayList<OneFeedbackSurvey>> results;
	
	public FeedbackGenration(){
		// Initialization
		currentDirectory = System.getProperty("user.dir");
		roundDirectory = null;
		results = new HashMap<String, ArrayList<OneFeedbackSurvey>>();
		
		roundDirectory = "resources/r1_responses_written/";
		File[] files = new File(currentDirectory + "/" + roundDirectory).listFiles();
		iterateFiles(files);
		
		roundDirectory = "resources/r2_responses_written/";
		files = new File(currentDirectory + "/" + roundDirectory).listFiles();
		iterateFiles(files);
		
		roundDirectory = "resources/r3_responses_written/";
		files = new File(currentDirectory + "/" + roundDirectory).listFiles();
		iterateFiles(files);
		
		roundDirectory = "resources/r4_responses_written/";
		files = new File(currentDirectory + "/" + roundDirectory).listFiles();
		iterateFiles(files);
		
		roundDirectory = "resources/r5_responses_written/";
		files = new File(currentDirectory + "/" + roundDirectory).listFiles();
		iterateFiles(files);
	}
	
	void iterateFiles(File[] files) {
	    for (File file : files) {
	    	parseOneFile(roundDirectory + file.getName());
	    }
	}
	
	public void parseOneFile(String name){
		System.out.println(name);
		ArrayList<OneFeedbackSurvey> thisResult = FeedbackParser.parseFile(name);
		for(int j=0; j<thisResult.size(); j++){
			if(results.get(thisResult.get(j).name) == null){
				// Need to create an ArrayList
				results.put(thisResult.get(j).name, new ArrayList<OneFeedbackSurvey>());
			}
			// Add to ArrayList is enough
			results.get(thisResult.get(j).name).add(thisResult.get(j));
		}
	}
	
	public void generate(){
		int totalDocNum = 0;
		@SuppressWarnings("rawtypes")
		Iterator it = results.entrySet().iterator();
	    while (it.hasNext()) {
	        @SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry)it.next();
	        String studentName = pair.getKey().toString();
	        @SuppressWarnings("unchecked")
			ArrayList<OneFeedbackSurvey> docList = (ArrayList<OneFeedbackSurvey>)pair.getValue();
	        System.out.println(studentName + " = " + docList.size());
	        totalDocNum += docList.size();
	        
	        outputResult(studentName, docList);
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    System.out.println(totalDocNum);
		//outputAvgResult();
		//outputSumResult();
		//outputCombine();
	}
	
	
	void outputResult(String studentName, ArrayList<OneFeedbackSurvey> docList){
		try {
			Collections.shuffle(docList);
			for(int docID=0; docID<docList.size(); docID++){
				String pathname = "output/feedback/" + (studentName) + "/";
				String filename = "output/feedback/" + (studentName) + "/" + (docID+1) + ".html";
				File path = new File(pathname);
				File logFile = new File(filename);
				path.mkdir();
				BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
				
				writer.write(docList.get(docID).getHTML());
				writer.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		FeedbackGenration rc = new FeedbackGenration();
		rc.generate();
	}
	
	
}
