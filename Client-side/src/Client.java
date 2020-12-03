import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String sentence, modifiedSentence;
        try (
                Socket clientSocket = new Socket("localhost", 6789);
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            sentence = sc.nextLine();
            while(!sentence.equals(("end"))) {
                outToServer.writeBytes(sentence+"\n");
                modifiedSentence = inFromServer.readLine();
                System.out.println("FROM SERVER: " + modifiedSentence);
                sentence = sc.nextLine();
            }
        } catch (IOException err) {
            System.out.println(err);
        }
    }
}
