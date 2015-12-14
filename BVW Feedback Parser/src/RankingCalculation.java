import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RankingCalculation {
	String currentDirectory;
	String roundDirectory;
	
	final String[] categories = {"Hours Worked", "Leadership", "Efficiency", "Availability", "Easy to Work With", "Creative Contributions", "Quality of Work"};
	
	ArrayList<SevenValue> list;
	ArrayList<Comparator<SevenValue>> compares;
	
	static int currentCase;
	
	
	CodeNameMap namemap;
	
	public RankingCalculation(){
		// Initialization
		currentDirectory = System.getProperty("user.dir");
		roundDirectory = null;
		list = new ArrayList<SevenValue>();
		compares = new ArrayList<Comparator<SevenValue>>();
		initialComparator();
		
		roundDirectory = "resources/r1_responses_rankings/";
		File[] files = new File(currentDirectory + "/" + roundDirectory).listFiles();
		iterateFiles(files);
		
		roundDirectory = "resources/r2_responses_rankings/";
		files = new File(currentDirectory + "/" + roundDirectory).listFiles();
		iterateFiles(files);
		
		roundDirectory = "resources/r3_responses_rankings/";
		files = new File(currentDirectory + "/" + roundDirectory).listFiles();
		iterateFiles(files);
		
		roundDirectory = "resources/r4_responses_rankings/";
		files = new File(currentDirectory + "/" + roundDirectory).listFiles();
		iterateFiles(files);
		
		roundDirectory = "resources/r5_responses_rankings/";
		files = new File(currentDirectory + "/" + roundDirectory).listFiles();
		iterateFiles(files);
		
		// Create Code Name Map
		namemap = new CodeNameMap();
	}
	
	public void initialComparator(){
		Comparator<SevenValue> aComp;
		aComp = new Comparator<SevenValue>(){
		     public int compare(SevenValue o1, SevenValue o2){
		    	 if(o1.value[0] == o2.value[0])
		             return 0;
		         return o1.value[0] > o2.value[0] ? -1 : 1;
		     }
		};
		compares.add(aComp);
		aComp = new Comparator<SevenValue>(){
		     public int compare(SevenValue o1, SevenValue o2){
		    	 if(o1.value[1] == o2.value[1])
		             return 0;
		         return o1.value[1] > o2.value[1] ? -1 : 1;
		     }
		};
		compares.add(aComp);
		aComp = new Comparator<SevenValue>(){
		     public int compare(SevenValue o1, SevenValue o2){
		    	 if(o1.value[2] == o2.value[2])
		             return 0;
		         return o1.value[2] > o2.value[2] ? -1 : 1;
		     }
		};
		compares.add(aComp);
		aComp = new Comparator<SevenValue>(){
		     public int compare(SevenValue o1, SevenValue o2){
		    	 if(o1.value[3] == o2.value[3])
		             return 0;
		         return o1.value[3] > o2.value[3] ? -1 : 1;
		     }
		};
		compares.add(aComp);
		aComp = new Comparator<SevenValue>(){
		     public int compare(SevenValue o1, SevenValue o2){
		    	 if(o1.value[4] == o2.value[4])
		             return 0;
		         return o1.value[4] > o2.value[4] ? -1 : 1;
		     }
		};
		compares.add(aComp);
		aComp = new Comparator<SevenValue>(){
		     public int compare(SevenValue o1, SevenValue o2){
		    	 if(o1.value[5] == o2.value[5])
		             return 0;
		         return o1.value[5] > o2.value[5] ? -1 : 1;
		     }
		};
		compares.add(aComp);
		aComp = new Comparator<SevenValue>(){
		     public int compare(SevenValue o1, SevenValue o2){
		    	 if(o1.value[6] == o2.value[6])
		             return 0;
		         return o1.value[6] > o2.value[6] ? -1 : 1;
		     }
		};
		compares.add(aComp);
		aComp = new Comparator<SevenValue>(){
		     public int compare(SevenValue o1, SevenValue o2){
		    	 if(o1.sum() == o2.sum())
		             return 0;
		         return o1.sum() > o2.sum() ? -1 : 1;
		     }
		};
		compares.add(aComp);
		aComp = new Comparator<SevenValue>(){
		     public int compare(SevenValue o1, SevenValue o2){
		    	 if(((float)(o1.sum()) / (float)(o1.samples)) == ((float)(o2.sum()) / (float)(o2.samples)))
		             return 0;
		         return ((float)(o1.sum()) / (float)(o1.samples)) > ((float)(o2.sum()) / (float)(o2.samples)) ? -1 : 1;
		     }
		};
		compares.add(aComp);
	}
	
	public static void main(String[] args) {
		RankingCalculation rc = new RankingCalculation();
		rc.calculation();
	}
	
	public void calculation(){
		for(int i=0; i<list.size(); i++){
			list.get(i).print();
		}
		
		outputAvgResult();
		outputSumResult();
		//outputCombine();
	}
	
	public void calcOneFile(String name){
		//System.out.println(name);
		ArrayList<SevenValue> thisResult = SurveyParser.parseFile(name);
		for(int j=0; j<thisResult.size(); j++){
			boolean isFound = false;
			for(int i=0; i<list.size() && !isFound; i++){
				if(list.get(i).name.equals(thisResult.get(j).name)){
					isFound = true;
					list.get(i).add(thisResult.get(j));
				}
			}
			if(!isFound){
				list.add(thisResult.get(j));
			}
		}
	}
	
	public void iterateFiles(File[] files) {
	    for (File file : files) {
	    	calcOneFile(roundDirectory + file.getName());
	    }
	}
	
	public void outputSumResult(){
		try {
			for(currentCase = 0; currentCase < categories.length; currentCase ++){
				String filename = "output/sum/[" + (currentCase + 1) + "]" + categories[currentCase] + ".csv";
				File logFile = new File(filename);
				BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
				
				//Collections.sort(list, compares.get(currentCategoryID));
				Collections.sort(list, new Comparator<SevenValue>(){
				     public int compare(SevenValue o1, SevenValue o2){
				    	 if(o1.value[RankingCalculation.currentCase] == o2.value[RankingCalculation.currentCase])
				             return 0;
				         return (o1.value[RankingCalculation.currentCase] > o2.value[RankingCalculation.currentCase]) ? -1 : 1;
				     }
				});
				
				for(int i=0; i<list.size(); i++){
					//String line = "" + list.get(i).name + "," + list.get(i).value[currentCase] + "\n";
					String line = "" + namemap.GetCodeName(list.get(i).name) + "," + list.get(i).value[currentCase] + "\n";
					writer.write(line);
				}
				writer.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void outputAvgResult(){
		try {
			NumberFormat formatter = new DecimalFormat("0.00");
			for(currentCase = 0; currentCase < categories.length; currentCase ++){
				String filename = "output/avg/[" + (currentCase + 1) + "]" + categories[currentCase] + ".csv";
				File logFile = new File(filename);
				BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
				
				//Collections.sort(list, compares.get(currentCategoryID));
				Collections.sort(list, new Comparator<SevenValue>(){
				     public int compare(SevenValue o1, SevenValue o2){
				    	 if(((float)(o1.value[RankingCalculation.currentCase]) / (float)(o1.samples)) == ((float)(o2.value[RankingCalculation.currentCase]) / (float)(o2.samples)))
				             return 0;
				         return ((float)(o1.value[RankingCalculation.currentCase]) / (float)(o1.samples)) > ((float)(o2.value[RankingCalculation.currentCase]) / (float)(o2.samples)) ? -1 : 1;
				     }
				});
				
				for(int i=0; i<list.size(); i++){
					String line = "" + list.get(i).name + "," + formatter.format((float)(list.get(i).value[currentCase]) / (float)(list.get(i).samples)) + "\n";
					writer.write(line);
				}
				writer.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void outputCombine(){
		try {
			// SUM
			String filename = "output/sum/[SUM]Total Score.csv";
			File logFile = new File(filename);
			BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
			
			Collections.sort(list, compares.get(7));
			
			for(int i=0; i<list.size(); i++){
				String line = "" + list.get(i).name + "," + list.get(i).sum() + "\n";
				writer.write(line);
			}
			writer.close();
			
			// AVG			
			NumberFormat formatter = new DecimalFormat("0.00");
			filename = "output/avg/[AVG]Total Score.csv";
			logFile = new File(filename);
			writer = new BufferedWriter(new FileWriter(logFile));
			
			Collections.sort(list, compares.get(8));
			
			for(int i=0; i<list.size(); i++){
				String line = "" + list.get(i).name + "," + formatter.format((float)(list.get(i).sum()) / (float)(list.get(i).samples)) + "\n";
				writer.write(line);
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
