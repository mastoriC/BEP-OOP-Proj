import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class viewClient implements ActionListener {
    JFrame fr;
    JButton refresh, print, cancel, choose;
    JPanel queueListPanel, optionPanel, optionList;
    JPanel southLayout, northLayout;
    JTextField pathPreview;
    JTextArea queueList;
    JLabel header;
    JLabel nameFile, list;

    // option list
    JLabel numberCoppies;
    JTextField numberCoppiesTf;

    JFileChooser fc;
    FileNameExtensionFilter extFilter = new FileNameExtensionFilter("PDF File", "pdf");

    File selectedFile;
    int NumberOfPages;
    Client cliSocket = new Client();

    //default constructor
    public viewClient(){

        //option selection
        JLabel numberOfPage, numberOfPage2;

        fr = new JFrame("BEP: Print from anywhere");
        fr.setLayout(new BorderLayout());
        fr.setSize(800,600);

        refresh = new JButton("refresh");

        print = new JButton("Print");
        print.setPreferredSize(new Dimension((int)(fr.getWidth()*0.7), 64));
        print.addActionListener(this);


        cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension((int)(fr.getWidth()*0.25), 64));
        cancel.addActionListener(this);

        queueList = new JTextArea("HelloWord.pdf",5,20);
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
        queueListPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        /* File to upload panel */
        northLayout.setLayout(new FlowLayout());
        nameFile = new JLabel("File to upload");
        nameFile.setPreferredSize(new Dimension((int)(fr.getWidth()*0.15), 48));
        nameFile.setHorizontalAlignment(JLabel.CENTER);
        nameFile.setVerticalAlignment(JLabel.CENTER);
        nameFile.setFont(new Font(nameFile.getFont().getFontName(), Font.BOLD, 14));

        pathPreview = new JTextField();
        pathPreview.setEditable(false);
        pathPreview.setText("No selected file");
        pathPreview.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        pathPreview.setPreferredSize(new Dimension((int)(fr.getWidth()*0.7), 48));

        choose = new JButton("Choose");
        choose.setPreferredSize(new Dimension((int)(fr.getWidth()*0.1), 48));
        choose.addActionListener(this);

        northLayout.add(nameFile);
        northLayout.add(pathPreview);
        northLayout.add(choose);

        /* Option Panel */
        header = new JLabel("Choose your option");
        optionList = new JPanel();
        numberCoppies = new JLabel("number of Coppies");
        numberCoppiesTf = new JTextField();
        optionList.setLayout(new GridLayout(3,1));

        optionList.add(numberCoppies);
        optionList.add(numberCoppiesTf);

        optionPanel.setLayout(new GridLayout(2,1));
        optionPanel.add(header);
        optionPanel.add(optionList);

        fr.add(northLayout, BorderLayout.NORTH);
        fr.add(queueListPanel, BorderLayout.WEST);
        fr.add(optionPanel, BorderLayout.CENTER);
        fr.add(southLayout, BorderLayout.SOUTH);

        fr.setLocationRelativeTo(null);
        fr.setResizable(false);
        fr.setVisible(true);

    }

    private String getFileExt() {
        String fileName = selectedFile.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".")+1);
        }
        return "";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(choose)){
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(fr);

            // get path of selected file.
            selectedFile = fc.getSelectedFile();
            pathPreview.setText(selectedFile.toString());

            // PDF File get number of pages
            String fileExt = getFileExt();

            if (fileExt.equals("pdf")) {
                try {
                    PDDocument doc = PDDocument.load(selectedFile);
                    NumberOfPages = doc.getNumberOfPages();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            } else if (fileExt.equals("docx")) {
               /* Will include Apache POI @tanssw */
            }

        } else if (e.getSource().equals(cancel)) {
//            JOptionPane pop1 = new JOptionPane();
//            JOptionPane.showMessageDialog(fr,"Welcome in Roseindia");
            JOptionPane.showConfirmDialog(fr, "confrim");

//            System.out.println("test");
        } else if (e.getSource().equals(print)) {
            if (selectedFile.equals(null)) {

            } else {
                System.out.println("[Send to socket] " + selectedFile);
                cliSocket.sendFile(selectedFile);
                pathPreview.setText("No selected file");
                CalPrice testCal = new CalPrice();
                testCal.calPrice(NumberOfPages, "color");
                System.out.println(testCal.getPrice() + " Bath.");
            }
        }
    }
}
