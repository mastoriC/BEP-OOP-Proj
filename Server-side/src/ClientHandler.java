import org.json.simple.*;

import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

public class ClientHandler extends Thread {
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;

    private String fileName;
    private long fileSize;
    private int bytesRead;
    private byte buff[] = new byte[6022386];

    private Database db = new Database();

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    private void updateClient(File log) {
        byte logBuff[] = new byte[(int) log.length()];
        try (
            FileInputStream fis = new FileInputStream(log);
            BufferedInputStream bis = new BufferedInputStream(fis);
        ) {
            bis.read(buff, 0, logBuff.length);
            dos.writeLong(buff.length);
            dos.write(buff, 0, logBuff.length);
            System.out.println("Update log to user.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                fileName = dis.readUTF();
                fileSize = dis.readLong();

                FileOutputStream fos = new FileOutputStream("./srvFiles/" + fileName);
                while (fileSize>0 && (bytesRead = dis.read(buff, 0, (int) Math.min(buff.length, fileSize))) != -1) {
                    fos.write(buff, 0, bytesRead);
                    fileSize -= bytesRead;
                }
                fos.close();

                db.save(fileName);
                System.out.println("File saved! (" + fileName + ", size: " + fileSize + " bytes).");

                updateClient(db.getLog());
                break;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            /* Close connection with client. */
            try {
                this.dis.close();
                this.dos.close();
                this.s.close();
                System.out.println("Finish process, close connection.");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}
