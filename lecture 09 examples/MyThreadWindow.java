import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MyThreadWindow implements ActionListener {
	private JButton    button;
	private JTextField nameField;
	private JTextArea  textArea;
	private int        sleepTimeInSecs = 0;
	
	public MyThreadWindow() {
        Font buttonFont = new Font("Helvetica", Font.BOLD, 16);
        Font labelFont = new Font("TimesRoman", Font.BOLD, 16);
        Font textFont = new Font("Courier New", Font.BOLD, 14);

        JFrame frame = new JFrame("A Swing Application to Demonstrate Threads Running");
        frame.setSize(650,630);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pane = new JPanel();

        JLabel label = new JLabel("Name: ");
        label.setFont(labelFont);
        pane.add(label);
        
        nameField = new JTextField(20);
        nameField.setFont(textFont);
        pane.add(nameField);

        button = new JButton("Start a New Thread");
        button.setFont(buttonFont);
        button.addActionListener(this);
        pane.add(button);

        textArea = new JTextArea(30,70);
        textArea.setEditable(false);
        textArea.setFont(textFont);
        JScrollPane scroller = new JScrollPane(textArea);
        pane.add(scroller);
        
        frame.setContentPane(pane);
        frame.setVisible(true);
	}
	
    public void actionPerformed(ActionEvent e) {
		Thread myThread = Thread.currentThread();
    	textArea.append("In method \"actionPerformed()\": Thread is named: "+myThread.getName()+"\n");
    	
    	sleepTimeInSecs++;
    	MyThreadWriter writer = new MyThreadWriter(sleepTimeInSecs,textArea);
    	Thread t = new Thread(writer);
    	t.setName(nameField.getText());
    	t.start();
    }
	
	public static void main(String[] args) {
		Thread myThread = Thread.currentThread();
		System.out.println("In method \"main()\".  Thread is named: "+myThread.getName());
		
		new MyThreadWindow();
	}
}
