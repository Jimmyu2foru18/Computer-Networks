import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HTTPClient {
  static   boolean stopped = false;
  public static void main(String[] args) {
    Socket clientSocket;
    BufferedReader in;
    PrintWriter out;

    Scanner keyboard = new Scanner(System.in);
    try {
      clientSocket = new Socket(args[0], Integer.parseInt(args[1]));
      out = new PrintWriter(clientSocket.getOutputStream());
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      Thread sender = new Thread(new Runnable() {
        String msg;
        @Override
        public void run() {
          while (!stopped) {
            msg = keyboard.nextLine();
            out.print(msg + "\r\n");
            out.flush();
          }
        }
      });
      sender.start();
      Thread receiver = new Thread(new Runnable() {
        String msg;
        @Override
        public void run() {
          try {
            msg = in.readLine();
            while (msg != null) {
              System.out.println("Server : " + msg);
              msg = in.readLine();
            }

            out.close();
            stopped = true;
            clientSocket.close();
            System.exit(0);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });
      receiver.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}