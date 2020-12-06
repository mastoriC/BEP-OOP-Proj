import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    final String HOSTNAME = "localhost";
    final int PORT = 6789;

    Socket clientSocket;
    DataInputStream dis;
    DataOutputStream dos;

    byte buff[];

    private void serverConnect() throws IOException {
        clientSocket = new Socket(HOSTNAME, PORT);
        System.out.println("Successfully connect to server.");
    }

    private void openIO() throws IOException {
        dis = new DataInputStream(clientSocket.getInputStream());
        dos = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("I/O stream created.");
    }

    private void close() throws IOException {
        dis.close();
        dos.close();
        clientSocket.close();
        System.out.println("Connection with server has been closed.");
    }

    private void updateLog() {
        System.out.println("Updating log.");

        try (
            FileOutputStream fos = new FileOutputStream("./cliFiles/log.dat");
        ) {
            long fileSize = dis.readLong();
            int bytesRead;

            while (fileSize>0 && (bytesRead = dis.read(buff, 0, (int) Math.min(buff.length, fileSize))) != -1) {
                fos.write(buff, 0, bytesRead);
                fileSize -= bytesRead;
                break;
            }
            System.out.println("Finish Update.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void sendFile(File file) {
        buff = new byte[(int) file.length()];
        try (
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
        ) {
            serverConnect();
            openIO();
            bis.read(buff, 0, buff.length);

            dos.writeUTF(file.getName());
            dos.writeLong(buff.length);
            dos.write(buff, 0, buff.length);
            dos.flush();

            System.out.println("File has been sent to server. (" + file.getName() + ", size: " + file.length() + " bytes).");
            updateLog();

            close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
