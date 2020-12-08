import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;

public class Database {

    private JSONObject printObj;
    private String fileName, rand;
    private int page, copy;
    private double price;
    private final String PATH = "./srvFiles/inQueue.json";
    private JSONArray logArr;
    private File log;

    private void addLog() throws IOException {
        read();
        printObj = new JSONObject();
        printObj.put("timestamp", new Timestamp(new Date().getTime()).toString());
        printObj.put("fileName", fileName);
        printObj.put("rand", rand);
        printObj.put("page", page);
        printObj.put("copy", copy);
        printObj.put("price", price);
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
    private void setCopy(int copy) {
        this.copy = copy;
    }
    private void setPrice(double price) {
        this.price = price;
    }

    public void save(String fileName, int page, int copy, double price, String rand) throws IOException {
        setFileName(fileName);
        setRand(rand);
        setCopy(copy);
        setPage(page);
        setPrice(price);
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
