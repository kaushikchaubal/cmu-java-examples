public class ReverseDigits {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        while (x > 0) {
            int digit = x % 10;
            System.out.print(digit);
            x = x/10;
        }
    }
}
