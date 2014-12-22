/*
 * Copyright (c) 2005-2007 Jeffrey L. Eppinger.  All Rights Reserved.
 *     Permission granted for educational use only.
 */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AccessController extends HttpServlet {
	private String accessCode;
	private String contentDirName;
	private String cookieName;
	private String cookieCode;

    public void init() {
        // Load the access code from the web.xml file
    	accessCode = getInitParameter("accessCode");

        // Load the cookie name and cookie code from the web.xml file
    	cookieName = getInitParameter("cookieName");
    	cookieCode = getInitParameter("cookieCode");

    	// Load the name of the content directory
        contentDirName = getInitParameter("contentDir");
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String path = request.getServletPath();
		assert path != null          : "Null Servlet Path";
		assert path.length() > 0     : "Zero Length Servlet Path";
		assert path.charAt(0) == '/' : "Path Does Not Start with a Slash";

		String fileName = path.substring(1);
		if (fileName.equals("start")) fileName = "index.html";
		
		loginWithCookie(request);   // Logs in user if he has our cookie

        HttpSession session = request.getSession();
    	if (session.getAttribute("login") == null) {
    		// We're not logged in, so send the login form (which comes back using the POST HTTP method)
    		logRequest(request,"doGet: not logged in: file="+fileName);
    		sendLoginPage(response,fileName,null,null);
    		return;
    	}
    	
		if (fileName.endsWith(".jpg") || fileName.endsWith(".gif") || fileName.endsWith(".pdf")) {
			sendBinaryFile(request,response,path,fileName);
			return;
		}

		sendTextFile(request,response,path,fileName);
    }

    /*
     * This method is only used to receive the response to the login form.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String afterLogin = request.getParameter("afterLogin");
        String code       = request.getParameter("code");
        String makeCookie = request.getParameter("makeCookie");
        
        if (code == null || code.length() == 0) {
    		logRequest(request,"POST: code required");
    		sendLoginPage(response,afterLogin,makeCookie,"The access code is required.  Try again.");
        	return;
        }
        
        if (!code.equals(accessCode)) {
    		logRequest(request,"POST: code invalid: "+code);
    		sendLoginPage(response,afterLogin,makeCookie,"The access code is invalid.  Try again.");
        	return;
        }
        
    	HttpSession session = request.getSession();
    	session.setAttribute("login",true);
    	
    	if (makeCookie != null && makeCookie.equals("yes")) {
	    	Cookie myCookie = new Cookie(cookieName,cookieCode);
	    	myCookie.setMaxAge(Integer.MAX_VALUE);
	    	response.addCookie(myCookie);
    	}

    	String fileName = "index.html";
    	if (afterLogin != null) fileName = afterLogin;
        
    	logRequest(request,"POST: successful login.  Redirecting to file="+fileName);
        response.sendRedirect(fileName);
    }
    
    /*
     * If we're not already logged in, check the cookie called "my-cookie".
     * If the cookie is present and it contains the correct value (cookieCode from the web.xml file)
     * then log in the user.  We track whether a user is logged in by the presence of the "login"
     * session variable.
     */
    private void loginWithCookie(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	if (session.getAttribute("login") != null) return;
    	
    	Cookie[] cookies = request.getCookies();
    	if (cookies == null) return;
    	
    	for (Cookie cookie : cookies) {
    		if (cookie.getName().equals(cookieName) && cookie.getValue().equals(cookieCode)) {
    			request.getSession().setAttribute("login", true);
    			return;  // return early...no point in checking the other cookies
    		}
    	}
    }

    private byte[] streamToBytes(InputStream is) throws IOException {
    	BufferedInputStream bis = new BufferedInputStream(is);
        try {
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	int b =  bis.read();
        	while (b != -1) {
        		baos.write(b);
        		b = bis.read();
        	}

        	return baos.toByteArray();
		} finally {
			try { bis.close(); } catch (IOException e) { /* Ignore */ }
        }
    }

    private String streamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        try {
	        StringBuffer b = new StringBuffer();

	        String line = br.readLine();
            while (line != null) {
                b.append(line);
                b.append('\n');
                line = br.readLine();
	        }
            
        	return b.toString();
        } finally {
        	try { br.close(); } catch (IOException e) { /* Ignore */ }
        }
    }
    
    private void logRequest(HttpServletRequest request, String message) {
    	log(request.getRemoteAddr()+": "+message);
    }
    
    private void sendTextFile(HttpServletRequest request, HttpServletResponse response, String reqInfo, String fileName) throws IOException {
    	if (fileName.indexOf('/') != -1 || fileName.indexOf('\\') != -1) {
        	logRequest(request,"sendTextFile: req="+reqInfo+": FORBIDDEN: file="+fileName);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
    	}
        
    	InputStream is = getServletContext().getResourceAsStream("/"+contentDirName+"/"+fileName);
    	if (is == null) {
        	logRequest(request,"sendTextFile: req="+reqInfo+": NOT_FOUND: file="+fileName);
        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
        	return;
        }

    	try {
       		String html = streamToString(is);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        	logRequest(request,"sendTextFile: req="+reqInfo+", file="+fileName);
    		out.println(html);
    	} catch (IOException e) {
        	logRequest(request,"sendTextFile: req="+reqInfo+": IOException: file="+fileName+": "+e.getMessage());
        	e.printStackTrace();
        	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    	}
    }

    private void sendBinaryFile(HttpServletRequest request, HttpServletResponse response, String reqInfo, String fileName) throws IOException {
    	if (fileName.indexOf('/') != -1 || fileName.indexOf('\\') != -1) {
        	logRequest(request,"sendBinaryFile: req="+reqInfo+": *** FORBIDDEN ***");
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
    	}

    	InputStream is = getServletContext().getResourceAsStream("/"+contentDirName+"/"+fileName);
    	if (is == null) {
        	logRequest(request,"sendBinaryFile: req="+reqInfo+": NOT_FOUND: file="+fileName);
        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
        	return;
        }

        try {
            byte[] bytes = streamToBytes(is);

            String suffix = fileName.substring(Math.max(0,fileName.length()-4)).toLowerCase();
            if (suffix.equals(".gif")) {
                response.setContentType("image/gif");
            } else if (suffix.equals(".jpg")) {
                response.setContentType("image/jpeg");
            } else if (suffix.equals(".pdf")) {
                response.setContentType("application/pdf");
            } else {
            	logRequest(request,"sendBinaryFile: req="+reqInfo+": *** UNSUPPORTED_MEDIA_TYPE *** file="+fileName);
                response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,fileName);
                return;
            }
            
            ServletOutputStream out = response.getOutputStream();
            out.write(bytes);
        } catch (IOException e) {
        	logRequest(request,"sendImage: req="+reqInfo+": IOException: file="+fileName+": "+e.getMessage());
        	e.printStackTrace();
        	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	return;
        }
    }
    
    private void sendLoginPage(
    		HttpServletResponse response,
    		String pageRequested,
    		String makeCookie,
    		String errorMessage)
    	throws IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
        out.println("<meta http-equiv=\"cache-control\" content=\"no-cache\">");
        out.println("<meta http-equiv=\"pragma\" content=\"no-cache\">");
        out.println("<title>Enter Access Code</title>");
        out.println("</head>");
        out.println("<body>");
        if (errorMessage != null) {
        	out.println("<p style=\"font-size: large; color: red\">");
        	out.println(errorMessage);
        	out.println("</p>");
        }
        out.println("<form method=\"POST\" action=\"login\">");
        if (pageRequested != null) {
        	out.println("    <input type=\"hidden\" name=\"afterLogin\" value=\""+pageRequested+"\" />");
        }
        out.println("    <p style=\"font-size: x-large\">");
        out.println("        For Access Enter Code:");
        out.println("        <input type=\"password\" name=\"code\" />");
        out.println("        <input type=\"submit\" name=\"button\" value=\"Submit\" />");
        out.println("    </p>");
        out.println("    <p>");
        out.print("        <input type=\"checkbox\" name=\"makeCookie\" value=\"yes\"");
        if (makeCookie != null && makeCookie.equals("yes")) out.print(" checked");
        out.println(" />");
        out.println("        Remember me on this computer");
        out.println("    </p>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
}
