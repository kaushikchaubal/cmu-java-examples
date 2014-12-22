import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class QuoteGUIAction implements ActionListener {
	JButton   obamaButton;
	JButton   bushButton;
	JTextArea area;
	
	String[] bushQuotes = {
			"\"Rarely is the questioned asked: Is our children learning?\" --Florence, South Carolina, Jan. 11, 2000\n",
			"\"If this were a dictatorship, it'd be a heck of a lot easier, just so long as I'm the dictator.\" --Washington, D.C., Dec. 19, 2000\n"
	};
	
	String[] obamaQuotes = {
			"Yes We Can!\n",
			"I'm going to quit smoking!\n"
	};
	
	public QuoteGUIAction() {
		JFrame frame = new JFrame();
		frame.setTitle("15-600 Sample GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,370);
		
		JPanel panel = new JPanel();
		
		Font font = new Font("Timesroman", Font.BOLD, 24);
		
		JLabel label = new JLabel("How about a quote?");
		label.setFont(font);
		panel.add(label);
		
		bushButton = new JButton(new ImageIcon("gwb.jpg"));
		bushButton.addActionListener(this);
		panel.add(bushButton);
		
		obamaButton = new JButton(new ImageIcon("bho.jpg"));
		obamaButton.addActionListener(this);
		panel.add(obamaButton);
		
		area = new JTextArea(10,50);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		
		JScrollPane scroller = new JScrollPane(area);
		panel.add(scroller);
		
		frame.setContentPane(panel);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new QuoteGUIAction();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == obamaButton) {
			long curTime = System.currentTimeMillis();
			area.append(obamaQuotes[(int)curTime%2]);
		} else if (e.getSource() == bushButton) {
			long curTime = System.currentTimeMillis();
			area.append(bushQuotes[(int)curTime%2]);
		} else {
			area.append("Unknown Source\n");
		}
	}

}
