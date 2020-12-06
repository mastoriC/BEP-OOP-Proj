import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GolfUI {
    private JPanel panel1;
    private JPanel northLayout;
    private JLabel header;
    private JTextField textField1;
    private JPanel westLayout;
    private JTable table1;
    private JButton button3;
    private JButton button1;
    private JButton button2;
    private JButton CHOOSE;
    private JPanel SouthLayout;
    private JPanel optionLayout;
    private JPanel optionHead;
    private JPanel all;
    private JRadioButton allRadioButton;
    private JRadioButton pageRadioButton;
    private JTextField textField2;
    private JLabel to;
    private JTextField textField3;
    private JSpinner spinner1;
    private JRadioButton blackRadioButton;
    private JRadioButton colorRadioButton;
    private JTextField textField4;

    public GolfUI() {

    }

    public static void main(String[] args) {
        JFrame fr = new JFrame();
        fr.setContentPane(new GolfUI().panel1);

        fr.pack();
        fr.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
