
/**
 * Class for storing one BVW Peer Evaluation Feedback survey
 * @author jcchiang
 *
 */
public class OneFeedbackSurvey {
	public String name;
	public String[] greatjobs, improvements;
	
	private int gj_count = 0, imp_count = 0;
	
	public OneFeedbackSurvey(String name){
		this.name = name;
		greatjobs = new String[]{"","",""};
		improvements = new String[]{"", "", ""};
	}
	
	/**
	 * Add a line for what this person did great
	 * @param gj the sentence string from other person
	 */
	public void addGJ(String gj){
		if(gj_count < greatjobs.length){
			greatjobs[gj_count] = gj.toString();
			gj_count++;
		}
	}
	
	/**
	 * Add a line for what this person can improve
	 * @param imp the sentence string from other person
	 */
	public void addImprove(String imp){
		if(imp_count < improvements.length){
			improvements[imp_count] = imp.toString();
			imp_count++;
		}
	}
	
	public String getHTML(){
		StringBuffer test = new StringBuffer();
		test.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'> <html xmlns='http://www.w3.org/1999/xhtml'> <head> <style type='text/css'> .title { font-family: Helvetica, Verdana, Arial, sans-serif; text-align: center; } .content{font-size: 20px;} </style> </head> <body> <div class='title'> <h1>BVW Peer Evaluation Feedback Survey</h1> </div> <br> <h2>Dear ");
		test.append(name);
		test.append(":</h2> <br> <h3>The best three things about working with you were:</h4> <ul> <li class='content'>");
		test.append(greatjobs[0]);
		test.append("</li> <br> <li class='content'>");
		test.append(greatjobs[1]);
		test.append("</li> <br> <li class='content'>");
		test.append(greatjobs[2]);
		test.append("<br> </ul> <br> <h3>In my opinion, the three things you should focus on improving are:</h4> <ul> <li class='content'>");
		test.append(improvements[0]);
		test.append("</li> <br> <li class='content'>");
		test.append(improvements[1]);
		test.append("</li> <br> <li class='content'>");
		test.append(improvements[2]);
		test.append("</li> <br> </ul> </body> </html>");
		return test.toString();
	}
	
	// For Debug Usage
	public void print(){
		String nameString = "To " + name + ":";
		String valueString = "";
		
		for(int i=0; i<3; i++){
			valueString += i + ":" + greatjobs[i] + "\n";
		}
		for(int i=0; i<3; i++){
			valueString += i + ":" + improvements[i] + "\n";
		}
		
		System.out.println(nameString + "\n" + valueString);
	}
}
