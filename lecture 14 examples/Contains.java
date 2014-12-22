import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public class Contains {
	private static DecimalFormat df = new DecimalFormat("#,###");

	public static void main(String[] args) {
		Collection<String> collection;

		if (args.length == 0) {
			System.out.println("java Contains ArrayList|HashSet|LinkedList [<size>]");
			return;
		}
		
		if (args[0].equals("ArrayList")) {
			collection = new ArrayList<String>();
		} else if (args[0].equals("HashSet")) {
			collection = new HashSet<String>();
		} else if (args[0].equals("LinkedList")) {
			collection = new LinkedList<String>();
		} else {
			throw new AssertionError(args[0]);
		}
		
		int size;
		if (args.length == 1) {
			size = IntPrompter.promptLine("How big should I make the "+args[0]+"?","10000");
		} else {
			size = Integer.parseInt(args[1]);
		}
		

		long constructionStartTime = System.currentTimeMillis();
		for (int i=0; i<size; i++) {
			collection.add(AlphaNumber.alphatize(i));
		}
		long constructionEndTime = System.currentTimeMillis();
		System.out.println("Building "+args[0]+" with "+df.format(size)+" elements take "+
				df.format(constructionEndTime-constructionStartTime)+" ms");
		
		while (true) {
			String key = TextPrompter.promptLine("Search key>","done");
			if (key.equals("done")) return;
			
			long start = System.currentTimeMillis();
			
			boolean found = collection.contains(key);
			
			long end   = System.currentTimeMillis();
			
			System.out.println("found="+found+", time="+df.format(end-start)+" ms");
		}
	}
}
