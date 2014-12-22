/*
 * Jeff Eppinger
 * Lecture 10 Demo Example
 * September 22, 2010
 * Course 15-600
 */

public class Factorial {
    public static int factorial(int n) {

    	if (n < 0) {
    		throw new IllegalArgumentException("Negative number factorials hurt my brain.");
    	}

    	if (n == 0) return 1;
        return n * factorial(n-1);
    }

    public static void main(String[] args) {
    	try {
       		int n = Integer.parseInt(args[0]);
    		int nFact = factorial(n);
    		System.out.println(nFact);
    	} catch (NumberFormatException e) {
    		System.out.println("This is not a number: "+args[0]);
    	} catch (IllegalArgumentException e) {
    		System.out.println("Houston, we have a problem: "+e.getMessage());
    		e.printStackTrace();
    	}
    }
}
