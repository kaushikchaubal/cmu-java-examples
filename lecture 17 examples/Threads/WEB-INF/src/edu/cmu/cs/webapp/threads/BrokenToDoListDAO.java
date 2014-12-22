package edu.cmu.cs.webapp.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class BrokenToDoListDAO extends ToDoListDAO {
	private List<String> list = new Vector<String>();

	public void addToTop(String item) {
		list.add(0,item);
	}

	public int addToBottom(String item) {
		list.add(item);
		sleep();
		return list.size();
	}
	
	public boolean delete(String position) {
    	if (position == null) return false;

    	sleep();
    	try {
    		int index = Integer.parseInt(position) - 1;
    		list.remove(index);
    		return true;
    	} catch (NumberFormatException e) {
    		return false;
    	} catch (IndexOutOfBoundsException e) {
    		return false;
    	}
	}

	public String getItem(int index) {
		try {
			sleep();
			return list.get(index);
		} catch (IndexOutOfBoundsException e) {
			return "*** There is no element at index="+index;
		}
	}
	
	public String[] getItems() {
		List<String> myCopy = new ArrayList<String>();
		while (myCopy.size() < list.size()) {
			myCopy.add(getItem(myCopy.size()));
		}
		return myCopy.toArray(new String[myCopy.size()]);
	}

	public int size() {
		return list.size();
	}
	
    private void sleep() {
        // The concurrency control problems happen more frequently
        // when these sleeps occur.
        try {
            Thread.sleep(100);   // sleep 100 milliseconds
         } catch (InterruptedException e) {
            // We were woken up early...oh well, who needs sleep anyway.
         }
    }

	
}
