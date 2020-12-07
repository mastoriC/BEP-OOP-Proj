import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.text.html.parser.Parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LogArray {
    private final String PATH = "./cliFiles/log.json";
    private JSONArray arr;
    private int size;

    public LogArray() {
        readFile();
    }

    private void readFile() {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(PATH));
            arr = (JSONArray) obj;
            size = arr.size();
        } catch (Exception ex) {}
    }

    public JSONArray getArr() {
        return arr;
    }

    public int getSize() {
        return size;
    }

    public void refresh() {
        readFile();
    }
}
