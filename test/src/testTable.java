import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class testTable {
    private JFrame fr;
    private JScrollPane scrollPane;
    private JTable table;
    public testTable(){
            fr = new JFrame();
            fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fr.setBounds(100,100,580,242);
            fr.setTitle("TestTitle");
            fr.getContentPane().setLayout(null);
            //scrollPane for Table

            scrollPane = new JScrollPane();
            scrollPane.setBounds(33,41,494,90);
            fr.getContentPane().add(scrollPane);
            //table

            table = new JTable();
            scrollPane.setViewportView(table);

            //module of table

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("No");
        model.addColumn("CustomerID");
        model.addColumn("Name");
        model.addColumn("Email");
        model.addColumn("CountryCode");

        for(int i=0;i <= 10; i++) {
            model.addRow(new Object[0]); model.setValueAt(i+1, i, 0); model.setValueAt("Data Col 1", i, 1); model.setValueAt("Data Col 2", i, 2); model.setValueAt("Data Col 3", i, 3); model.setValueAt("Data Col 4", i, 4);
        }
        fr.setVisible(true);
    }
    public static void main(String[] args) {  new testTable(); }
}


