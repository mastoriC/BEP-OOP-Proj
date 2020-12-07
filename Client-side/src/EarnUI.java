import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

public class EarnUI {
    public JFrame fr;
    private JPanel panel1;
    private JPanel northLayout;
    private JLabel header;
    private JTextField pathName;
    private JPanel westLayout;
    private JTable tableQ;
    private JButton refresh;
    private JButton Cancel;
    private JButton print;
    private JButton CHOOSE;
    private JPanel SouthLayout;
    private JPanel optionLayout;
    private JPanel optionHead;
    private JPanel all;
    private JRadioButton allRadioButton;
    private JTextField startPage;
    private JLabel to;
    private JTextField endPage;
    private JSpinner spinner1;
    private JRadioButton blackRadioButton;
    private JRadioButton colorRadioButton;
    private JRadioButton PageSelect;
    private ButtonGroup buttonGroupPage;
    private JTextField IPField;
    private JLabel Queue;

    private JFileChooser fc;
    FileNameExtensionFilter extFilter;

    private Client cliSocket;
    private File selectedFile;
    private int NumberOfPages;

    private CalPrice calculator;

    public EarnUI() {

        cliSocket = new Client();
        calculator = new CalPrice();

        fr = new JFrame("BEP: Print from anywhere");
        pathName.setEditable(false);

        // button group
        buttonGroupPage = new ButtonGroup();
        buttonGroupPage.add(allRadioButton);
        buttonGroupPage.add(PageSelect);

        // model table
        DefaultTableModel model = (DefaultTableModel) tableQ.getModel();
        model.addColumn("No");
        model.addColumn("CustomerID");

        for(int i=0;i <= 5; i++) {
            model.addRow(new Object[0]); model.setValueAt(i+1, i, 0); model.setValueAt("Data Col 1", i, 1);
        }

        CHOOSE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Choose files
                try {
                    if (e.getSource().equals(CHOOSE)){
                        fc = new JFileChooser();
                        extFilter = new FileNameExtensionFilter("PDF/DOCX File", "pdf", "docx");
                        fc.setFileFilter(extFilter);
                        fc.showOpenDialog(CHOOSE.getFocusCycleRootAncestor());

                        selectedFile = fc.getSelectedFile();
                        pathName.setText(selectedFile.toString());

                        String fileExt = getFileExt(); // PDF File get number of pages
                        if (fileExt.equals("pdf")) {
                            try (PDDocument doc = PDDocument.load(selectedFile)) {
                                NumberOfPages = doc.getNumberOfPages();
                            } catch (IOException exc) {
                                exc.printStackTrace();
                            }
                        } else if (selectedFile != null) {
                            selectionReset();
                            JOptionPane.showMessageDialog(fr, "Only PDF File is acceptable!!!", "Error", JOptionPane.OK_OPTION);
                        }
                    }
                } catch (NullPointerException exp) {}
            }
        });
        Cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(Cancel)){
                    int status = JOptionPane.showConfirmDialog(fr, "Do you want to exit?",  "Select an Option...",JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                    if(status == 0){
                        // user press yes
                        System.exit(0);
                    }
                }
            }
        });
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!selectedFile.equals(null)) {

                        try{
                            int StartPage = Integer.parseInt(startPage.getText());
                            int EndPage = Integer.parseInt(endPage.getText());
                            System.out.println(StartPage);
                            System.out.println(EndPage);
                        }
                        catch (Exception x){
                            System.out.println("It's Not Number!!!!!!!!!");
                            JOptionPane.showMessageDialog(fr, "Please input number in Pages selection", "Error", JOptionPane.OK_OPTION);
                        }

                        String destHostname = IPField.getText();
                        setupConnection(destHostname);
                        transferFile();
                        selectionReset();
                        calculator.calPrice(NumberOfPages, "color");
                        System.out.println(calculator.getPrice() + " Bath.");
                        //test to preview file
                        fc.getFileView();
                    }
                } catch (NullPointerException npex){
                    JOptionPane.showMessageDialog(fr, "Please select a file!", "Error", JOptionPane.OK_OPTION);
                }
            }
        });

//        fr.setContentPane(new EarnUI().panel1);
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.add(panel1);
        fr.pack();
        fr.setVisible(true);

        PageSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Some Pages");

            }
        });
        allRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("All");
            }
        });
    }

    private String getFileExt() {
        String fileName = selectedFile.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".")+1);
        }
        return "";
    }

    private void setupConnection(String hostname) {
        if (hostname != cliSocket.getHostname()) {
            cliSocket.setHostname(hostname);
        }
        System.out.println(String.format("Setup connection with %s:%s", cliSocket.getHostname(), cliSocket.getPORT()));
    }

    private void transferFile() {
        try {
            cliSocket.sendFile(selectedFile, NumberOfPages);
            System.out.println(String.format("%s has been sent.", selectedFile.getName()));
            JOptionPane.showMessageDialog(fr, "File sent!", "Complete", JOptionPane.OK_OPTION);
        } catch (NullPointerException npex){
            JOptionPane.showMessageDialog(fr, "Please select file!", "Error", JOptionPane.OK_OPTION);
        } catch (UnknownHostException uhex) {
            JOptionPane.showMessageDialog(fr, "No response from server.", "Error", JOptionPane.OK_OPTION);
        }
    }

    private void selectionReset() {
        selectedFile = null;
        pathName.setText("Choose file to upload.");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new EarnUI();
        });
    }

//    private void createUIComponents() {
//        // TODO: place custom component creation code here
//        String data[][]={ {"01","Amit"},
//                {"02","Jai"},
//                {"03","Sachin"}};
//        String column[]={"ID","NAME"};
//        tableQ = new JTable(data,column);
//    }
}
