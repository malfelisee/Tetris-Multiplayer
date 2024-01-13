package main;

import java.io.*;
import java.net.*;
public class MultiClientServer {
    public static void main(String[] args) {
        int portNumber = 8080;
        int clientCount = 0;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Serveur en attente de connexions sur le port " + portNumber);

            
                Socket clientSocket = serverSocket.accept();
                clientCount++;
                System.out.println("Nouvelle connexion établie : " + clientSocket);

                // Créez un nouveau thread pour gérer le client
                Thread clientThread = new Thread(new ClientHandler(clientSocket, clientCount));
                clientThread.start();
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du serveur sur le port " + portNumber);
            e.printStackTrace();
        }
    }
}

