import org.apache.pdfbox.pdmodel.PDDocument;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
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
    private JRadioButton AllSelect;
    private JTextField startPage;
    private JLabel to;
    private JTextField endPage;
    private JSpinner copy;
    private JRadioButton blackRadioButton;
    private JRadioButton colorRadioButton;
    private JRadioButton PageSelect;
    private ButtonGroup buttonGroupPage, buttonGroupColor;
    private JTextField IPField;
    private JLabel Queue;
    String colorType = "";
    String printMode = "";
    int StartPage;
    int EndPage;

    private JFileChooser fc;
    FileNameExtensionFilter extFilter;

    private Client cliSocket;
    private File selectedFile;
    private int NumberOfPages;

    private DefaultTableModel model;
    private LogArray logs;
    private JSONObject tempObj;

    private CalPrice calculator;
    private int valCopy;

    public EarnUI() {

        cliSocket = new Client();
        calculator = new CalPrice();
        logs = new LogArray();

        fr = new JFrame("BEP: Print from anywhere");
        pathName.setEditable(false);

        //Page Range
        startPage.setEditable(false);
        endPage.setEditable(false);

        // button group page selection
        buttonGroupPage = new ButtonGroup();
        buttonGroupPage.add(AllSelect);
        buttonGroupPage.add(PageSelect);

        // model table
        model = (DefaultTableModel) tableQ.getModel();
        model.addColumn("no");
        model.addColumn("fileName");
        model.addColumn("copy");
        model.addColumn("price");

        for(int i=0;i < logs.getSize(); i++) {
            tempObj = (JSONObject)(logs.getArr().get(i));
            model.addRow(new Object[0]);
            model.setValueAt(i+1, i, 0);
            model.setValueAt(tempObj.get("fileName"), i, 1);
            model.setValueAt(tempObj.get("copy") + " cp", i, 2);
            model.setValueAt(tempObj.get("price") + " thb", i, 3);
        }

        // button group black and color
        buttonGroupColor = new ButtonGroup();
        buttonGroupColor.add(blackRadioButton);
        buttonGroupColor.add(colorRadioButton);

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
                    int status = JOptionPane.showConfirmDialog(fr, "Do you want to exit?",  "Select an Option...",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
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
                    if (checkValidate()) {
                        valCopy = (Integer) copy.getValue(); // Get number of copy.
                        calculator.calPrice(NumberOfPages, colorType, valCopy);

                        String destHostname = IPField.getText();
                        setupConnection(destHostname);
                        transferFile();
                        selectionReset();

                        System.out.println(colorType);
                        System.out.println(calculator.getPrice() + " Bath.");
                        //test to preview file
                        fc.getFileView();
                    }
            }
        });

        PageSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Some Pages");
                startPage.setEditable(true);
                endPage.setEditable(true);
                printMode = "select";


            }
        });
        AllSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPage.setEditable(false);
                endPage.setEditable(false);
                startPage.setText("");
                endPage.setText("");
                System.out.println("All");
                printMode = "all";
            }
        });

        blackRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorType = "black";
            }
        });
        colorRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorType = "color";

            }
        });

//        fr.setContentPane(new EarnUI().panel1);
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setResizable(false);
        fr.add(panel1);
        fr.pack();
        fr.setVisible(true);
    }

    private String getFileExt() {
        String fileName = selectedFile.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".")+1);
        }
        return "";
    }

    private boolean pageRangeValidation() {
        if (PageSelect.isSelected()) {
            try {
                // Check if input number is number.
                StartPage = Integer.parseInt(startPage.getText());
                EndPage = Integer.parseInt(endPage.getText());

                if (EndPage - StartPage < 0) {
                    JOptionPane.showMessageDialog(fr, "Last page cannot less than start page. Please try again.", "Error", JOptionPane.OK_OPTION);
                } else {
                    if (EndPage - StartPage > NumberOfPages) {
                        JOptionPane.showMessageDialog(fr, "Out of length Page", "Error", JOptionPane.OK_OPTION);
                        startPage.setText("");
                        endPage.setText("");
                    } else {
                        NumberOfPages = EndPage - StartPage + 1;
                        return true;
                    }
                }

            } catch (NumberFormatException nfex) {
                JOptionPane.showMessageDialog(fr, "Please input number in Pages selection", "Error", JOptionPane.OK_OPTION);
            }
        } else if (AllSelect.isSelected()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(fr, "Please select page range. (All/Page)", "Error", JOptionPane.OK_OPTION);
        }
        return false;
    }

    private boolean colorValidation() {
        if (!colorType.equals("")) {
            return true;
        }
        JOptionPane.showMessageDialog(fr, "Please select color mode.", "Error", JOptionPane.OK_OPTION);
        return false;
    }

    private boolean checkValidate() {
        try {
            /*
                1) Exception caught if no file has been selected.
                2) Check page range validation.
                3) Check color method selection.
            */
            if (!selectedFile.equals(null) && pageRangeValidation() && colorValidation()) {
                return true;
            }
        } catch (NullPointerException npex) {
            JOptionPane.showMessageDialog(fr, "Please select a file!", "Error", JOptionPane.OK_OPTION);
        }
        return false;
    }

    private void setupConnection(String hostname) {
        if (hostname != cliSocket.getHostname()) {
            cliSocket.setHostname(hostname);
        }
        System.out.println(String.format("Setup connection with %s:%s", cliSocket.getHostname(), cliSocket.getPORT()));
    }

    private void transferFile() {
        try {
            cliSocket.sendFile(selectedFile, NumberOfPages, valCopy, calculator.getPrice());
            System.out.println(String.format("%s has been sent.", selectedFile.getName()));
            updateLogs();
            JOptionPane.showMessageDialog(fr, "File sent!", "Complete", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException npex){
            JOptionPane.showMessageDialog(fr, "Please select file!", "Error", JOptionPane.OK_OPTION);
        } catch (ConnectException conex) {
            JOptionPane.showMessageDialog(fr, "No response from server.", "Error", JOptionPane.OK_OPTION);
        } catch (UnknownHostException uhex) {
            JOptionPane.showMessageDialog(fr, "Unknow host. Please check Destination IP.", "Error", JOptionPane.OK_OPTION);
        }
    }

    private void selectionReset() {
        selectedFile = null;
        pathName.setText("Choose file to upload.");
    }

    private void updateLogs() {
        // removing old logs
        while (model.getRowCount() > 0) {
            model.removeRow(model.getRowCount()-1);
        }

        logs.refresh();

        for(int i=0;i < logs.getSize(); i++) {
            tempObj = (JSONObject)(logs.getArr().get(i));
            model.addRow(new Object[0]);
            model.setValueAt(i+1, i, 0);
            model.setValueAt(tempObj.get("fileName"), i, 1);
            model.setValueAt(tempObj.get("copy") + " cp", i, 2);
            model.setValueAt(tempObj.get("price") + " thb", i, 3);
        }
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //Spinner
        SpinnerModel value = new SpinnerNumberModel(1,1,99,1);
        copy = new JSpinner(value);
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
