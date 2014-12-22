import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ToDoListDAOSQL implements ToDoListDAO {
	private List<Connection> connectionPool = new ArrayList<Connection>();
	
	private String jdbcDriver;
	private String jdbcURL;
	private String tableName;
	
	public ToDoListDAOSQL(String jdbcDriver, String jdbcURL) {
		this.jdbcDriver = jdbcDriver;
		this.jdbcURL    = jdbcURL;
		this.tableName  = "todolist";
		
		createTable();
	}

	private synchronized Connection getConnection() {
		if (connectionPool.size() > 0) {
			return connectionPool.remove(connectionPool.size()-1);
		}
		
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }

        try {
            return DriverManager.getConnection(jdbcURL);
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
	}
	
	private synchronized void releaseConnection(Connection con) {
		connectionPool.add(con);
	}
	
	private void createTable() {
    	Connection con = getConnection();
    	try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(
            		"create table "+tableName+
            		" (id int not null auto_increment," +
            		" item varchar(255)," +
            		" primary key(id))");
            stmt.close();
            releaseConnection(con);
        } catch (SQLException e) {
            try { con.close(); } catch (SQLException e2) { /* ignore */ }
            System.out.println("Exception creating table: "+e);
            System.out.println("...assume it already exists");
        }
	}
		
	public void addToTop(String item) {
    	try {
        	Connection con = getConnection();
        	con.setAutoCommit(false);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select min(id) from "+tableName+" for update");
            rs.next();
            int minId = rs.getInt("min(id)");
            rs.close();
            stmt.close();
            
            int newId = minId - 1;
            if (newId == 0) newId = -1; // don't use zero

            PreparedStatement pstmt = con.prepareStatement(
            		"insert into "+tableName+"(id,item) values (?,?)");
            pstmt.setInt(1,newId);
            pstmt.setString(2, item);
            pstmt.executeUpdate();
            pstmt.close();
            
            con.commit();
            con.setAutoCommit(true);
            
            releaseConnection(con);
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
	}
	
	public int addToBottom(String item) {
    	try {
        	Connection con = getConnection();
        	con.setAutoCommit(false);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select max(id) from "+tableName+" for update");
            rs.next();
            int maxId = rs.getInt("max(id)");
            rs.close();
            stmt.close();
            
            int newId = maxId + 1;
            if (newId == 0) newId = 1; // don't use zero

            PreparedStatement pstmt = con.prepareStatement(
            		"insert into "+tableName+"(id,item) values (?,?)");
            pstmt.setInt(1,newId);
            pstmt.setString(2, item);
            pstmt.executeUpdate();
            pstmt.close();
            
            stmt = con.createStatement();
            rs = stmt.executeQuery("select count(item) from "+tableName);
            rs.next();
            int count = rs.getInt("count(item)");
            
            rs.close();
            stmt.close();
            
            con.commit();
            con.setAutoCommit(true);
            releaseConnection(con);
            
            return count;
            
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
	}
	
	public synchronized boolean delete(int position) {
		throw new UnsupportedOperationException();
	}

	public synchronized String getItem(int position) {
		throw new UnsupportedOperationException();
	}
	
	public String[] getItems() {
    	try {
        	Connection con = getConnection();
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select item from "+tableName+" order by id");

            List<String> list = new ArrayList<String>();
            while (rs.next()) {
            	list.add(rs.getString("item"));
            }
            rs.close();
            stmt.close();
            releaseConnection(con);
            
            return list.toArray(new String[list.size()]);
            
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
	}
	
	public int size() {
    	try {
        	Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select count(item) from "+tableName);
            rs.next();
            int count = rs.getInt("count(item)");
            
            rs.close();
            stmt.close();
            releaseConnection(con);
            
            return count;
            
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
	}
}
