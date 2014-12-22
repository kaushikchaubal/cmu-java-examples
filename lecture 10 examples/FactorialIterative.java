/*
 * Jeff Eppinger
 * Lecture 10 Demo Example
 * September 22, 2010
 * Course 15-600
 */

public class FactorialIterative {
    public static void main(String[] args) {
    	try {
       		int n = Integer.parseInt(args[0]);
       		
       		if (n < 0) {
        		System.out.println("Houston, we have a problem: Negative number factorials hurt my brain.");
        		return;
       		}
       		
       		int answer = 1;
       		for (int i=1; i<=n; i++) {
       			answer = answer * i;
       		}

       		System.out.println(answer);
    	} catch (NumberFormatException e) {
    		System.out.println("This is not a number: "+args[0]);
    	}
    }
}
