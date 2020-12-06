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

            FileWriter fw = new FileWriter("./srvFiles/inQueue.dat", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
        ) {
            fileName = dis.readUTF();
            fileSize = dis.readLong();

            try (FileOutputStream fos = new FileOutputStream("./srvFiles/" + fileName)) {
                while (fileSize>0 && (bytesRead = dis.read(buff, 0, (int) Math.min(buff.length, fileSize))) != -1) {
                    fos.write(buff, 0, bytesRead);
                    fileSize -= bytesRead;
                }
            } catch (IOException FOSexc) {
                throw FOSexc;
            }

            pw.println(fileName); // Append file name into file list.

        } catch (IOException exc) {
            System.out.println(exc);
        }
    }
}
