import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ToDoList4 implements ActionListener {
	JTextField item;
	JTextArea toDoList;
	JButton bottomButton, topButton;
	List list = new ArrayList();

	public ToDoList4() {
		JFrame frame = new JFrame("Example ToDoList!");
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel content = new JPanel();

		Font fixFont = new Font("Courier New", Font.BOLD, 20);
		Font varFont = new Font("Helvetica", Font.BOLD, 20);

		JLabel label = new JLabel("Item: ");
		label.setFont(varFont);
		content.add(label);

		item = new JTextField(35);
		item.setFont(fixFont);
		content.add(item);

		bottomButton = new JButton("Add To Bottom");
		bottomButton.setFont(varFont);
		bottomButton.addActionListener(this);
		content.add(bottomButton);

		topButton = new JButton("Add To Top");
		topButton.setFont(varFont);
		topButton.addActionListener(this);
		content.add(topButton);

		toDoList = new JTextArea(11, 45);
		toDoList.setFont(fixFont);
		content.add(toDoList);

		frame.setContentPane(content);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == bottomButton) {
			addToBottom(item.getText());
			item.setText("");
			displayList();
		} else if (ae.getSource() == topButton) {
			addToTop(item.getText());
			item.setText("");
			displayList();
		} else {
			toDoList.setText("Unknown Event");
		}
	}

	private void addToBottom(String s) {
		list.add(s);
	}

	private void addToTop(String s) {
		list.add(0, s);
	}

	private void displayList() {
		StringBuffer b = new StringBuffer();

		for (int i = 0; i < list.size(); i++) {
			String item = (String) list.get(i);
			b.append(i + 1);
			b.append(". ");
			b.append(item);
			b.append('\n');
		}

		toDoList.setText(b.toString());
	}

	public static void main(String args[]) {
		new ToDoList4();
	}
}
