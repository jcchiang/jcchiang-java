
public class SevenValue {
	public String name;
	public int samples;
	public int[] value;
	
	public SevenValue(String name){
		this.name = name;
		value = new int[]{0,0,0,0,0,0,0};
		samples = 1;
	}
	
	public void setValue(int attributeNumber, int point){
		if(attributeNumber < 1 || attributeNumber > 7)
			return;
		
		value[attributeNumber - 1] = point;
	}
	
	public void add(SevenValue other){
		for(int i=0; i<7; i++){
			value[i] += other.value[i];
		}
		samples ++;
	}
	
	public int sum(){
		int sum = 0;
		for(int i=0; i<7; i++){
			sum += value[i];
		}
		return sum;
	}
	
	public void print(){
		String nameString = "Name: " + name;
		String valueString = "Value: ";
		
		for(int i=0; i<7; i++){
			valueString += value[i] + " ";
		}
		
		System.out.println(nameString + "\n" + valueString);
	}
}
