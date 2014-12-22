public class PrintArgs {
    public static void main(String[] args) {

        // Prints out the arguments
        for (int i=0; i<args.length; i++) {
            System.out.println("args[" +
            i + "]=\"" + args[i]+ "\"");
        }
    }
}
