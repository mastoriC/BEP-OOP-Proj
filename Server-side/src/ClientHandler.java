import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    String fileName;
    long fileSize;
    int bytesRead;
    byte buff[] = new byte[6022386];


    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    public void run() {
        while (true) {

            try (
                FileWriter fw = new FileWriter("./srvFiles/inQueue.dat", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
            ) {
                fileName = dis.readUTF();
                fileSize = dis.readLong();

                FileOutputStream fos = new FileOutputStream("./srvFiles/" + fileName);
                while (fileSize>0 && (bytesRead = dis.read(buff, 0, (int) Math.min(buff.length, fileSize))) != -1) {
                    fos.write(buff, 0, bytesRead);
                    fileSize -= bytesRead;
                }
                fos.close();

                System.out.println("File saved! (" + fileName + ", size: " + fileSize + " bytes).");
                pw.println(fileName); // Append file name into file list.

                break;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            try {
                this.dis.close();
                this.dos.close();
                this.s.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}
