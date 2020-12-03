import javax.swing.*;
import java.awt.*;

public class viewClient {

    //default constructor
    public viewClient(){
        JFrame fr;
        JButton refresh, print, cancel;
        JPanel queueListPanel, optionPanel;
        JPanel southLayout, northLayout;
        JLabel nameFile, list;

        fr = new JFrame("BEP: Print for anywhere");
        fr.setLayout(new BorderLayout());

        refresh = new JButton("refresh");
        print = new JButton("print");

        fr.add(refresh);
        fr.add(print);

        fr.setSize(500,500);
        fr.setVisible(true);

    }
}
