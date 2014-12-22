import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class HashMapLookup {
	private static DecimalFormat df = new DecimalFormat("#,###");
	public static void main(String[] args) {
		Map<Integer,String> map = new HashMap<Integer,String>();

		int size;
		if (args.length == 0) {
			size = IntPrompter.promptLine("How big should I make the list?","10000");
		} else {
			size = Integer.parseInt(args[0]);
		}
		

		long constructionStartTime = System.currentTimeMillis();
		for (int i=0; i<size; i++) {
			map.put(i,AlphaNumber.alphatize(i));
		}
		long constructionEndTime = System.currentTimeMillis();
		System.out.println("Building "+size+" with "+df.format(size)+" elements take "+
				df.format(constructionEndTime-constructionStartTime)+" ms");
		
		
		while (true) {
			int x = IntPrompter.promptLine("Lookup>","-1");
			if (x < 0) return;
			
			long start = System.currentTimeMillis();
			
			String s = map.get(x);
			
			long end   = System.currentTimeMillis();
			
			System.out.println("found="+s);
			System.out.println("time="+df.format(end-start)+" ms");
		}
	}
}
