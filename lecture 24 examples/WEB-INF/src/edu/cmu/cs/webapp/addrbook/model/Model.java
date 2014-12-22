package edu.cmu.cs.webapp.addrbook.model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.mybeans.factory.BeanTable;


public class Model {
	private LogDAO    logDAO;
	private EntryDAO  entryDAO;
	private UserDAO   userDAO;

	private String  localNetAddr;
	private String  localNetPrefix;
	private boolean requireSSL;

	public Model(ServletConfig config) throws ServletException {
		localNetAddr   = config.getInitParameter("localNetAddr");
        localNetPrefix = config.getInitParameter("localNetPrefix");
        requireSSL     = new Boolean(config.getInitParameter("requireSSL"));       

		String jdbcDriver = config.getInitParameter("jdbcDriverName");
	    String jdbcURL    = config.getInitParameter("jdbcURL");
	    BeanTable.useJDBC(jdbcDriver,jdbcURL);

    	userDAO  = new UserDAO();
    	entryDAO = new EntryDAO();

    	String logDir = config.getInitParameter("logDirectory");
    	if (logDir != null && logDir.length() > 0) {
    		logDAO = LogDAO.getFileInstance(logDir+"/log.csv");
    	} else {
    		logDAO = LogDAO.getInstance();
    	}
	}
	
	public EntryDAO  getEntryDAO()       { return entryDAO;       }
	public String    getLocalNetAddr()   { return localNetAddr;   }
	public String    getLocalNetPrefix() { return localNetPrefix; }
	public LogDAO    getLogDAO()         { return logDAO;         }
	public boolean   getRequireSSL()     { return requireSSL;     }
	public UserDAO   getUserDAO()        { return userDAO;        }
}
