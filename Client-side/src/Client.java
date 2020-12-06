import java.io.*;
import java.net.*;
import java.util.*;

public class Client{
    public void sendFile(File file)  {
        byte buff[] = new byte[(int) file.length()];
        try (
            Socket clientSocket = new Socket("localhost", 6789);

            OutputStream os = clientSocket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
        ) {
            bis.read(buff, 0, buff.length);

            dos.writeUTF(file.getName());
            dos.writeLong(buff.length);
            dos.write(buff, 0, buff.length);
            dos.flush();

            os.write(buff, 0, buff.length);
            os.flush();
            System.out.println("File has been sent.");
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }

}
