import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class QuoteGUI1 {
	public QuoteGUI1() {
		JFrame frame = new JFrame("15-600 QuoteGUI Example");
		frame.setSize(500,380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel pane = new JPanel();

		Font bigFont = new Font("TimesRoman",Font.ITALIC,20);
		
		JLabel label = new JLabel("How about a presidential quote?");
		label.setFont(bigFont);
		pane.add(label);
		
		JButton button = new JButton("George W. Bush");
		pane.add(button);
		
		JButton button2 = new JButton(new ImageIcon("gwb.jpg"));
		pane.add(button2);
		
		JTextArea area = new JTextArea(10,40);
		area.setText("Is our children learning?");
		pane.add(area);
		
		frame.setContentPane(pane);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new QuoteGUI1();
	}
}
