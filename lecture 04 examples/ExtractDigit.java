/*
 * Jeff Eppinger
 * 15-600
 * Homework #2 Prep
 * 9/2/10
 */

public class ExtractDigit {
	public static void main(String[] args) {
		String s = args[0];
		System.out.println(s);
		
		System.out.println(s.charAt(0));
		System.out.println(s.charAt(1));
		System.out.println(s.charAt(2));
		System.out.println(s.charAt(3));
		
		int d1 = s.charAt(0) - '0';
		System.out.println(d1);
		
		System.out.println(s.substring(0,1));
		
		System.out.println("------------");
		
		long x = Long.parseLong(s);
		System.out.println(x % 10);
		System.out.println(x / 10 % 10);
		System.out.println(x / 100 % 10);
	}
}
