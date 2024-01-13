package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Serveur {
    public static void main(String[] args) throws UnknownHostException, IOException {
        try (ServerSocket serveur = new ServerSocket(345)) {
            while (true) {
                Socket socket = serveur.accept();
                new SocketClient(socket);                
            }
        }
 
    }
}

