import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Lights implements Runnable {
	private static final String OFF_STRING = "   ";
	private static final String ON_STRING  = ":-)";
	private static final Color  OFF_COLOR  = Color.LIGHT_GRAY;
	private static final Color  ON_COLOR   = Color.PINK;
	
	private long      sleepTime;
	private JButton[] buttons;
	private Random    random = new Random();
	
	public Lights(int numLights, long sleepTime) {
		this.sleepTime = sleepTime;
        Font font = new Font("Courier New", Font.BOLD, 14);

        JFrame frame = new JFrame("A Swing Application to Demonstrate Threads Running");
        frame.setSize(650,630);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pane = new JPanel();
        
        buttons = new JButton[numLights];
        for (int i=0; i<buttons.length; i++) {
        	buttons[i] = new JButton(OFF_STRING);
        	buttons[i].setFont(font);
        	buttons[i].setBackground(OFF_COLOR);
        	pane.add(buttons[i]);
        }
        
        frame.setContentPane(pane);
        frame.setVisible(true);
        
        Thread t = new Thread(this);
        t.start();
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				throw new AssertionError(e);
			}
			
			int randomLightNum = random.nextInt(buttons.length);
			
			JButton b = buttons[randomLightNum];
			if (b.getText().equals(OFF_STRING)) {
				b.setText(ON_STRING);
				b.setBackground(ON_COLOR);
			} else {
				b.setText(OFF_STRING);
				b.setBackground(OFF_COLOR);
			}
		}
	}
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java Lights <numLights> <sleepMillis>");
			return;
		}
		
		int  numLights   = Integer.parseInt(args[0]);
		long sleepMillis = Long.parseLong(args[1]);
		new Lights(numLights,sleepMillis);
	}
}
