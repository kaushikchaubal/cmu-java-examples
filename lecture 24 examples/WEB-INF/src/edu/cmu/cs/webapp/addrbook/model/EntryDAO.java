package edu.cmu.cs.webapp.addrbook.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.mybeans.dao.DAOException;
import org.mybeans.dao.GenericDAO;
import org.mybeans.factory.MatchArg;
import org.mybeans.factory.RollbackException;
import org.mybeans.factory.Transaction;

import edu.cmu.cs.webapp.addrbook.databean.Entry;
import edu.cmu.cs.webapp.addrbook.formbean.AdvancedSearchForm;

public class EntryDAO extends GenericDAO<Entry> {
	private MyComparator comparator = new MyComparator(false);
	private MyComparator spouseComparator = new MyComparator(true);

	public EntryDAO() {
		super(Entry.class,"entry","id");
        setUseAutoIncrementOnCreate(true);
        
        // Long running web apps need to clean up idle database connections (for MySQL anyway).
        // (You would only notice a problem after leaving your web app running for several hours.)
        setIdleConnectionCleanup(true);
	}
	
	public String computeDigest(Entry entry) throws DAOException {
		try {
			return getFactory().computeDigest(entry);
		} catch (RollbackException e) {
			throw new DAOException(e);
		}
	}
	
	private void addIfNotEmpty(List<MatchArg> constraintList, String propName, String value) {
		if (value == null) return;
		if (value.length() == 0) return;
		constraintList.add(MatchArg.containsIgnoreCase(propName,value));
	}
	
	public Entry[] lookupAdvanced(AdvancedSearchForm form) throws DAOException {
		List<MatchArg> constraintList = new ArrayList<MatchArg>();
		
		if (form.getLastName().length() > 0) {
			constraintList.add(
					MatchArg.or(
							MatchArg.containsIgnoreCase("lastName", form.getLastName()),
							MatchArg.containsIgnoreCase("spouseLast", form.getLastName())));
		}

		if (form.getFirstName().length() > 0) {
			constraintList.add(
					MatchArg.or(
							MatchArg.containsIgnoreCase("firstNames", form.getFirstName()),
							MatchArg.containsIgnoreCase("spouseFirst", form.getFirstName())));
		}


		if (form.getAnyPhone().length() > 0) {
			constraintList.add(
					MatchArg.or(
							MatchArg.containsIgnoreCase("cellPhone", form.getAnyPhone()),
							MatchArg.containsIgnoreCase("fax", form.getAnyPhone()),
							MatchArg.containsIgnoreCase("homePhone", form.getAnyPhone()),
							MatchArg.containsIgnoreCase("workPhone", form.getAnyPhone()),
							MatchArg.containsIgnoreCase("spouseCell", form.getAnyPhone()),
							MatchArg.containsIgnoreCase("spouseWork", form.getAnyPhone())));
		}

		if (form.getEmail().length() > 0) {
			constraintList.add(
					MatchArg.or(
							MatchArg.containsIgnoreCase("email", form.getEmail()),
							MatchArg.containsIgnoreCase("spouseEmail", form.getEmail())));
		}

		addIfNotEmpty(constraintList,"additional",form.getAdditional());
		addIfNotEmpty(constraintList,"address",form.getAddress());
		addIfNotEmpty(constraintList,"city",form.getCity());
		addIfNotEmpty(constraintList,"state",form.getState());
		addIfNotEmpty(constraintList,"country",form.getCountry());
		addIfNotEmpty(constraintList,"zip",form.getZip());

		addIfNotEmpty(constraintList,"receivedCards",form.getReceivedCards());
		addIfNotEmpty(constraintList,"sentCards",form.getSentCards());


		if (constraintList.size() == 0) return new Entry[0];
		
		try {
			MatchArg[] constraints = constraintList.toArray(new MatchArg[constraintList.size()]);
			Entry[] list = getFactory().match(constraints);
			Arrays.sort(list,comparator);
			return list;
    	} catch (RollbackException e) {
    		throw new DAOException(e);
    	}
	}

	public Entry[] lookupStartsWith(String startOfLast, String startOfFirst) throws DAOException {
    	try {
			Entry[] list = getFactory().match(
						MatchArg.startsWithIgnoreCase("lastName",startOfLast),
						MatchArg.startsWithIgnoreCase("firstNames",startOfFirst));
			Arrays.sort(list,comparator);
			return list;
    	} catch (RollbackException e) {
    		throw new DAOException(e);
    	}
	}

	public Entry[] lookupSpouseStartsWith(String startOfLast, String startOfFirst) throws DAOException {
    	try {
			Entry[] list = getFactory().match(
						MatchArg.startsWithIgnoreCase("spouseLast",startOfLast),
						MatchArg.startsWithIgnoreCase("spouseFirst",startOfFirst));
			Arrays.sort(list,spouseComparator);
			return list;
    	} catch (RollbackException e) {
    		throw new DAOException(e);
    	}
	}
	
	public Entry update(String oldDigest, Entry newEntry) throws DAOException {
		try {
			Transaction.begin();
			
			int id = newEntry.getId();
			Entry dbEntry = getFactory().lookup(id);
			if (dbEntry == null) {
				throw new DAOException("No entry in database: id="+newEntry.getId()+".  (Someone else must have just deleted it.)");
			}
			
			if (!oldDigest.equals(getFactory().computeDigest(dbEntry))) {
				throw new DAOException("Entry in database has been changed by someone else.  (Enter your changes again.)");
			}
			
			Entry oldEntry = new Entry(id);
			getFactory().copyInto(dbEntry,oldEntry);
			getFactory().copyInto(newEntry,dbEntry);
			Transaction.commit();
			
			// return oldEntry so that we can write into the log the old and new values
			return oldEntry;
		} catch (RollbackException e) {
			throw new DAOException(e);
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	
	private static class MyComparator implements Comparator<Entry> {
		private boolean spouseCompare;
		
		public MyComparator(boolean spouseCompare) {
			this.spouseCompare = spouseCompare;
		}
		
		// Sorts by lastname then firstname or spouse's last then spouse's first,
		// depending on setting of spouseCompare variable.
		public int compare(Entry e1, Entry e2) {
			int lastCompare;
			if (spouseCompare) {
				lastCompare = compareNames(e1.getSpouseLast(),e2.getSpouseLast());
			} else {
				lastCompare = compareNames(e1.getLastName(),e2.getLastName());
			}
			
			if (lastCompare != 0) return lastCompare;
			
			int firstCompare;
			if (spouseCompare) {
				firstCompare = compareNames(e1.getSpouseFirst(),e2.getSpouseFirst());
			} else {
				firstCompare = compareNames(e1.getFirstNames(),e2.getFirstNames());
			}
			
			if (firstCompare != 0) return firstCompare;
			
			return e2.getId()-e1.getId();
		}
		
		private int compareNames(String n1, String n2) {
			// The application never stores null names in the database, but
			// just in case someone puts a null name in there...
			if (n1 == null && n2 == null) return 0;
			if (n1 == null) return -1;
			if (n2 == null) return 1;
			return n1.compareTo(n2);
		}
	};
}
