import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    Socket clientSocket;
    DataInputStream dis;
    DataOutputStream dos;

    public Client() {
        try {
            serverConnect();
            openIO();
        } catch (ConnectException cex) {
            System.out.println("Server offline.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void serverConnect() throws IOException {
        clientSocket = new Socket("localhost", 6789);
    }

    private void openIO() throws IOException {
        dis = new DataInputStream(clientSocket.getInputStream());
        dos = new DataOutputStream(clientSocket.getOutputStream());
    }

    private void closeIO() throws IOException {
        dis.close();
        dos.close();
    }

    public void sendFile(File file) {
        byte buff[] = new byte[(int) file.length()];
        try (
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
        ) {
            openIO();
            bis.read(buff, 0, buff.length);

            dos.writeUTF(file.getName());
            dos.writeLong(buff.length);
            dos.write(buff, 0, buff.length);
            dos.flush();

            System.out.println("File has been sent to server. (" + file.getName() + ", size: " + file.length() + " bytes).");
            closeIO();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
