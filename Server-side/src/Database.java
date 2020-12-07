

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;

public class Database {

    private JSONObject printObj;
    private String fileName;
    private final String PATH = "./srvFiles/inQueue.json";
    private JSONArray logArr;
    private File log = new File(PATH);

    private void addLog() {
        printObj = new JSONObject();
        printObj.put("timestamp", new Timestamp(new Date().getTime()));
        printObj.put("fileName", fileName);
        logArr.add(printObj);
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void save(String fileName) {
        setFileName(fileName);
        try (
            FileWriter fw = new FileWriter(PATH);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
        ) {
            addLog();
            pw.write(logArr.toJSONString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void read() throws IOException {
        try (
            FileReader fr = new FileReader(PATH);
        ) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(fr);
            logArr = (JSONArray) obj;

        } catch (FileNotFoundException | ParseException fnf) {
            FileWriter fw = new FileWriter(PATH);
            JSONArray emptyArr = new JSONArray();
            fw.write(emptyArr.toJSONString());
        }
    }
    public File getLog() throws IOException {
        return log;

    }
}
