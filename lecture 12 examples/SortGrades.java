

public class SortGrades {
	public static void main(String[] args) {

		int[] grades = new int[args.length];

		for (int i = 0; i < args.length; i++) {
			grades[i] = Integer.parseInt(args[i]);
		}

		// Here�s something interesting�
		for (int i = 0; i < grades.length; i++) {
			for (int j = i + 1; j < grades.length; j++) {
				if (grades[j] < grades[i]) {
					// Swap grades[i] & grades[j]
					int temp = grades[i];
					grades[i] = grades[j];
					grades[j] = temp;
				}
			}
		}

		for (int i = 0; i < grades.length; i++) {
			System.out.println(grades[i]);
		}

	}
}
