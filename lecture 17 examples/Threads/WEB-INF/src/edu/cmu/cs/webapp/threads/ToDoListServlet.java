package edu.cmu.cs.webapp.threads;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ToDoListServlet extends HttpServlet {
	private ToDoListDAO dao = new BrokenToDoListDAO();  // The Data Access Object
	private Logger      logger = Logger.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    	logger.logRequest(request);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");

        // Generate the HTML <head>
        out.println("  <head>");
        out.println("    <meta http-equiv=\"cache-control\" content=\"no-cache\">");
        out.println("    <meta http-equiv=\"pragma\" content=\"no-cache\">");
        out.println("    <meta http-equiv=\"expires\" content=\"0\">");
        out.println("    <title>To Do List</title>");
        out.println("  </head>");

        out.println("<body>");
        out.println("<h2>Web App To Do List</h2>");

        // Generate an HTML <form> to get data from the user
        out.println("<form method=\"GET\">");
        out.println("  <table>");
        out.println("  <tr><td colspan=\"3\"><hr/></td></tr>");
        out.println("  <tr>");
        out.println("    <td>Item to Add:</td>");
        out.println("    <td colspan=\"2\"><input type=\"text\" size=\"40\" name=\"addText\"/></td>");
        out.println("  </tr>");
        out.println("  <tr>");
        out.println("    <td/>");
        out.println("    <td><input type=\"submit\" name=\"what\" value=\"Add to Top\"></td>");
        out.println("    <td><input type=\"submit\" name=\"what\" value=\"Add to Bottom\"></td>");
        out.println("  </tr>");
        out.println("  <tr><td colspan=\"3\"><hr/></td></tr>");
        out.println("  <tr>");
        out.println("    <td>Item Number:</td>");
        out.println("    <td><input type=\"test\" size=\"10\" name=\"deleteNum\"></td>");
        out.println("    <td><input type=\"submit\" name=\"what\" value=\"Delete\"></td>");
        out.println("  </tr>");
        out.println("  <tr><td colspan=\"3\"><hr/></td></tr>");
        out.println("  </table>");
        out.println("</form>");


        // Look at the parameters.  Add or data item as requested
        String what = request.getParameter("what");
        
        if (what != null) {
            if (what.equals("Add to Top")) {
            	System.out.println("ToDoList: "+request.getRemoteAddr()+": Add to Top \""+request.getParameter("addText")+"\"");
            	dao.addToTop(request.getParameter("addText"));
               	out.println("<p>Added item in position 1</p>");
            } else if (what.equals("Add to Bottom")) {
            	System.out.println("ToDoList: "+request.getRemoteAddr()+": Add to Bottom \""+request.getParameter("addText")+"\"");
            	int pos =dao.addToBottom(request.getParameter("addText"));
            	out.println("<p>Added item in position "+pos+"</p>");
            } else if (what.equals("Delete")) {
            	System.out.println("ToDoList: "+request.getRemoteAddr()+": Delete \""+request.getParameter("deleteNum")+"\"");
            	boolean b = dao.delete(request.getParameter("deleteNum"));
            	if (b) {
            		out.println("<p>Deleted item in position "+request.getParameter("deleteNum")+"</p>");
            	} else {
            		out.println("<p>There is no item in position "+request.getParameter("deleteNum")+"</p>");
            	}
            }
        }
 
        out.println("<p>The list now has "+dao.size()+" items.</p>");
        for (int i=0; i<dao.size(); i++) {
        	out.println((i+1)+". "+dao.getItem(i)+"<br/>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}

