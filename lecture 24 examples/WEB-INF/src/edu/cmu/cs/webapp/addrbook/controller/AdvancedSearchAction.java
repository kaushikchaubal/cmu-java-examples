package edu.cmu.cs.webapp.addrbook.controller;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.dao.DAOException;

import edu.cmu.cs.webapp.addrbook.model.EntryDAO;
import edu.cmu.cs.webapp.addrbook.model.Model;

public class AdvancedSearchAction extends Action {
	private EntryDAO entryDAO;

    public AdvancedSearchAction(Model model) {
		entryDAO = model.getEntryDAO();
	}

    public String getName() { return "advanced-search.do"; }

    public String perform(HttpServletRequest request) {
        try {
			int numEntries = entryDAO.getCount();
			request.setAttribute("numEntries",numEntries);
			
			return "advanced-search.jsp";
        } catch (DAOException e) {
        	e.printStackTrace();
        	request.setAttribute("dbError",e.getMessage());
        	return "db-error.jsp";
		}
    }
}
