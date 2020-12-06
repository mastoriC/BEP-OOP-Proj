import org.json.JSONObject;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;

public class Database {

    private JSONObject printObj;
    private String fileName;
    private final String PATH = "./srvFiles/inQueue.dat";
    private File log = new File(PATH);

    private String createJSON() {
        printObj = new JSONObject();
        printObj.put("timestamp", new Timestamp(new Date().getTime()));
        printObj.put("fileName", fileName);
        return printObj.toString();
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void save(String fileName) {
        setFileName(fileName);
        try (
            FileWriter fw = new FileWriter(PATH, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
        ) {
            if (log.length() == 0) {
                pw.print(createJSON());
            } else {
                pw.print(",\n"+createJSON());
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public File getLog() {
        return log;
    }
}
