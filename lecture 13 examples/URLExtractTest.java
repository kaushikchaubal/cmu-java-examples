import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class URLExtractTest implements ActionListener {
    private JButton      button;
    private JTextField   textField;
    private JTextArea    textArea;

    public URLExtractTest() {
        JFrame frame = new JFrame("A Swing Application that demonstrates URLHelpers.extractURLs");
        frame.setSize(900,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pane = new JPanel();

        Font labelFont = new Font("Helvetica", Font.BOLD, 14);
        JLabel label = new JLabel("URL:");
        label.setFont(labelFont);
        pane.add(label);

        Font textFont = new Font("Courier New", Font.BOLD, 16);
        textField = new JTextField(40);
        textField.setFont(textFont);
        textField.addActionListener(this);
        pane.add(textField);        

        Font buttonFont = new Font("Helvetica", Font.BOLD, 12);
        button = new JButton("Display");
        button.setFont(buttonFont);
        button.addActionListener(this);
        pane.add(button);

        textArea = new JTextArea("URLs referenced in the Web page will be displayed here",25,80);
        textArea.setEditable(false);
        textArea.setFont(textFont);

        JScrollPane scroller = new JScrollPane(textArea);
        pane.add(scroller);
        
        frame.setContentPane(pane);
        frame.setVisible(true);
    }


    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if (source == button || source == textField) {
            String urlName = textField.getText();
            try {
	            String htmlDoc = URLHelpers.urlToString(urlName);
                List<String> list = URLHelpers.extractURLs(urlName,htmlDoc);
                textArea.setText(null);
                for (String s : list) {
                    textArea.append(s);
                    textArea.append("\n");
                }
            } catch (IOException e) {
            	textArea.setText(e.toString());
            }
        }
    }

    public static void main(String[] args) {
        new URLExtractTest();
    }
}
