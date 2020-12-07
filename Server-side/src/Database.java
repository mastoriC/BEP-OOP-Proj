import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;

public class Database {

    private JSONObject printObj;
    private String fileName, rand;
    private int page;
    private final String PATH = "./srvFiles/inQueue.json";
    private JSONArray logArr;
    private File log;

    private void addLog() throws IOException {
        read();
        System.out.println("logArr");
        System.out.println(logArr);
        printObj = new JSONObject();
        printObj.put("timestamp", new Timestamp(new Date().getTime()).toString());
        printObj.put("fileName", fileName);
        printObj.put("rand", rand);
        printObj.put("page", page);
        logArr.add(printObj);
        System.out.println(logArr);
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }
    private void setRand(String rand) {
        this.rand = rand;
    }
    private void setPage(int page) {
        this.page = page;
    }

    public void save(String fileName, int page, String rand) throws IOException {
        setFileName(fileName);
        setRand(rand);
        setPage(page);
        addLog();

        FileWriter fw = new FileWriter(PATH);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        pw.write(logArr.toJSONString());

        pw.close();
        bw.close();
        fw.close();
    }

    private void read() throws IOException {
        log = new File(PATH);
        System.out.println("log length");
        System.out.println(log.length());

        JSONParser parser = new JSONParser();
        if (log.length() == 0) {
            logArr = new JSONArray();
        } else {
            try {
                Object obj = parser.parse(new FileReader(PATH));
                logArr = (JSONArray) obj;
            } catch (ParseException pex) {
                pex.printStackTrace();
            }
        }
    }
    public File getLog() throws IOException {
        return log;
    }
}
