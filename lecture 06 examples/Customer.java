public class Customer {
    private static int lastCustNum = 0;

    public static int getNumCustomers() {
        return lastCustNum / 11;
    }

    private int    customerNumber;
    private String firstName;
    private String lastName;

    public Customer(String first, String last) {
        firstName = first;
        lastName = last;
        lastCustNum += 11;
        customerNumber = lastCustNum;
    }

    public String getFirstName() { return firstName;      }
    public String getLastName()  { return lastName;       }
    public int    getCustNum()   { return customerNumber; }
    public void   setFirstName(String s) { firstName = s; }
    public void   setLastName(String s)  { lastName = s;  }

    public String toString() {
        return "Customer(#"+customerNumber+", "+lastName+", "+firstName+")";
    }

}
