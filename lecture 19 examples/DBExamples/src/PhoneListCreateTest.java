import java.sql.*;

public class PhoneListCreateTest {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }

        try {
        	Connection con = DriverManager.getConnection("jdbc:mysql:///test");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("create table phonelist (id int not null auto_increment," +
            		" lastname varchar(20), firstname varchar(20), phonenumber varchar(20)," +
            		" primary key(id))");
            stmt.close();
            System.out.println("Successfully created phonelist table");
            con.close();
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
    }
}
