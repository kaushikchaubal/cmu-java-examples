public class AddressBookEntry2 {
    String firstName;
    String lastName;
    String telephoneNumber;
    String eMailAddress;
    int    age;

    public void print() {
        System.out.println();
        System.out.println("Name:      " + firstName + " " + lastName);
        System.out.println("Telephone: " + telephoneNumber);
        System.out.println("E-mail:    " + eMailAddress);
        System.out.println("Age:       " + age);
    }

    public void birthday() {
        age = age + 1;
    }

    public static void main(String[] args) {
        AddressBookEntry2 jeff = new AddressBookEntry2();
        jeff.firstName = "Jeffrey";
        jeff.lastName  = "Eppinger";
        jeff.telephoneNumber = "412-268-7688";
        jeff.eMailAddress = "eppinger@cmu.edu";
        jeff.age = 50;

        AddressBookEntry2 barry = new AddressBookEntry2();
        barry.firstName = "Barack";
        barry.lastName  = "Obama";
        barry.telephoneNumber = "202-456-1414";
        barry.eMailAddress = "potus@whitehouse.gov";
        barry.age = 49;

        jeff.print();
        barry.print();

        jeff.birthday();
        barry.birthday();
        barry.birthday();

        jeff.print();
        barry.print();

    }
}
