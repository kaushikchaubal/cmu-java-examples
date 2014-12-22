import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListPosition {
	private static DecimalFormat df = new DecimalFormat("#,###");

	public static void main(String[] args) {
		List<String> list;

		if (args.length == 0) {
			System.out.println("java ListPosition ArrayList|LinkedList [<size>]");
			return;
		}

		int size;
		if (args.length == 1) {
			size = IntPrompter.promptLine("How big should I make the "+args[0]+"?","10000");
		} else {
			size = Integer.parseInt(args[1]);
		}
		
		
		if (args[0].equals("ArrayList")) {
			list = new ArrayList<String>(size);
		} else if (args[0].equals("LinkedList")) {
			list = new LinkedList<String>();
		} else {
			throw new AssertionError(args[0]);
		}

		long constructionStartTime = System.currentTimeMillis();
		for (int i=0; i<size; i++) {
			list.add(AlphaNumber.alphatize(i));
		}
		long constructionEndTime = System.currentTimeMillis();
		System.out.println("Building "+args[0]+" with "+df.format(size)+" elements takes "+
				df.format(constructionEndTime-constructionStartTime)+" ms");
		
		while (true) {
			int pos = IntPrompter.promptLine("Position>","-1");
			if (pos < 0) return;
			
			long start = System.currentTimeMillis();
			
			String s = list.get(pos);
			
			long end   = System.currentTimeMillis();
			
			System.out.println("found="+s);
			System.out.println("time="+df.format(end-start)+" ms");
		}
	}
}
