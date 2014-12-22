import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Lights2 {
	private JButton[] buttons;
	
	public Lights2(int numLights, long sleepTime) {
        Font font = new Font("Courier New", Font.BOLD, 14);

        JFrame frame = new JFrame("A Swing Application to Demonstrate Threads Running");
        frame.setSize(650,630);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pane = new JPanel();
        
        buttons = new JButton[numLights];
        for (int i=0; i<buttons.length; i++) {
        	buttons[i] = new JButton("   ");
        	buttons[i].setFont(font);
        	buttons[i].setBackground(Color.LIGHT_GRAY);
        	buttons[i].setEnabled(false);
        	pane.add(buttons[i]);
        }
        
        frame.setContentPane(pane);
        frame.setVisible(true);
        
        Thread t1 = new HelperThread(buttons,sleepTime,Color.GREEN,":-)");
        t1.start();
        
        Thread t2 = new HelperThread(buttons,sleepTime,Color.RED,":-(");
        t2.start();
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java Lights2 <numLights> <sleepMillis>");
			return;
		}
		
		int  numLights   = Integer.parseInt(args[0]);
		long sleepMillis = Long.parseLong(args[1]);
		new Lights2(numLights,sleepMillis);
	}
	
	private static class HelperThread extends Thread {
		private JButton[] buttons;
		private long      mySleepTime;
		private Color     myColor;
		private String    myText;
		private Random    random = new Random();
		
		public HelperThread(JButton[] buttons, long sleepTime, Color color, String text) {
			this.buttons = buttons;
			mySleepTime  = sleepTime;
			myColor      = color;
			myText       = text;
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(mySleepTime);
				} catch (InterruptedException e) {
					// Ignore...just proceed
				}
				
				int randomLightNum = random.nextInt(buttons.length);
				
				JButton b = buttons[randomLightNum];
				b.setText(myText);
				b.setBackground(myColor);
			}
		}		
	}
}
