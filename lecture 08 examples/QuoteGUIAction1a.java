import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class QuoteGUIAction1a extends JFrame implements ActionListener {
	private JTextArea area;
	
	private String[] quotes = {
			"\"And Brownie, you're doing a heck of a job.\"—To FEMA director Mike Brown who resigned 10 days later amid criticism over his job performance.—Mobile, Ala., Sept. 2, 2005",
			"\"If this were a dictatorship, it'd be a heck of a lot easier, just so long as I'm the dictator.\" —Washington, D.C., Dec. 19, 2000",
			"\"We need an energy bill that encourages consumption.\" —Trenton, N.J., Sept. 23, 2002"
	};
	
	private Random random = new Random();
	
	public QuoteGUIAction1a() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("15-600 Quote GUI");
		setSize(500,350);
		JPanel pane = new JPanel();
		
		Font bigFont = new Font("TimesRoman",Font.ITALIC,20);
		
		JLabel label = new JLabel("How about a quote?");
		label.setFont(bigFont);
		pane.add(label);
		
		JButton button = new JButton("George W. Bush");
		button.addActionListener(this);
		pane.add(button);
		
		ImageIcon icon = new ImageIcon("gwb.jpg");
		JButton button2 = new JButton(icon);
		button2.addActionListener(this);
		pane.add(button2);
		
		area = new JTextArea(10,40);
		area.setEditable(false);
		area.setLineWrap(true);
		pane.add(area);
		
		setContentPane(pane);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event) {
		int r = random.nextInt(quotes.length);
		area.setText(quotes[r]);
	}

	public static void main(String[] args) {
		new QuoteGUIAction1a();
	}
}
