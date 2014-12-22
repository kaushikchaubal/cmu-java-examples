package dao;

import org.mybeans.dao.DAOException;
import org.mybeans.factory.BeanFactory;
import org.mybeans.factory.BeanFactoryException;
import org.mybeans.factory.BeanTable;
import org.mybeans.factory.MatchArg;
import org.mybeans.factory.RollbackException;

import databeans.Entry;

public class EntryDAO {
	private BeanFactory<Entry> factory;
	
	public EntryDAO(String jdbcDriverName, String jdbcURL, boolean isLongRunning) throws DAOException {
		try {
			BeanTable<Entry> table = BeanTable.getSQLInstance(Entry.class,"entry",jdbcDriverName,jdbcURL);
			table.setIdleConnectionCleanup(isLongRunning);
			if (!table.exists()) table.create("id");
			factory = table.getFactory();
		} catch (BeanFactoryException e) {
			throw new DAOException(e);
		}
	}
	
	public EntryDAO(String fileName) throws DAOException {
		try {
			BeanTable<Entry> table = BeanTable.getCSVInstance(Entry.class,fileName);
			if (!table.exists()) table.create("id");
			factory = table.getFactory();
		} catch (BeanFactoryException e) {
			throw new DAOException(e);
		}
	}

	public Entry lookup(int id) throws DAOException {
		try {
			return factory.lookup(id);
		} catch (RollbackException e) {
			throw new DAOException(e);
		}
	}
	
	public Entry[] lookupByLastName(String lastName) throws DAOException{
		try {
			return factory.match(MatchArg.equals("lastName", lastName));
		} catch (RollbackException e) {
			throw new DAOException(e);
		}
	}
}
