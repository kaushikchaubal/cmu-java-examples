import java.math.BigDecimal;

/*
 * Jeff Eppinger
 * Lecture 10 Demo Example
 * September 22, 2010
 * Course 15-600
 */

public class BigFactorial {
    public static BigDecimal factorial(BigDecimal n) {
    	if (n.compareTo(BigDecimal.ZERO) < 0) {
    		throw new IllegalArgumentException("Negative number factorials hurt my brain.");
    	}

        if (n.equals(BigDecimal.ZERO)) return BigDecimal.ONE;
        return n.multiply(factorial(n.subtract(BigDecimal.ONE)));
    }

    public static void main(String[] args) {
    	try {
	   		BigDecimal n = new BigDecimal(args[0]);
			BigDecimal nFact = factorial(n);
			System.out.println(nFact);
		} catch (NumberFormatException e) {
			System.out.println("This is not a number: "+args[0]);
		} catch (IllegalArgumentException e) {
			System.out.println("Houston, we have a problem: "+e.getMessage());
			e.printStackTrace();
		}
    }
}
