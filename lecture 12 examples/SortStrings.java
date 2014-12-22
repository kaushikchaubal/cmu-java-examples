

public class SortStrings {
	public static void main(String[] args) {

		System.out.println("Before sort:");
		for (int i = 0; i < args.length; i++) {
			System.out.println("    " + args[i]);
		}

		for (int i = 0; i < args.length; i++) {
			for (int j = i + 1; j < args.length; j++) {
				if (args[i].length() < args[j].length()) {
					// Swap args[i] & args[j]
					String temp = args[i];
					args[i] = args[j];
					args[j] = temp;
				}
			}
		}

		System.out.println("After sort:");
		for (int i = 0; i < args.length; i++) {
			System.out.println("    " + args[i]);
		}
	}
}
