import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ToDoList2 extends HttpServlet {
	private SimpleUserDAO userDAO;
	private ToDoListDAO toDoListDAO;

	
	public void init() throws ServletException {
    	String jdbcDriver = getInitParameter("jdbcDriver");
    	String jdbcURL    = getInitParameter("jdbcURL");
    	
    	userDAO     = new SimpleUserDAO(jdbcDriver,jdbcURL);
    	toDoListDAO = new ToDoListDAOSQL(jdbcDriver,jdbcURL);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
        	login(request,response);
        } else {
        	manageList(request,response);
        }
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request,response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		List<String> errors = new ArrayList<String>();
       	
   		String userName = request.getParameter("userName");
   		String password = request.getParameter("password");
   		String button   = request.getParameter("button");
   		
    	if (userName == null && password == null && button == null) {
    		outputLoginPage(response,userName,errors);
    		return;
    	}
	
   		if (userName == null || userName.length() == 0) {
   			errors.add("User Name is required");
   		}
   		
   		if (password == null || password.length() == 0) {
   			errors.add("Password is required");
   		}
   		
   		if (button == null || !(button.equals("Login") || button.equals("Register"))) {
   			errors.add("Invalid button");
   		}

       	if (errors.size() != 0) {
    		outputLoginPage(response,userName,errors);
    		return;
    	}

       	try {
           	SimpleUser user;
       		
       		if (button.equals("Register")) {
       			user = userDAO.create(userName);
       			userDAO.setPassword(userName,password);
       		} else {
		       	user = userDAO.lookup(userName);
		       	if (user == null) {
		       		errors.add("No such user");
		    		outputLoginPage(response,userName,errors);
		    		return;
		       	}
		       	
		       	if (!password.equals(user.getPassword())) {
		       		errors.add("Incorrect password");
		    		outputLoginPage(response,userName,errors);
		    		return;
		       	}
       		}
	    	
	       	HttpSession session = request.getSession();
	       	session.setAttribute("user",user);
	       	System.out.println("ToDoList: login: user="+user.getUserName());
	       	manageList(request,response);
       	} catch (MyDAOException e) {
       		errors.add(e.getMessage());
       		outputLoginPage(response,userName,errors);
       	}
	}

    private void manageList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Look at the what parameter to see what we're doing to the list
        String what = request.getParameter("what");
        
        if (what == null) {
        	// No change to list requested
        	outputToDoList(response);
        	return;
        }
        
        if (what.equals("X")) {
        	processDelete(request,response);
        	return;
        }
        
        if (what.equals("Add to Top")) {
        	processAdd(request,response,true);
        	return;
        }

        if (what.equals("Add to Bottom")) {
        	processAdd(request,response,false);
        	return;
        }

        outputToDoList(response,"No such operation: "+what);
	}
    
    private void processAdd(HttpServletRequest request, HttpServletResponse response, boolean addToTop) throws ServletException, IOException {
   		List<String> errors = new ArrayList<String>();
   		
   		String item = request.getParameter("item");
   		
   		if (item == null || item.length() == 0) {
   			errors.add("Must provide item to be added to the list");
        	outputToDoList(response,errors);
        	return;
   		}
   		
    	if (addToTop) {
            System.out.println("ToDoList: "+request.getRemoteAddr()+": Add to Top \""+item+"\"");
    		toDoListDAO.addToTop(item);
    	} else {
            System.out.println("ToDoList: "+request.getRemoteAddr()+": Add to Bottom \""+item+"\"");
    		toDoListDAO.addToBottom(item);
    	}
    	outputToDoList(response,"Item Added");
	}
    
    private void processDelete(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
   		List<String> errors = new ArrayList<String>();

   		String idStr = request.getParameter("id");
   		
   		if (idStr == null) {
   			errors.add("Must provide the number to delete");
        	outputToDoList(response,errors);
        	return;
   		}
   		
   		int id;
   		try {
   			id = Integer.parseInt(idStr);
   		} catch (NumberFormatException e) {
   			errors.add("Invalid number: "+idStr);
        	outputToDoList(response,errors);
        	return;
   		}
   		
        System.out.println("ToDoList: "+request.getRemoteAddr()+": Delete \""+id+"\"");
    	toDoListDAO.delete(id);
    	outputToDoList(response,"Item Deleted");
	}

    // Methods that generate & output HTML
    
    private void generateHead(PrintWriter out) {
	    out.println("  <head>");
	    out.println("    <meta http-equiv=\"cache-control\" content=\"no-cache\">");
	    out.println("    <meta http-equiv=\"pragma\" content=\"no-cache\">");
	    out.println("    <meta http-equiv=\"expires\" content=\"0\">");
	    out.println("    <title>To Do List Login</title>");
	    out.println("  </head>");
    }
    
    private void outputLoginPage(HttpServletResponse response, String userName, List<String> errors) throws IOException {
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	
	    out.println("<html>");
	
	    generateHead(out);
	
	    out.println("<body>");
	    out.println("<h2>Welcome to Innovations 2011</h2>");
	    
	    if (errors != null && errors.size() > 0) {
	    	for (String error : errors) {
	        	out.println("<p style=\"font-size: large; color: red\">");
	        	out.println(error);
	        	out.println("</p>");
	    	}
	    }
	
	    // Generate an HTML <form> to get data from the user
        out.println("<form method=\"POST\">");
        out.println("    <table/>");
        out.println("        <tr>");
        out.println("            <td style=\"font-size: x-large\">User Name:</td>");
        out.println("            <td>");
        out.println("                <input type=\"text\" name=\"userName\"");
        if (userName != null) {
        	out.println("                    value=\""+userName+"\"");
        }
        out.println("                />");
        out.println("            <td>");
        out.println("        </tr>");
        out.println("        <tr>");
        out.println("            <td style=\"font-size: x-large\">Password:</td>");
        out.println("            <td><input type=\"password\" name=\"password\" /></td>");
        out.println("        </tr>");
        out.println("        <tr>");
        out.println("            <td colspan=\"2\" align=\"center\">");
        out.println("                <input type=\"submit\" name=\"button\" value=\"Login\" />");
        out.println("                <input type=\"submit\" name=\"button\" value=\"Register\" />");
        out.println("            </td>");
        out.println("        </tr>");
        out.println("    </table>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
	}
    
    private void outputToDoList(HttpServletResponse response) throws IOException {
    	// Just call the version that takes a List passing an empty List
    	List<String> list = new ArrayList<String>();
    	outputToDoList(response,list);
    }
   
    private void outputToDoList(HttpServletResponse response, String message) throws IOException {
    	// Just put the message into a List and call the version that takes a List
    	List<String> list = new ArrayList<String>();
    	list.add(message);
    	outputToDoList(response,list);
    }
    
    private void outputToDoList(HttpServletResponse response, List<String> messages) throws IOException {
    	// Get the list of items to display at the end
    	String[] items = toDoListDAO.getItems();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");

        generateHead(out);

        out.println("<body>");
        out.println("<h2>Web App To Do List</h2>");

        // Generate an HTML <form> to get data from the user
        out.println("<form method=\"POST\">");
        out.println("    <table>");
        out.println("        <tr><td colspan=\"3\"><hr/></td></tr>");
        out.println("        <tr>");
        out.println("            <td style=\"font-size: large\">Item to Add:</td>");
        out.println("            <td colspan=\"2\"><input type=\"text\" size=\"40\" name=\"item\"/></td>");
        out.println("        </tr>");
        out.println("        <tr>");
        out.println("            <td/>");
        out.println("            <td><input type=\"submit\" name=\"what\" value=\"Add to Top\"/></td>");
        out.println("            <td><input type=\"submit\" name=\"what\" value=\"Add to Bottom\"/></td>");
        out.println("        </tr>");
        out.println("        <tr><td colspan=\"3\"><hr/></td></tr>");
        out.println("    </table>");
        out.println("</form>");

        for (String message : messages) {
        	out.println("<p style=\"font-size: large; color: red\">");
        	out.println(message);
        	out.println("</p>");
        }
 
        out.println("<p style=\"font-size: x-large\">The list now has "+items.length+" items.</p>");
        out.println("<table>");
        for (int i=0; i<items.length; i++) {
        	out.println("    <tr>");
        	out.println("        <td>");
        	out.println("            <form method=\"POST\">");
        	out.println("                <input type=\"hidden\" name=\"id\" value=\""+(i+1)+"\" />");
        	out.println("                <input type=\"submit\" name=\"what\" value=\"X\" />");
        	out.println("            </form>");
        	out.println("        </td>");
        	out.println("        <td valign=\"top\" style=\"font-size: x-large\">&nbsp;"+(i+1)+".&nbsp;</td>");
        	out.println("        <td valign=\"top\" style=\"font-size: x-large\">"+items[i]+"</td>");
        	out.println("    </tr>");
        }
        out.println("</table>");

        out.println("</body>");
        out.println("</html>");
    }
}