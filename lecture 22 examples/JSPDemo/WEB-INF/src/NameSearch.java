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

public class NameSearch extends HttpServlet {
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
        String last  = request.getParameter("lastName");
        
        if (last == null) {
            request.setAttribute("error","Invalid parameter: last==null");
            RequestDispatcher d = request.getRequestDispatcher("AddrBookError.jsp");
            d.forward(request,response);
            return;
        }

    	try {
    		Entry[] list = dao.lookupByLastName(last);

	        if (list.length == 0) {
	            request.setAttribute("error","No entries for "+last);
	            RequestDispatcher d = request.getRequestDispatcher("AddrBookError.jsp");
	            d.forward(request,response);
	            return;
	        }
	
	        if (list.length > 1) {
	            request.setAttribute("list",list);
	            RequestDispatcher d = request.getRequestDispatcher("AddrBookList.jsp");
	            d.forward(request,response);
	            return;
	        }
	
	        request.setAttribute("entry",list[0]);
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
