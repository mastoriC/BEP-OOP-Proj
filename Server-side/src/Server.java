import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        String clientSentence, capitailizedSentence;
        try (
            ServerSocket welcomeSocket = new ServerSocket(6789);
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        ) {
            while (true) {
                clientSentence = inFromClient.readLine();
                if (clientSentence == null) break;
                capitailizedSentence = clientSentence.toUpperCase() + '\n';
                outToClient.writeBytes(capitailizedSentence);
            }
        } catch (IOException err) {
            System.out.println(err);
        }
    }
}
