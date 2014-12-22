import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class URLToStringTest implements ActionListener {
    private JButton      button;
    private JTextField   textField;
    private JTextArea    textArea;

    public URLToStringTest() {
        JFrame frame = new JFrame("A Swing Application that Displays the contents of a URL in a Scrollable TextArea");
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

        textArea = new JTextArea("Web page will be displayed here",25,80);
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
            	textArea.setText(htmlDoc);
            } catch (IOException e) {
            	textArea.setText(e.toString());
            }
        }
    }

    public static void main(String[] args) {
        new URLToStringTest();
    }
}
