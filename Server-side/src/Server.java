import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        String fileName;
        long fileSize;
        int bytesRead;
        byte buff[] = new byte[6022386];

        System.out.println("Server started!");

        try (
            ServerSocket handshakeSocket = new ServerSocket(6789);
            Socket connectionSocket = handshakeSocket.accept();
            InputStream is = connectionSocket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
        ) {
            fileName = dis.readUTF();
            fileSize = dis.readLong();

            FileOutputStream fos = new FileOutputStream(fileName);
            while (fileSize>0 && (bytesRead = dis.read(buff, 0, (int) Math.min(buff.length, fileSize))) != -1) {
                fos.write(buff, 0, bytesRead);
                fileSize -= bytesRead;
            }

            fos.close();
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }
}
