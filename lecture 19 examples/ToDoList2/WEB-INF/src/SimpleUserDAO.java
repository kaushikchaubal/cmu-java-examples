import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SimpleUserDAO {
	private List<Connection> connectionPool = new ArrayList<Connection>();

	private String jdbcDriver;
	private String jdbcURL;
	
	public SimpleUserDAO(String jdbcDriver, String jdbcURL) {
		this.jdbcDriver = jdbcDriver;
		this.jdbcURL    = jdbcURL;
		
		try {
			createTable();
		} catch (MyDAOException e) {
			// Ignore ... if thrown assume it's because table already exists
			// If it's some other problem we'll fail later on
		}
	}
	
	private synchronized Connection getConnection() throws MyDAOException {
		if (connectionPool.size() > 0) {
			return connectionPool.remove(connectionPool.size()-1);
		}
		
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            throw new MyDAOException(e);
        }

        try {
            return DriverManager.getConnection(jdbcURL);
        } catch (SQLException e) {
            throw new MyDAOException(e);
        }
	}
	
	private synchronized void releaseConnection(Connection con) {
		connectionPool.add(con);
	}


	public SimpleUser create(String userName) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("INSERT INTO user (userName,password) VALUES (?,?)");
        	
    		SimpleUser newUser = new SimpleUser(userName);
        	pstmt.setString(1,userName);
        	pstmt.setString(2,newUser.getPassword());
        	int count = pstmt.executeUpdate();
        	if (count != 1) throw new SQLException("Insert updated "+count+" rows");
        	
        	pstmt.close();
        	releaseConnection(con);
        	return newUser;
        	
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}

	public SimpleUser lookup(String userName) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM user WHERE userName=?");
        	pstmt.setString(1,userName);
        	ResultSet rs = pstmt.executeQuery();
        	
        	SimpleUser user;
        	if (!rs.next()) {
        		user = null;
        	} else {
        		user = new SimpleUser(rs.getString("userName"));
        		user.setPassword(rs.getString("password"));
        	}
        	
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
            return user;
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}
	
	public void setPassword(String userName, String password) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("UPDATE user SET password=? WHERE userName=?");
        	pstmt.setString(1,password);
        	pstmt.setString(2,userName);
        	int count = pstmt.executeUpdate();
        	pstmt.close();
        	
        	if (count == 0) throw new MyDAOException("No such user: "+userName);
        	if (count > 1)  throw new MyDAOException("Update modified "+count+" rows!");
        	
        	releaseConnection(con);
        	
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	if (e instanceof MyDAOException) throw (MyDAOException) e;
            throw new MyDAOException(e);
        }
	}

	private void createTable() throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE TABLE user (userName VARCHAR(255) NOT NULL, password VARCHAR(255), PRIMARY KEY(userName))");
            stmt.close();
        	
        	releaseConnection(con);

        } catch (SQLException e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
    }
}
