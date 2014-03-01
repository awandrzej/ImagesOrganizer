import java.util.ArrayList;
import java.util.List;

import com.andrzej.imageTools.ArrayToFile;


public class HelloWorld {

	List <String> listImg = new ArrayList<String>();
	
	public static void main(String[] args) {
		
		HelloWorld t = new HelloWorld();
		t.addElements();
	}
	
	private void addElements  (){
		for (int i=0;i<10;i++){
			listImg.add("name"); 
			}
		System.out.println(this.listImg.get(5));
		

	}
	
}