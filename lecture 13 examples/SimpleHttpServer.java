import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SimpleHttpServer implements Runnable {
	private static File docRoot;
	private static int  port;
	
	public static void main(String[] args) {
		docRoot = new File(".");
		port = 80;
		
		processArgs(args);
		
		serverLoop();
	}

	private static void processArgs(String[] args) {
		System.out.println("Usage: java SimpleHttpServer [<docRoot> [<port>]]");

		if (args.length >  0) {
			docRoot = new File(args[0]);
		}
		
		if (args.length > 1) {
			try {
				port = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.out.println("Specified port is not an int: "+args[1]);
				System.exit(1);
			}
		}
	}
	
	private static void serverLoop() {
		System.out.println("Starting up on port "+port+".");
		System.out.println("DocRoot="+docRoot.getAbsolutePath());
		try {
			ServerSocket serverSock = new ServerSocket(port);
			while (true) {
				Socket newSock = serverSock.accept();
				SimpleHttpServer server = new SimpleHttpServer(newSock);
				Thread t = new Thread(server);
				t.start();
			}
		} catch (IOException e) {
			System.out.println("IOException in serverLoop(): "+e.getMessage());
		}
	}
	
	private Socket mySock;
	
	public SimpleHttpServer(Socket mySock) {
		this.mySock = mySock;
	}
	
	public void run() {
		System.out.println(Thread.currentThread().getName()+
				": New connection from "+mySock.getInetAddress()+":"+mySock.getPort());
		BufferedReader reader = null;
		OutputStream   out    = null;   // needs to be an output stream to send back image data
		
		try {
			reader = new BufferedReader(new InputStreamReader(mySock.getInputStream()));
			out = mySock.getOutputStream();
			while (true) {
				MyRequest request  = readRequest(reader);
				byte[]    response = processRequest(request);
				sendResponse(out,response);
			}
		} catch (IOException e) {
			System.out.println(Thread.currentThread().getName()+": "+e.getMessage());
		} finally {
			System.out.println(Thread.currentThread().getName()+": closing streams and socket");
			try { if (reader != null) reader.close(); } catch (IOException e2) { /* Ignore */ }
			try { if (out != null) out.close(); } catch (IOException e2) { /* Ignore */ }
			try { mySock.close(); } catch (IOException e2) { /* Ignore */ }
			System.out.println(Thread.currentThread().getName()+": exiting");
		}
	}
	
	private MyRequest readRequest(BufferedReader reader) throws IOException {
		MyRequest request = new MyRequest();
		String firstLine = reader.readLine();
		System.out.println("firstLine="+firstLine);
		if (firstLine == null) {
			throw new IOException("End of file (expecting firstLine)");
		} else if (!firstLine.startsWith("GET ")) {
			throw new IOException("Unrecognized HTTP method: "+firstLine);
		}
		
		// extract from firstLine the identifier
		String identifier = firstLine.substring(firstLine.indexOf(' ')).trim();
		if (identifier.indexOf(' ') != -1) {
			// There's a space...assume it's the start of the protocol...
			// Remove the protocol
			identifier = identifier.substring(0,identifier.indexOf(' '));
		}
		
		int qMarkPos = identifier.indexOf('?');
		if (qMarkPos != -1) {
			// There's a queryString
			request.setQueryString(identifier.substring(qMarkPos+1));
			identifier = identifier.substring(0,qMarkPos);
		}
		
		request.setIdentifier(identifier); 

		String headerLine = reader.readLine();
		while (headerLine != null && headerLine.length() > 0) {
			System.out.println("headerLine="+headerLine);
			request.addHeaderLine(headerLine);
			headerLine = reader.readLine();
		}
		
		if (headerLine == null) throw new IOException("End of File (expecting headerLine)");
		
		return request;
	}
	
	private byte[] processRequest(MyRequest request) throws IOException {
		System.out.println("queryString="+request.getQueryString());

		String identifier = request.getIdentifier();
		File doc = new File(docRoot,request.getIdentifier());
		System.out.println(doc);

		if (identifier.charAt(0) != '/' || identifier.contains("..")) {
			return illegalRequest(doc);
		}
		
		if (!doc.exists()) {
			return fileNotFoundError(doc);
		}
		
		if (doc.isDirectory()) {
			return directoryError(doc);
		}
		
		InputStream is = null;
		try {
			int bytesToRead = (int) doc.length();
			byte[] response = new byte[bytesToRead];
			is = new FileInputStream(doc);
			int pos = 0;
			while (bytesToRead > 0) {
				int bytesRead = is.read(response,pos,bytesToRead);
				pos += bytesRead;
				bytesToRead -= bytesRead;
			}
			return response;
		} finally {
			if (is != null) try { is.close(); } catch (IOException e) { /* Ignore */ }
		}
	}
	
	private void sendResponse(OutputStream out, byte[] response) throws IOException{
		out.write(("HTTP/1.1 200 OK\n").getBytes());
		out.write("Content-Type: text/html\n".getBytes());
		out.write(("Content-Length: "+response.length+"\n").getBytes());
		out.write('\n');
		out.write(response);
		out.flush();
	}
	
	private byte[] fileNotFoundError(File doc) {
		StringBuffer b = new StringBuffer();
		b.append("<html>");
		b.append("<head><title> Error 404 - File Not Found </title></head>");
		b.append("<body>");
		b.append("  <h1>Error 404 --  File Not Found</h1>");
		b.append("  <h2>File = "+doc+"</h2>");
		b.append("</body>");
		b.append("</html>");
		return b.toString().getBytes();
	}
	
	private byte[] illegalRequest(File doc) {
		StringBuffer b = new StringBuffer();
		b.append("<html>");
		b.append("<head><title> Error 403 - Forbidden </title></head>");
		b.append("<body>");
		b.append("  <h1>Error 403 --  Forbidden</h1>");
		b.append("  <h2>File = "+doc+"</h2>");
		b.append("</body>");
		b.append("</html>");
		return b.toString().getBytes();
	}
	
	private byte[] directoryError(File doc) {
		StringBuffer b = new StringBuffer();
		b.append("<html>");
		b.append("<head><title> Error 401 - Unauthorized </title></head>");
		b.append("<body>");
		b.append("  <h1>Error 401 --  Unauthorized (no directory listings)</h1>");
		b.append("  <h2>File = "+doc+"</h2>");
		b.append("</body>");
		b.append("</html>");
		return b.toString().getBytes();
	}
	
	private static class MyRequest {
		private String  identifier  = null;
		private String  queryString = null;
		
		private List<String> headerLines = new ArrayList<String>();
		
		public void addHeaderLine(String headerLine) {
			headerLines.add(headerLine);
		}
		
		public String getIdentifier()  { return identifier;  }
		public String getQueryString() { return queryString; }
		
		public void setIdentifier(String s)  { identifier  = s;    }
		public void setQueryString(String s) { queryString = s;    }
	}
}
