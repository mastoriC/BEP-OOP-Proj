import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class EarnUI {
    public JFrame fr;
    private JPanel panel1;
    private JPanel northLayout;
    private JLabel header;
    private JTextField pathName;
    private JPanel westLayout;
    private JTable table1;
    private JButton refresh;
    private JButton Cancel;
    private JButton print;
    private JButton CHOOSE;
    private JPanel SouthLayout;
    private JPanel optionLayout;
    private JPanel optionHead;
    private JPanel all;
    private JRadioButton allRadioButton;
    private JTextField textField2;
    private JLabel to;
    private JTextField textField3;
    private JSpinner spinner1;
    private JRadioButton blackRadioButton;
    private JRadioButton colorRadioButton;
    private JTextField textField4;

    JFileChooser fc;
    FileNameExtensionFilter extFilter = new FileNameExtensionFilter("PDF File", "pdf");

    File selectedFile;
    int NumberOfPages;
    Client cliSocket = new Client();

    private String getFileExt() {
        String fileName = selectedFile.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".")+1);
        }
        return "";
    }

    public EarnUI() {
        JFrame fr = new JFrame("BEP: Print from anywhere");
        pathName.setEditable(false);



        CHOOSE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Choose files
                if (e.getSource().equals(CHOOSE)){
                    JFileChooser fc = new JFileChooser();
                    fc.showOpenDialog(CHOOSE.getFocusCycleRootAncestor());


                    selectedFile = fc.getSelectedFile();
                    pathName.setText(selectedFile.toString());

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

                }
            }
        });




//        fr.setContentPane(new EarnUI().panel1);


        Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(Cancel)){
                    int status = JOptionPane.showConfirmDialog(fr, "Do you want to exit?",  "Select an Option...",JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                    if(status == 0){
                        // uesr press yes
                        System.exit(0);
                    }
                }
            }
        });
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (selectedFile.equals(null)) {

                    } else {
                        System.out.println("[Send to socket] " + selectedFile);
                        cliSocket.sendFile(selectedFile);
                        pathName.setText("No selected file");
                        CalPrice testCal = new CalPrice();
                        testCal.calPrice(NumberOfPages, "color");
                        System.out.println(testCal.getPrice() + " Bath.");
                        //test to preview file
                        fc.getFileView();
                    }
                }
                catch (NullPointerException n){
                    n.printStackTrace();
                    JOptionPane.showMessageDialog(fr, "Please select file!", "Error", JOptionPane.OK_OPTION);
                }


            }
        });



        fr.add(panel1);
        fr.pack();
        fr.setVisible(true);
    }


    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            e.printStackTrace();

        }
        SwingUtilities.invokeLater(() ->{
            new EarnUI();



        });

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
