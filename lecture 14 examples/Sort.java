import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Sort {
	private static DecimalFormat df = new DecimalFormat("#,###");

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();

		if (args.length == 0) {
			System.out.println("java Sort <size>");
			return;
		}
		
		int size = Integer.parseInt(args[0]);
		
		long constructionStartTime = System.currentTimeMillis();
		for (int i=0; i<size; i++) {
			list.add(AlphaNumber.alphatize(i));
		}
		long constructionEndTime = System.currentTimeMillis();
		System.out.println("Building ArrayList with "+df.format(size)+" elements takes "+
				df.format(constructionEndTime-constructionStartTime)+" ms");
		

		long sortStartTime = System.currentTimeMillis();
		
		java.util.Collections.sort(list);
		
		long sortEndTime = System.currentTimeMillis();
		System.out.println("Sorting ArrayList with "+df.format(size)+" elements takes "+
				df.format(sortEndTime-sortStartTime)+" ms");
		
		if (args.length > 1) {
			for (String s : list) {
				System.out.println(s);
			}
		}
	}
}
