package edu.cmu.cs.webapp.threads;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Logger {
	private static final boolean ENABLE_LOGGING = false;
	
	private static Logger instance = null;
	
	public static synchronized Logger getInstance() {
		if (instance == null) {
			instance = new Logger(new File("c:/tmp/To Do List.log"));
		}
		
		return instance;		
	}
	
	private RandomAccessFile raf = null;
	
	private Logger(File f) {
		if (!ENABLE_LOGGING) return;
		
		try {
			f.createNewFile();
			raf = new RandomAccessFile(f,"rw");
		} catch (IOException e) {
			throw new AssertionError(e);
		}
	}
	
	public void logRequest(HttpServletRequest request) throws IOException {
		if (!ENABLE_LOGGING) return;
		
		StringBuffer b = new StringBuffer();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		b.append(sdf.format(new Date()));
		b.append(' ');
		b.append(request.getServletPath());
		b.append(' ');
		b.append(request.getRemoteAddr());
		b.append(' ');
		b.append(request.getQueryString());
		b.append('\n');
		
		synchronized (raf) {
			raf.seek(raf.length());
			raf.write(b.toString().getBytes());
		}
	}
}
