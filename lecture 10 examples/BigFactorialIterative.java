import java.math.BigDecimal;

/*
 * Jeff Eppinger
 * Lecture 10 Demo Example
 * September 22, 2010
 * Course 15-600
 */

public class BigFactorialIterative {
    public static void main(String[] args) {
   		BigDecimal n = new BigDecimal(args[0]);

   		if (n.compareTo(BigDecimal.ZERO) < 0) {
    		System.out.println("Houston, we have a problem: Negative number factorials hurt my brain.");
    		return;
    	}

		BigDecimal answer = BigDecimal.ONE;
		for (BigDecimal i=BigDecimal.ONE; i.compareTo(n) <= 0; i=i.add(BigDecimal.ONE)) {
			answer = answer.multiply(i);
		}
		
		System.out.println(answer);
    }
}
