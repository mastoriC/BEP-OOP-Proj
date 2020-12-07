import javax.swing.*;

public class EarnUI {
    private JPanel panel1;
    private JPanel northLayout;
    private JLabel header;
    private JTextField partName;
    private JPanel westLayout;
    private JTable table1;
    private JButton button3;
    private JButton Cancel;
    private JButton print;
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

    public EarnUI() {
        partName.setEditable(false);

    }

    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            e.printStackTrace();

        }
        SwingUtilities.invokeLater(() ->{
            JFrame fr = new JFrame("BEP: Print from anywhere");
            fr.setContentPane(new EarnUI().panel1);
            fr.pack();
            fr.setVisible(true);
        });

    }
}
