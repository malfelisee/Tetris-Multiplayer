
package main;
import java.io.PrintWriter;
import java.net.Socket;

public class Cl {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
            ClientHandler clientHandler = new ClientHandler(socket, 1);
            Thread clientThread = new Thread(clientHandler);
            clientThread.start();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Message du client");
            Thread.sleep(1000);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
