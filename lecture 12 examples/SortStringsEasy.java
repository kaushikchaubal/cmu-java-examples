import java.util.Arrays;

public class SortStringsEasy {
	public static void main(String[] args) {

		System.out.println("Before sort:");
		for (int i = 0; i < args.length; i++) {
			System.out.println("    " + args[i]);
		}

		Arrays.sort(args);

		System.out.println("After sort:");
		for (int i = 0; i < args.length; i++) {
			System.out.println("    " + args[i]);
		}
	}
}
