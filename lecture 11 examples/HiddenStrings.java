import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HiddenStrings {
    public static void main(String[] args) {

        if (args.length != 1) {
        	System.out.println("A program to print the printable strings in a file that are more than four characters long.");
            System.out.println("Usage: java HiddenStrings filename");
            System.exit(1);
        }

        try {
            FileReader fr = new FileReader(args[0]);

            // Read the first character:
            int c = fr.read();

            while (c != -1) {

                // If it's a printable character (and doesn't start with a space)
                // create a StringBuffer and start filling it up.
                if (c > ' ' && c <= '~') {
                    StringBuffer data = new StringBuffer();

                    // File the StringBuffer with up to 80 printable characters:
                    while (c != -1 && c >= ' ' && c <= '~' && data.length() < 80) {
                        data.append((char) c);
                        c = fr.read();
                    }

                    // If we have more than 4 printable characters, print it out!
                    if (data.length() > 4) {
                        System.out.println(data);
                    }
                }
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
