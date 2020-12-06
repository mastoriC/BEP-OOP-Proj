import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(6789);
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
