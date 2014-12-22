public class AddressBookEntry {
    String firstName;
    String lastName;
    String telephoneNumber;
    String eMailAddress;

    public void print() {
        System.out.println();
        System.out.println("Name:      " + firstName + " " + lastName);
        System.out.println("Telephone: " + telephoneNumber);
        System.out.println("E-mail:    " + eMailAddress);
    }

    public static void main(String[] args) {
        AddressBookEntry jeff = new AddressBookEntry();
        jeff.firstName = "Jeffrey";
        jeff.lastName  = "Eppinger";
        jeff.telephoneNumber = "412-268-7688";
        jeff.eMailAddress = "eppinger@cmu.edu";

        AddressBookEntry barry = new AddressBookEntry();
        barry.firstName = "Barack";
        barry.lastName  = "Obama";
        barry.telephoneNumber = "202-456-1414";
        barry.eMailAddress = "potus@whitehouse.gov";

        jeff.print();
        barry.print();

    }
}
