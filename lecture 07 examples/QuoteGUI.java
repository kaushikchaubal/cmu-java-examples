import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class QuoteGUI {
	
	public QuoteGUI() {
		JFrame frame = new JFrame();
		frame.setTitle("15-600 Sample GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,370);
		
		JPanel panel = new JPanel();
		
		Font font = new Font("Timesroman", Font.BOLD, 24);
		
		JLabel label = new JLabel("How about a quote?");
		label.setFont(font);
		panel.add(label);
		
		JButton bushButton = new JButton(new ImageIcon("gwb.jpg"));
		panel.add(bushButton);
		
		JButton obamaButton = new JButton(new ImageIcon("bo.jpg"));
		panel.add(obamaButton);
		
		JTextArea area = new JTextArea(10,50);
		area.setText("Yes We Can!");
		panel.add(area);
		
		frame.setContentPane(panel);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		QuoteGUI gui = new QuoteGUI();
	}

}
