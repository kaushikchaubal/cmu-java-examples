package edu.cmu.cs.webapp.addrbook.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mybeans.dao.DAOException;

import edu.cmu.cs.webapp.addrbook.databean.User;
import edu.cmu.cs.webapp.addrbook.model.Model;

public class Controller extends HttpServlet {
	Model model;
	
    public void init() throws ServletException {
        model = new Model(getServletConfig());

        Action.add(new AddEntryAction(model));
        Action.add(new CreateUserAction(model));
        Action.add(new DeleteEntryAction(model));
        Action.add(new DeleteUserAction(model));
        Action.add(new LoginAction(model));
        Action.add(new LogoutAction(model));
        Action.add(new LookupEntryAction(model));
        Action.add(new ManageUsersAction(model));
        Action.add(new ModifyEntryAction(model));
        Action.add(new RemoteLoginAction(model));
        Action.add(new SearchAction(model));
        Action.add(new SetPasswordAction(model));
        Action.add(new UpdateEntryAction(model));

        // Additional Actions for Advanced Search
        Action.add(new AdvancedSearchAction(model));
        Action.add(new AdvancedSearchAjaxAction(model));
        Action.add(new SearchAjaxAction(model));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (model.getRequireSSL() && !request.isSecure()) {
			// If we're required to use SSL and we're not on a secure connection, redirect
        	String host = request.getServerName();
        	String port = (request.getServerPort()==80) ? "" : ":8443";
            response.sendRedirect("https://"+host+port+request.getRequestURI());
            return;
        }

        try {
	        model.getLogDAO().write(request, "doGet: "+request.getRequestURI());
	        String nextPage = performTheAction(request);
	        sendToNextPage(nextPage,request,response);
        } catch (DAOException e) {
        	e.printStackTrace();
        	request.setAttribute("dbError",e.getMessage());
	        sendToNextPage("db-error.jsp",request,response);
        }
    }
    
    /*
     * Extracts the requested action and (depending on whether the user is logged in)
     * perform it (or make the user login).
     * @param request
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) {
        HttpSession session     = request.getSession(true);
        String      servletPath = request.getServletPath();
        User        user        = (User) session.getAttribute("user");
        String      actionName  = getActionName(servletPath);
        
        // if the user is not logged in and is not local, make him log in for access
        if (user == null && !isLocalIPAddress(request.getRemoteAddr())) {
			return Action.perform("remote-login.do",request);
        }
        
        // if there are no users, make this user (who must be local because no one cannot login)
        // create an initial user (presumable for himself)
        try {
        	int numUsers = model.getUserDAO().getCount();
        	if (numUsers == 0) return Action.perform("create-user.do",request);
        } catch (DAOException e) {
        	// Perhaps the DB is down
        	// Skip the initial user check and just proceed with the users requested
        	// (which will fail, also, if the database is down
        }
        
        if (actionName.equals("start")) {
        	// if the user is local or logged-in start on the search page
			return Action.perform("search.do",request);
        }

      	// Let the local or logged-in user run his chosen action
		return Action.perform(actionName,request);
    }
    
    /*
     * If nextPage is null, send back 404
     * If nextPage starts with a '/', redirect to this page.
     *    In this case, the page must be the whole servlet path including the webapp name
     * Otherwise dispatch to the page (the view)
     *    This is the common case
     * Note: If nextPage equals "image", we will dispatch to /image.  In the web.xml file, "/image"
     *    is mapped to the ImageServlet will which return the image bytes for display.
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	// System.out.println("nextPage="+nextPage);
    	
    	if (nextPage == null) {
    		response.sendError(HttpServletResponse.SC_NOT_FOUND,request.getServletPath());
    		return;
    	}
    	
    	if (nextPage.charAt(0) == '/') {
    		String proto = request.isSecure() ? "https://" : "http://";
			String host  = request.getServerName();
			String port  = ":"+String.valueOf(request.getServerPort());
			if (port.equals(":80")) port = "";
			if (port.equals(":443")) port = "";
			String context = request.getContextPath();
			int lastSlash = context.lastIndexOf('/');
			String prefix = ( lastSlash==0 ? context : context.substring(0,lastSlash) );
			response.sendRedirect(proto+host+port+prefix+nextPage);
			return;
    	}
    	
   		RequestDispatcher d = request.getRequestDispatcher("/view/"+nextPage);
   		d.forward(request,response);
    }

	/*
	 * Returns the path component after the last slash
	 */
    private String getActionName(String path) {
        int slash = path.lastIndexOf('/');
        return path.substring(slash+1);
    }
    
    private boolean isLocalIPAddress(String addr) {
    	String localPrefix = model.getLocalNetPrefix();
    	if (localPrefix != null && addr.startsWith(localPrefix)) return true;

    	String localAddr = model.getLocalNetAddr();
    	if (localAddr != null && addr.equals(localAddr)) return true;
    	
    	return false;
    }
}
