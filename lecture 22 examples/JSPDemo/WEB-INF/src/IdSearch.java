import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mybeans.dao.DAOException;

import dao.EntryDAO;
import databeans.Entry;

public class IdSearch extends HttpServlet {
	private EntryDAO dao;

	public void init() throws ServletException {
        ServletContext context = getServletContext();
        String jdbcDriverName = context.getInitParameter("jdbcDriverName");
        String jdbcURL = context.getInitParameter("jdbcURL");
        
        try {
        	dao = new EntryDAO(jdbcDriverName,jdbcURL,true);
        } catch (DAOException e) {
        	throw new ServletException(e);
        }
	}
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idStr = request.getParameter("id");
        
        if (idStr == null) {
            request.setAttribute("error","Invalid parameter: idStr == null");
            RequestDispatcher d = request.getRequestDispatcher("AddrBookError.jsp");
            d.forward(request,response);
            return;
        }

        int id;
       	try {
       		id = Integer.parseInt(idStr);
       	} catch (NumberFormatException e) {
            request.setAttribute("error","Invalid parameter: idStr is not an int");
            RequestDispatcher d = request.getRequestDispatcher("AddrBookError.jsp");
            d.forward(request,response);
            return;
        }
        
        
    	try {
	        Entry e = dao.lookup(id);
	
	        if (e == null) {
	            request.setAttribute("error","No entry for id "+id);
	            RequestDispatcher d = request.getRequestDispatcher("AddrBookError.jsp");
	            d.forward(request,response);
	            return;
	        }
	
	        request.setAttribute("entry",e);
	        RequestDispatcher d = request.getRequestDispatcher("AddrBookEntry.jsp");
	        d.forward(request,response);
	        return;
    	} catch (DAOException e) {
            request.setAttribute("error",e.toString());
            RequestDispatcher d = request.getRequestDispatcher("AddrBookError.jsp");
            d.forward(request,response);
            return;
    	}
    }
}
