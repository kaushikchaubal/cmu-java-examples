import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CountTestBuffered {
    public static void main(String[] args) {

        if (args.length != 1) {
        	System.out.println("A program to count the number of characters in a given file.");
            System.out.println("Usage: java CountTestBuffered filename");
            System.exit(1);
        }

        try {
            long startTime = System.currentTimeMillis();

            FileReader fr = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(fr);

            long count = 0;

            // Read the first character:
            int c = br.read();

            while (c != -1) {
                count = count + 1;
                // Read the next character:
                c = br.read();
            }

            br.close();
            fr.close();

            long endTime = System.currentTimeMillis();

            System.out.println(count + " characters counted in " +
                               (endTime-startTime) + " milliseconds");

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
