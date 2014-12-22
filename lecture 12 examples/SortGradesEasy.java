

import java.util.Arrays;

public class SortGradesEasy {
	public static void main(String[] args) {

		int[] grades = new int[args.length];

		for (int i = 0; i < args.length; i++) {
			grades[i] = Integer.parseInt(args[i]);
		}

		Arrays.sort(grades);

		for (int i = 0; i < grades.length; i++) {
			System.out.println(grades[i]);
		}

	}
}
