package testUI;

import javax.swing.*;

public class testUI {
    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JTextField textField1;

    public testUI() {
        panel1 = new JPanel();
        panel1.add(button1);
        button1 = new JButton("EARNNN");



    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("testUI");
        frame.setContentPane(new testUI().panel1);
        frame.pack();
        frame.setVisible(true);
    }


}
