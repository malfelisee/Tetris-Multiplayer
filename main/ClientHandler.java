package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

class ClientHandler implements Runnable {
    public static JFrame windows = new JFrame("Tetris");
    private final Socket clientSocket;
    private final int clientNumber;
    private final List<Integer> Player1 = new ArrayList<>();
    private final List<Integer> Player2 = new ArrayList<>();

    public static GamePanel gamePanel ;
    
    public ClientHandler(Socket socket, int clientNumber) {
        this.clientSocket = socket;
        this.clientNumber = clientNumber;
        Player1.add(0);
        Player1.add(0);
        Player1.add(0);
        Player1.add(0);
        Player2.add(390);
        Player2.add(400);
        Player2.add(220);
        Player2.add(1);
        gamePanel = new GamePanel(Player1);
        windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windows.setResizable(false);
        windows.add(gamePanel);
        windows.pack();
        windows.setLocationRelativeTo(null);
        windows.setVisible(true);
        gamePanel.launchGame();
    }
    
    @Override
    public void run() {
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            Scanner scan = new Scanner(System.in);
        ) {
            
            Thread tw = new Thread(){
                public void run(){
                    try {
                        while (true) {
                            objectOutputStream.writeObject(gamePanel);
                            
                        }
                    } catch (Exception ex) {
                        try { 
                            scan.close();
                        } catch (Exception ex1) {
                            ex.printStackTrace();
                            // TODO: handle exception
                        }
                    }
                }
            };
    
            Thread tr = new Thread(){
                public void run(){
                    try {
                        while (true) {
                            GamePanel gamePanel2 = new GamePanel(null);
                            
                            // Recevoir le GamePanel du serveur
                            gamePanel2 = (GamePanel) objectInputStream.readObject();

                            // Afficher le GamePanel
                            windows.add(gamePanel2);
                            windows.setVisible(true);
                            gamePanel2.launchGame();
                        }
                    } catch (Exception e) {
                        try { 
                            scan.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            // TODO: handle exception
                        }
                    }
    
                }
            };
            tr.start();
            tw.start();

            // String inputLine;

            // while ((inputLine = in.readLine()) != null) {
            //     System.out.println("Client " + clientNumber + " : " + inputLine);
            //     out.println("Message reçu par le serveur.");
            // }
        } catch (IOException e) {
            System.err.println("Erreur de communication avec le client " + clientNumber);
            e.printStackTrace();
        }
    }
}
