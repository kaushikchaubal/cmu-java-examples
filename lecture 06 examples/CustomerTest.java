public class CustomerTest {
    public static void main(String[] args) {
        Customer[] list = new Customer[5];
        list[0] = new Customer("George","Bush");
        list[1] = new Customer("Barack","Obama");
        list[2] = new Customer("Bill","Clinton");
        list[3] = new Customer("Jeb","Bush");
        list[4] = new Customer("Hillary","Clinton");

        for (int i=0; i<list.length; i++) {
            System.out.println(list[i]);
        }

        System.out.println(Customer.getNumCustomers() + " customers");
    }
}
