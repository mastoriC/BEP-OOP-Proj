import javax.swing.*;

public class mainViewClient {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() ->{
            new viewClient();
        });

    }
}
