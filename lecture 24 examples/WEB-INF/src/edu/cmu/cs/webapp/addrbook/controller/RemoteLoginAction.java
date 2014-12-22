package edu.cmu.cs.webapp.addrbook.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.dao.DAOException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.addrbook.databean.User;
import edu.cmu.cs.webapp.addrbook.formbean.LoginForm;
import edu.cmu.cs.webapp.addrbook.model.Model;

public class RemoteLoginAction extends Action {
	private static FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);


	private Model model;

	public RemoteLoginAction(Model model) {
		this.model = model;
	}

	public String getName() { return "remote-login.do"; }
    
    public String perform(HttpServletRequest request) {
        try {
        	int numEntries = model.getEntryDAO().getCount();
			request.setAttribute("numEntries",numEntries);

        	List<String> errors = new ArrayList<String>();
            request.setAttribute("errors",errors);

	    	User user = (User) request.getSession(true).getAttribute("user");
	    	if (user != null) {
	    		errors.add("You are already logged in.");
	    		return "search.jsp";
	    	}

			LoginForm form = formBeanFactory.create(request);
			request.setAttribute("form",form);

			int numUsers = model.getUserDAO().getCount();
        	if (numUsers == 0) {
        		errors.add(getNoUserWarning());
        		return "remote-login.jsp";
        	}

			if (!form.isPresent()) return "remote-login.jsp";

	    	errors.addAll(form.getValidationErrors());
	    	if (errors.size() > 0) return "remote-login.jsp";
	    	
	    	user = model.getUserDAO().lookup(form.getUserName());
	    	if (user == null || !user.checkPassword(form.getPassword())) {
	    		errors.add("Incorrect user name / password");
	    		return "remote-login.jsp";
	    	}
	    	
	    	request.getSession(true).setAttribute("user",user);
	    	return "search.jsp";
        } catch (DAOException e) {
        	e.printStackTrace();
        	request.setAttribute("dbError",e.getMessage());
        	return "db-error.jsp";
		} catch (FormBeanException e) {
        	e.printStackTrace();
        	request.setAttribute("formError",e.getMessage());
        	return "form-error.jsp";
		}
    }
    
    private String getNoUserWarning() {
    	StringBuffer b = new StringBuffer();
    	b.append("Warning: no users configured for this application.  ");

    	String localPrefix = model.getLocalNetPrefix();
    	String localAddr = model.getLocalNetAddr();
    	if (localPrefix == null && localAddr == null) {
    		b.append("Specify either \"localNetAddr\" or \"localNetPrefix\" in the web.xml file ");
    		b.append("and then connect locally to configure the initial user.");
    		return b.toString();
    	}
    	
    	b.append("Configure initial using by connecting locally from ");
    	if (localAddr != null) {
    		b.append(localAddr);
    		if (localPrefix != null) b.append(" or ");
    	}
    	
    	if (localPrefix != null) {
    		b.append("subnet ");
    		b.append(localPrefix);
    	}
    	
    	b.append(" to configure the initial user.");
    	return b.toString();
    }
}
