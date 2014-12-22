import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FileWindow implements ActionListener {
    private JButton      button;
    private JTextField   textField;
    private JTextArea    textArea;

    public FileWindow() {
        JFrame frame = new JFrame("A Swing Application that Displays a File in a Scrollable TextArea");
        frame.setSize(900,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pane = new JPanel();

        Font labelFont = new Font("Helvetica", Font.BOLD, 14);
        JLabel label = new JLabel("Filename:");
        label.setFont(labelFont);
        pane.add(label);

        Font textFont = new Font("Courier New", Font.BOLD, 16);
        textField = new JTextField(20);
        textField.setFont(textFont);
        textField.addActionListener(this);
        pane.add(textField);        

        Font buttonFont = new Font("Helvetica", Font.BOLD, 12);
        button = new JButton("Display");
        button.setFont(buttonFont);
        button.addActionListener(this);
        pane.add(button);

        textArea = new JTextArea("File contents will be displayed here",25,80);
        textArea.setEditable(false);
        textArea.setFont(textFont);

        JScrollPane scroller = new JScrollPane(textArea);
        pane.add(scroller);
        
        frame.setContentPane(pane);
        frame.setVisible(true);
    }

    private String fileToString(String filename) {

        StringBuffer b = new StringBuffer();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));

            int lineNumber = 0;
            boolean eof = false;

            while (!eof) {
                String line = br.readLine();
                if (line == null) {
                    eof = true;
                } else {
                    lineNumber = lineNumber + 1;
                    b.append("line " + lineNumber + ": " + line + '\n');
                }
            }

            b.append("You have " + lineNumber + " lines in this file!!");
            br.close();
        } catch (FileNotFoundException e) {
            return e.toString();
        } catch (IOException e) {
        	try { br.close(); } catch (IOException e2) { /* Ignored */ }
            return e.toString();
        }

        return b.toString();
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == button || source == textField) {
            String filename = textField.getText();
            textArea.setText(fileToString(filename));
        }
    }

    public static void main(String[] args) {
        new FileWindow();
    }
}
