public class Example1 {
    public static void main(String[] args) {
        double a = Double.parseDouble(args[0]);
        double b = Double.parseDouble(args[1]);
        double sum = a*a + b*b;

        double c = sum/2;
        while (c*c != sum) {
            c = (sum/c + c)/2;
        }

        System.out.println("c=" + c);
    }
}    