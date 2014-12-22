import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FilePrint {
    public static void main(String[] args) {

        if (args.length != 1) {
        	System.out.println("Program to print out any given file as characters.");
            System.out.println("Usage: java FilePrint filename");
            System.exit(1);
        }

        try {
            FileReader fr = new FileReader(args[0]);

            // Count the numbers of characters printed per line in the column variable:
            int column = 0;

            // Read the first character:
            int c = fr.read();

            while (c != -1) {
                // Characters that are less than space (' ') are not printable.
                // Convert them to the control representation (^A, ^B, etc.):
                if (c >= ' ') {
                    System.out.print(" '" + (char)(c) + "' ");
                } else {
                    System.out.print(" '^" + (char)(c+'@') + "'");
                }

                // After printing 10 characters on a line, go to the next:
                column = column + 1;
                if (column > 9) {
                    System.out.println();
                    column = 0;
                }

                // Read the next character:
                c = fr.read();
            }

            fr.close();

        } catch (FileNotFoundException e) {
            System.out.println("File " + args[0] + " was not found");
            System.exit(1);
        } catch (IOException e) {
            System.out.println();
            System.out.println("IOException!");
            System.exit(1);
        } 
    }
}
