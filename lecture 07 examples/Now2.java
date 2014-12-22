public class Now2 {
	public static void main(String[] args) {
	    long now = System.currentTimeMillis();
    	java.text.DecimalFormat df;
	    df = new java.text.DecimalFormat("#,###");
    	System.out.println(df.format(now));
	}
}
