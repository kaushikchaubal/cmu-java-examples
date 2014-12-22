package edu.cmu.cs.webapp.threads;

import java.util.ArrayList;
import java.util.List;

public class CorrectToDoListDAO extends ToDoListDAO {
	private List<String> list = new ArrayList<String>();
	
	public synchronized void addToTop(String item) {
		list.add(0,item);
	}
	
	public synchronized int addToBottom(String item) {
		list.add(item);
		return list.size();
	}
	
	public synchronized boolean delete(String position) {
    	if (position == null) return false;

    	try {
    		int index = Integer.parseInt(position) - 1;
    		if (index < 0 || index >= list.size()) return false;
    		list.remove(index);
    		return true;
    	} catch (NumberFormatException e) {
    		return false;
    	}
	}

	public synchronized String getItem(int index) {
		if (index < 0 || index >= list.size()) {
			return "*** There is no element at index="+index;
		}
		
		return list.get(index);
	}
	
	public synchronized String[] getItems() {
		return list.toArray(new String[list.size()]);
	}
	
	public synchronized int size() {
		return list.size();
	}
}
