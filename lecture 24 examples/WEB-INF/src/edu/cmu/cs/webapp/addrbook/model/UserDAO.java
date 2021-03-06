package edu.cmu.cs.webapp.addrbook.model;

import org.mybeans.dao.DAOException;
import org.mybeans.dao.GenericDAO;
import org.mybeans.factory.RollbackException;
import org.mybeans.factory.Transaction;

import edu.cmu.cs.webapp.addrbook.databean.User;

public class UserDAO extends GenericDAO<User> {
	public UserDAO() {
		super(User.class,"user","userName");
        
        // Long running web apps need to clean up idle database connections.
        // So we can tell each BeanTable to clean them up.  (You would only notice
        // a problem after leaving your web app running for several hours.)
        getTable().setIdleConnectionCleanup(true);
	}
	
	public void setPassword(String userName, String password) throws DAOException {
        try {
        	Transaction.begin();
			User dbUser = getFactory().lookup(userName);
			
			if (dbUser == null) {
				throw new DAOException("User "+userName+" no longer exists");
			}
			
			dbUser.setPassword(password);
			Transaction.commit();
		} catch (RollbackException e) {
			throw new DAOException(e);
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
}
