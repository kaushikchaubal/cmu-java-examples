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
import javax.swing.ScrollPaneConstants;

public class CubesGUIEasier implements ActionListener{
    private JTextField countField, incrField, rowsField;
    private JTextArea textArea;

    public CubesGUIEasier() {
    	JFrame frame = new JFrame("This Swing GUI Displays Cubes and Cube Roots");
        frame.setSize(780,380);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pane = new JPanel();

        JLabel label1 = new JLabel("Start Counting at:");
        pane.add(label1);

        Font fixedFont = new Font("Courier New",Font.BOLD,12);

        countField = new JTextField(10);
        countField.setFont(fixedFont);
        pane.add(countField);

        JLabel label2 = new JLabel("Increment by:");
        pane.add(label2);

        incrField = new JTextField(10);
        incrField.setFont(fixedFont);
        pane.add(incrField);
        
        JLabel label3 = new JLabel("Rows:");
        pane.add(label3);
        
        rowsField = new JTextField(10);
        rowsField.setFont(fixedFont);
        pane.add(rowsField);

        JButton computeButton = new JButton("Compute");
        computeButton.addActionListener(this);
        pane.add(computeButton);

        textArea = new JTextArea(20,100);
        textArea.setFont(fixedFont);
        textArea.setEditable(false);
        JScrollPane scroller = new JScrollPane(textArea);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.add(scroller);

        frame.setContentPane(pane);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new CubesGUIEasier();
    }
    
    public void actionPerformed(ActionEvent evt) {
        int countStart = 0;
        int increment = 0;
        int rows = 0;
        
        textArea.setText("");

        try {
            countStart = Integer.parseInt(countField.getText());
        } catch (NumberFormatException e) {
            textArea.append("Invalid value for starting count.\n");
            return;
        }

        try {
            increment = Integer.parseInt(incrField.getText());
        } catch (NumberFormatException e) {
            textArea.append("Invalid value for increment.\n");
            return;
        }

        try {
            rows = Integer.parseInt(rowsField.getText());
        } catch (NumberFormatException e) {
            textArea.append("Invalid value for rows.\n");
            return;
        }

        String table = computeTable(countStart,increment,rows);
        textArea.append(table);
    }

    private String computeTable(int countStart, int increment, int n) {

        StringBuffer b = new StringBuffer();

        // Print header
        b.append("Number           Cube        Cube Root");
        b.append('\n');
        b.append("--------------------------------------");
        b.append('\n');

        // Print table
        for (int i=0; i<n; i++) {
            int number = countStart+i*increment;
            int cube = number * number * number;
            double cubeRoot = Math.pow(number,1.0/3.0);
            
			String line = String.format("%1$6d %2$14d %3$16.3f\n", number, cube, cubeRoot);
			b.append(line);
        }

        return b.toString();
    }
}