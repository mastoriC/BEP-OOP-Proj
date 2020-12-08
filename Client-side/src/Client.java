import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    private String hostname;
    private final int PORT = 6789;

    private Socket clientSocket;
    DataInputStream dis;
    DataOutputStream dos;

    byte buff[];

    private void serverConnect() throws IOException {
        clientSocket = new Socket(hostname, PORT);
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
            FileOutputStream fos = new FileOutputStream("./cliFiles/log.json");
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

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    public String getHostname() {
        return this.hostname;
    }
    public int getPORT() {
        return this.PORT;
    }

    public void sendFile(File file, int page, int copy, double price) throws ConnectException, UnknownHostException {
        buff = new byte[(int) file.length()];
        try (
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
        ) {
            serverConnect();
            openIO();
            bis.read(buff, 0, buff.length);

            System.out.println(page);

            dos.writeUTF(file.getName());
            dos.writeInt(page);
            dos.writeInt(copy);
            dos.writeDouble(price);
            dos.writeLong(buff.length);
            dos.write(buff, 0, buff.length);
            dos.flush();

            System.out.println("File has been sent to server. (" + file.getName() + ", size: " + file.length() + " bytes).");
            updateLog();

            close();
        } catch (UnknownHostException uhex) {
            throw uhex;
        } catch (ConnectException conex) {
            throw conex;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
