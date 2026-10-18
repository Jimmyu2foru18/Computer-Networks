import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("137.139.52.48", 5000)) {
            System.out.println("Connected to server.");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("Hello Server it is me jimmy!");
            String response = in.readLine();
            System.out.println("Server says: " + response);

            out.println("Hows it going fellas");
            response = in.readLine();
            System.out.println("Server says: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
