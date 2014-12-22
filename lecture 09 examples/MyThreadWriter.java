import javax.swing.JTextArea;

public class MyThreadWriter implements Runnable {
	private long mySleepTimeInMillis;
	private JTextArea area;
	
	public MyThreadWriter(int sleepTimeInSecs, JTextArea a) {
		mySleepTimeInMillis = sleepTimeInSecs * 1000;
		area = a;
	}
	
	public void run() {
		while (true) {
			Thread myThread = Thread.currentThread();
			area.append("In \"MyThreadWriter.run()\": thread name: "+myThread.getName()+"\n");
			try {
				Thread.sleep(mySleepTimeInMillis);
			} catch (InterruptedException e) {
				area.append(myThread.getName()+": Interrupted\n");
			}
		}
	}
}
