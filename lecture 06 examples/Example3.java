public class Example3 {
    public static double hypotenuse(double a,
                                    double b) {
        return Math.sqrt(a*a + b*b);
    }

    public static void main(String[] args) {
        double a = Double.parseDouble(args[0]);
        double b = Double.parseDouble(args[1]);
        double c = hypotenuse(a,b);
        System.out.println("c=" + c);
    }
}    
