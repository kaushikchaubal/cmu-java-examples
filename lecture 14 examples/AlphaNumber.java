public class AlphaNumber {

	public static void main(String[] args) {
		long x = Long.parseLong(args[0]);
		System.out.println(alphatize(x));
	}

	public static String alphatize(long x) {
		if (x >= 0) return fifteenDigit(x);
		return "minus "+ fifteenDigit(-x);
	}
	
	private static String oneDigit(int x) {
		switch (x) {
			case 0:  return "zero";
			case 1:  return "one";
			case 2:  return "two";
			case 3:  return "three";
			case 4:  return "four";
			case 5:  return "five";
			case 6:  return "six";
			case 7:  return "seven";
			case 8:  return "eight";
			case 9:  return "nine";
			default: throw new IllegalArgumentException(String.valueOf(x));
		}
	}

	private static String teen(int x) {
		if (x < 10) return oneDigit(x);
		
		switch (x) {
			case 10: return "ten";
			case 11: return "eleven";
			case 12: return "twelve";
			case 13: return "thirteen";
			case 14: return "fourteen";
			case 15: return "fifteen";
			case 16: return "sixteen";
			case 17: return "seventeen";
			case 18: return "eighteen";
			case 19: return "nineteen";
			default: throw new IllegalArgumentException(String.valueOf(x));
		}
	}

	private static String twoDigit(int x) {
		if (x < 0 || x >= 100) throw new IllegalArgumentException(String.valueOf(x));
		
		if (x < 10) return oneDigit(x);
		if (x < 20) return teen(x);
		
		int tens = x / 10;
		int remainder = x % 10;
		
		String suffix;
		if (remainder == 0) {
			suffix = "";
		} else {
			suffix = "-" + oneDigit(remainder);
		}
		
		switch (tens) {
			case 2: return "twenty" + suffix;
			case 3: return "thirty" + suffix;
			case 4: return "forty"  + suffix;
			case 5: return "fifty"  + suffix;
			case 6: return "sixty"  + suffix;
			case 7: return "seventy"+ suffix;
			case 8: return "eighty" + suffix;
			case 9: return "ninety" + suffix;
		}
		
		throw new AssertionError("Can't get here: "+x);
	}
	
	private static String threeDigit(int x) {
		if (x < 0 || x >= 1000) throw new IllegalArgumentException(String.valueOf(x));
		
		if (x < 100) return twoDigit(x);
		
		int hundreds = x / 100;
		int remainder = x % 100;
		
		String suffix;
		if (remainder == 0) {
			suffix = "";
		} else {
			suffix = " " + twoDigit(remainder);
		}

		return oneDigit(hundreds) + " hundred" + suffix;
	}
	
	private static final int MILLION = 1000 * 1000;
	
	private static String sixDigit(int x) {
		if (x < 0 || x >= MILLION) throw new IllegalArgumentException(String.valueOf(x));
		
		if (x < 1000) return threeDigit(x);
		
		int thousands = x / 1000;
		int remainder = x % 1000;
		
		String suffix;
		if (remainder == 0) {
			suffix = "";
		} else {
			suffix = " " + threeDigit(remainder);
		}

		return threeDigit(thousands) + " thousand" + suffix;
	}
	
	private static final int BILLION = MILLION * 1000;

	private static String nineDigit(int x) {
		if (x < 0 || x >= BILLION) throw new IllegalArgumentException(String.valueOf(x));
		
		if (x < MILLION) return sixDigit(x);
		
		int millions = x / MILLION;
		int remainder = x % MILLION;
		
		String suffix;
		if (remainder == 0) {
			suffix = "";
		} else {
			suffix = " " + sixDigit(remainder);
		}

		return threeDigit(millions) + " million" + suffix;
	}
	
	private static final long TRILLION = BILLION * 1000L;
	
	private static String twelveDigit(long x) {
		if (x < 0 || x >= TRILLION) throw new IllegalArgumentException(String.valueOf(x));
		
		if (x < BILLION) return nineDigit( (int) x );
		
		int billions  = (int) (x / BILLION);
		int remainder = (int) (x % BILLION);
		
		String suffix;
		if (remainder == 0) {
			suffix = "";
		} else {
			suffix = " " + nineDigit(remainder);
		}

		return threeDigit(billions) + " billion" + suffix;
	}

	private static final long QUADRILLION = TRILLION * 1000L;
	
	private static String fifteenDigit(long x) {
		if (x < 0 || x >= QUADRILLION) throw new IllegalArgumentException(String.valueOf(x));
		
		if (x < TRILLION) return twelveDigit( x );
		
		int  trillions  = (int) (x / TRILLION);
		long remainder = x % TRILLION;
		
		String suffix;
		if (remainder == 0) {
			suffix = "";
		} else {
			suffix = " " + twelveDigit(remainder);
		}

		return threeDigit(trillions) + " trillion" + suffix;
	}
}
