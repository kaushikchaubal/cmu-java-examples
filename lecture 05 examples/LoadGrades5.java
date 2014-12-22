public class LoadGrades5 {
  public static void main(String[] args) {
    int[] grades = new int[args.length];
    for (int i=0; i<args.length; i++) {
      grades[i] = Integer.parseInt(args[i]);
    }
    // insert something interesting here
    for (int g : grades) System.out.println(g);
  }
}

