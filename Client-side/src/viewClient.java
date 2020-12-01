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
        fr.setLayout(new GridLayout(1,1));
//        fr.setLayout(new BorderLayout());

        queueListPanel = new JPanel();


        list = new JLabel("test");
        refresh = new JButton("refresh");

        queueListPanel.setLayout(new BorderLayout());
        queueListPanel.add(list, BorderLayout.NORTH);
        queueListPanel.add(refresh, BorderLayout.SOUTH);


        optionPanel = new JPanel();
        nameFile = new JLabel("Files name to upload");
        optionPanel.setLayout(new BorderLayout());
        optionPanel.add(nameFile, BorderLayout.CENTER);


        fr.add(queueListPanel);
        fr.add(optionPanel);
//        fr.add(queueListPanel, BorderLayout.WEST);
//        fr.add(optionPanel, BorderLayout.EAST);



        fr.setSize(500,500);
        fr.setVisible(true);

    }
}
