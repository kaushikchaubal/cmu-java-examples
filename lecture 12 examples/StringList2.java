import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StringList2 {
    public static void main(String[] args) {
    	List<String> list = new ArrayList<String>();
    	
    	for (String s : args) {
    		list.add(s);
    	}

    	Collections.sort(list, new MyComparator());
    	
    	for (String s : list) {
    		System.out.println(s);
    	}
    }
    
    private static class MyComparator implements Comparator<String> {
    	public int compare(String s1, String s2) {
    		return s1.compareTo(s2);
    	}
    }
}
