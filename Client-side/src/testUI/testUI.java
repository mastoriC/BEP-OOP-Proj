package testUI;

import javax.swing.*;

public class testUI {
    private JPanel panel1;
    private JButton button1;

    public testUI() {
        panel1 = new JPanel();
        button1 = new JButton("EARNNN");
        panel1.add(button1);



    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("testUI");
        frame.setContentPane(new testUI().panel1);
        frame.pack();
        frame.setVisible(true);
    }


}
