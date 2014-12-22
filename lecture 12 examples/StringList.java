import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringList {
    public static void main(String[] args) {
    	List<String> list = new ArrayList<String>();
    	
    	for (String s : args) {
    		list.add(s);
    	}
    	
    	Collections.sort(list);
    	
    	for (String s : list) {
    		System.out.println(s);
    	}
    }
}
