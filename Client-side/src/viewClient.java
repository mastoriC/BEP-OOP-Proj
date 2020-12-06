import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class viewClient implements ActionListener {
    JFrame fr;
    JButton refresh, print, cancel, choose;
    JPanel queueListPanel, optionPanel, optionList;
    JPanel southLayout, northLayout;
    JTextArea queueList;
    JLabel header;
    JLabel nameFile, list;
    JFileChooser fc;

    //default constructor
    public viewClient(){


        //option selection
        JLabel numberOfPage, numberOfPage2;


        fr = new JFrame("BEP: Print for anywhere");
        fr.setLayout(new BorderLayout());

        refresh = new JButton("refresh");
        print = new JButton("Print");
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);

        queueList = new JTextArea("HelloWord.pdf",5,15);
        queueList.setEditable(false);
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
       choose = new JButton("Choose");
       choose.addActionListener(this);
       northLayout.add(nameFile);
       northLayout.add(choose);


       //option panel
        header = new JLabel("Choose your option");
        optionList = new JPanel();
        optionList.setLayout(new GridLayout(3,1));


        optionPanel.setLayout(new GridLayout(3,1));
        optionPanel.add(header);
        optionPanel.add(optionList);






       fr.add(northLayout, BorderLayout.NORTH);
       fr.add(queueListPanel, BorderLayout.WEST);
       fr.add(optionPanel, BorderLayout.CENTER);
       fr.add(southLayout, BorderLayout.SOUTH);

       fr.setLocationRelativeTo(null);

        fr.setSize(800,600);
        fr.setVisible(true);



    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(choose)){
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(fr);
            // get Selected Files and get name
            File test = fc.getCurrentDirectory();
            File nameFiles = fc.getSelectedFile();
            System.out.println(fc.getName(nameFiles));
            System.out.println(test);

        }
        else if(e.getSource().equals(cancel)){
//            JOptionPane pop1 = new JOptionPane();
            JOptionPane.showMessageDialog(fr,"Welcome in Roseindia");
//            System.out.println("test");

        }
    }
}
