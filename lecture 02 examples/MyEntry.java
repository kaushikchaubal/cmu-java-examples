public class MyEntry extends AddressBookEntry {
    int    age;

    public void birthday() {
        age = age + 1;
    }

    public void print() {
        super.print();
        System.out.println("age: " + age);
    }

    public static void main(String[] args) {
        MyEntry jeff = new MyEntry();
        jeff.firstName = "Jeffrey";
        jeff.lastName  = "Eppinger";
        jeff.telephoneNumber = "412-268-7688";
        jeff.eMailAddress = "eppinger@cmu.edu";
        jeff.age = 50;

        MyEntry barry = new MyEntry();
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
