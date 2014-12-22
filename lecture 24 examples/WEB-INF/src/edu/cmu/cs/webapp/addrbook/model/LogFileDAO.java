package edu.cmu.cs.webapp.addrbook.model;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.dao.DAOException;

import edu.cmu.cs.webapp.addrbook.databean.User;

class LogFileDAO extends LogDAO {
    private RandomAccessFile raf;
    private Calendar         calendar;

    /**
     * Constructor that takes a the name of the log file.
     *
     * The current date is inserted into the file name before the extension
     * (unless there is no extension) to create the log file name.
     * For example, if the file name is "c:/my-app-data/log.csv",
     * and today's date is Dec 17, 2003 then the audit log file name will
     * be "c:/my-app-data/log-2003-12-17.csv".
     *
     * @param fileName the name of the log file (to which the date is added).
     */
    public LogFileDAO(String fileName) {
    	int dotPos = fileName.lastIndexOf('.');
    	String prefix, suffix;
    	if (dotPos == -1) {
    		prefix = fileName;
    		suffix = "";
    	} else {
    		prefix = fileName.substring(0,dotPos);
    		suffix = fileName.substring(dotPos);
    	}

        calendar = Calendar.getInstance();
        StringBuffer b = new StringBuffer(prefix);
        b.append('-');
        b.append(calendar.get(Calendar.YEAR));
        b.append('-');
        twoDigitAppend(b,calendar.get(Calendar.MONTH)+1);
        b.append('-');
        twoDigitAppend(b,calendar.get(Calendar.DAY_OF_MONTH));
        b.append(suffix);
        String logFileName = b.toString();

        try {
            // Open the file for read/write access
            raf = new java.io.RandomAccessFile(logFileName, "rw");
        } catch (IOException e) {
        	throw new AssertionError(e);
        }
    }

	public synchronized void write(HttpServletRequest request, String text) throws DAOException {
		StringBuffer b = new StringBuffer();
		b.append(request.getRemoteAddr());
		b.append(',');
		
		calendar.setTimeInMillis(System.currentTimeMillis());
		b.append(calendar.get(Calendar.YEAR));
		b.append('-');
		twoDigitAppend(b,calendar.get(Calendar.MONTH)+1);
		b.append('-');
		twoDigitAppend(b,calendar.get(Calendar.DAY_OF_MONTH));
		b.append(',');
		twoDigitAppend(b,calendar.get(Calendar.HOUR_OF_DAY));
		b.append(':');
		twoDigitAppend(b,calendar.get(Calendar.MINUTE));
		b.append(':');
		twoDigitAppend(b,calendar.get(Calendar.SECOND));
		b.append(',');

		User user = (User) request.getSession(true).getAttribute("user");
		String userName = user == null ? null : user.getUserName();
		b.append(userName);

		b.append(",\"");
		b.append(text);
		b.append("\"\n");

		try {
            // Go to the end of the file
            raf.seek(raf.length());
            // Write out the line.
            raf.writeBytes(b.toString());
        } catch (java.io.IOException e) {
        	throw new DAOException(e);
        }
	}

   private static void twoDigitAppend(StringBuffer b, int i) {
       if (i<10) b.append('0');
       b.append(i);
   }
}
