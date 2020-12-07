import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;

    private Database db;
    private Randomizer randomizer;

    private String fileName;
    private long fileSize;
    private int page, bytesRead;
    private byte buff[];

    private final String DIR = "./srvFiles/";
    private String randedStr;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;

        db = new Database();
        randomizer = new Randomizer();

        buff = new byte[6022386];
        randedStr = randomizer.randStr();
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

    private void createFile() throws IOException {
        String fileSaveName = DIR + randedStr + "_" + fileName;
        FileOutputStream fos = new FileOutputStream(fileSaveName);
        while (fileSize>0 && (bytesRead = dis.read(buff, 0, (int) Math.min(buff.length, fileSize))) != -1) {
            fos.write(buff, 0, bytesRead);
            fileSize -= bytesRead;
        }
        fos.close();
    }

    public void run() {
        while (true) {
            try {
                fileName = dis.readUTF();
                page = dis.readInt();
                fileSize = dis.readLong();

                createFile(); // copy file into server's directory.

                db.save(fileName, page, randedStr);
                System.out.println("File saved! (" + fileName + ", size: " + fileSize + " bytes).");

                updateClient(db.getLog()); // Update new log to client.
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
