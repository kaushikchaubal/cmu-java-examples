public class CubesEasilyFormatted {
	public static void main(String args[]) {
		if (args.length != 3) {
			System.out.println("Usage: java CubeTest <countStart> <increment> <n>");
			System.out.println("  Program to print out a table of cubes and cube roots.");
			System.out.println("  The table starts computing roots at the int <countStart>");
			System.out.println("  and continues by increments of <increment> for <n> rows.");
			System.exit(1);
		}

		int countStart = Integer.parseInt(args[0]);
		int increment = Integer.parseInt(args[1]);
		int n = Integer.parseInt(args[2]);

		// Print header
		System.out.println("Number           Cube        Cube Root");
		System.out.println("--------------------------------------");

		// Print table
		for (int i = 0; i < n; i++) {
			int number = countStart + i * increment;
			int cube = number * number * number;
			double cubeRoot = Math.pow(number, 1.0 / 3.0);
			
			System.out.printf("%1$6d %2$,14d %3$16.3f\n", number, cube, cubeRoot);
		}
	}
}