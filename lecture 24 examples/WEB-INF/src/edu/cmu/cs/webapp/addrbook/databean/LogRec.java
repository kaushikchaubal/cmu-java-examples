package edu.cmu.cs.webapp.addrbook.databean;

import java.util.Date;

public class LogRec {
	private int    recNo;
	private Date   time;
	private String ipAddr;
	private String text;
	private String userName;
	
	public LogRec(int recNo)          { this.recNo = recNo; }
	
	public String getIpAddr()         { return ipAddr;   }
	public int    getRecNo()          { return recNo;    }
	public String getText()           { return text;     }
	public Date   getTime()           { return time;     }
	public String getUserName()       { return userName; }
	
	public void setIpAddr(String x)   { ipAddr   = x; }
	public void setText(String x)     { text     = x; } 
	public void setTime(Date d)       { time     = d; }
	public void setUserName(String x) { userName = x; }
}
