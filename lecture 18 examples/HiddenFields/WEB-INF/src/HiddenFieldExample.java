import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HiddenFieldExample extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	
	    String firstname = request.getParameter("firstname");
	    String lastname  = request.getParameter("lastname");
	    String what      = request.getParameter("what");
	
	    out.println("<html>");
	    out.println("<head>");
	    out.println("    <title>Hidden Field Example</title>");
	    out.println("</head>");
	    out.println("<body>");
	    if (firstname == null) {
	        out.println("<form action=\"HiddenFieldExample\" method=\"POST\" />");
	        out.println("    What's your firstname: <input type=\"text\" size=\"40\" name=\"firstname\" />");
	        out.println("    <input type=\"submit\" />");
	        out.println("</form>");
	    } else if (lastname == null) {
	        out.println("<form action=\"HiddenFieldExample\" method=\"POST\" />");
	        out.println("    What's your lastname: <input type=\"text\" size=\"40\" name=\"lastname\" >");
	        out.println("    <input type=\"hidden\" name=\"firstname\" value=\"" + firstname + "\">");
	        out.println("    <input type=\"submit\" />");
	        out.println("</form>");
	    } else if (what == null) {
	        out.println("<form action=\"HiddenFieldExample\" method=\"POST\" />");
	        out.println("    What do you want to buy: <input type=\"text\" size=\"40\" name=\"what\" />");
	        out.println("    <input type=\"hidden\" name=\"firstname\" value=\"" + firstname + "\" />");
	        out.println("    <input type=\"hidden\" name=\"lastname\" value=\"" + lastname + "\" />");
	        out.println("    <input type=\"submit\" />");
	        out.println("</form>");
	    } else {
	        out.println("<h1>Sorry, " + firstname + " " + lastname + ".</h1>");
	        out.println("<h1>We're out of " + what + "!</h1>");
	    }
	    out.println("</body>");
	    out.println("</html>");
	
	}
	
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
