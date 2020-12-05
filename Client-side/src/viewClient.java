import javax.swing.*;
import java.awt.*;

public class viewClient {

    //default constructor
    public viewClient(){
        JFrame fr;
        JButton refresh, print, cancel, choose;
        JPanel queueListPanel, optionPanel;
        JPanel southLayout, northLayout;
        JTextArea queueList;
        JLabel nameFile, list;

        fr = new JFrame("BEP: Print for anywhere");
        fr.setLayout(new BorderLayout());

        refresh = new JButton("refresh");
        print = new JButton("print");
        cancel = new JButton("Cancel");

        queueList = new JTextArea();
        queueList.setSize(500, 500);

       queueListPanel = new JPanel();
       optionPanel = new JPanel();

       southLayout = new JPanel();
       northLayout = new JPanel();

       southLayout.add(print);
       southLayout.add(cancel);

       queueListPanel.setLayout(new BorderLayout());
       queueListPanel.add(refresh, BorderLayout.SOUTH);
       queueListPanel.add(queueList, BorderLayout.CENTER);

       northLayout.setLayout(new FlowLayout());
       nameFile = new JLabel("File to upload");
       northLayout.add(nameFile);


       fr.add(northLayout, BorderLayout.NORTH);
       fr.add(queueListPanel, BorderLayout.WEST);
       fr.add(optionPanel, BorderLayout.CENTER);
       fr.add(southLayout, BorderLayout.SOUTH);

        fr.setSize(500,500);
        fr.setVisible(true);



    }
}
