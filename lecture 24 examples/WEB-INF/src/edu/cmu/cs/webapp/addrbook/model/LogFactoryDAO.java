package edu.cmu.cs.webapp.addrbook.model;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.dao.DAOException;
import org.mybeans.factory.BeanFactory;
import org.mybeans.factory.BeanTable;
import org.mybeans.factory.RollbackException;
import org.mybeans.factory.Transaction;

import edu.cmu.cs.webapp.addrbook.databean.LogRec;
import edu.cmu.cs.webapp.addrbook.databean.User;

class LogFactoryDAO extends LogDAO {
	private BeanFactory<LogRec> factory;
	
	public LogFactoryDAO() {
		// Get a BeanTable so we can create the "log" table
        BeanTable<LogRec> table = BeanTable.getInstance(LogRec.class,"log");
        
        if (!table.exists()) table.create("recNo");
        
        // Long running web apps need to clean up idle database connections.
        // So we can tell each BeanTable to clean them up.  (You would only notice
        // a problem after leaving your web app running for several hours.)
        table.setIdleConnectionCleanup(true);

        // Get a BeanFactory which the actions will use to read and write rows of the "log" table
        factory = table.getFactory();
	}

    public void write(HttpServletRequest request, String text) throws DAOException {
    	try {
       		Transaction.begin();

       		LogRec rec = factory.create();
    		rec.setIpAddr(request.getRemoteAddr());
    		rec.setTime(new Date());
    		rec.setText(text);

    		User user = (User) request.getSession(true).getAttribute("user");
    		String userName = user == null ? null : user.getUserName();
    		rec.setUserName(userName);
    		
    		Transaction.commit();
    	} catch (RollbackException e) {
    		throw new DAOException (e);
    	}
    }
}
