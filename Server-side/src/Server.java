import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

        final int PORT = 6789;

        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("BEP Server activated.");
        while (true) {
            Socket s = null;
            try {
                s = ss.accept();
                System.out.println("New Client Connected ("+s+").");

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("New thread assigned to this client.");
                Thread t = new ClientHandler(s, dis, dos);
                t.start();
            } catch (IOException ioe) {
                s.close();
                ioe.printStackTrace();
            }
        }
    }
}
