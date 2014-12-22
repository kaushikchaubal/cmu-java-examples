

public class SimpleUser {
	private String userName;
	private String password;

	public SimpleUser(String userName) { this.userName = userName; }

	public String getPassword()        { return password;          }
	public String getUserName()        { return userName;          }
	
	public void setPassword(String s)  { password = s;             }
}
