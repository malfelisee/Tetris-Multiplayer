package main;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

public class Client {
    public static JFrame windows = new JFrame("Tetris");
    
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("127.0.0.1",8080);
        
        List<Integer> Player2 = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        List<Integer> Player1 = new ArrayList<>();
        Player1.add(0);
        Player1.add(0);
        Player1.add(0);
        Player1.add(0);
        Player2.add(390);
        Player2.add(400);
        Player2.add(220);
        Player2.add(1);
        GamePanel gamePanel = new GamePanel(Player1,null);
        windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windows.setResizable(false);
        windows.add(gamePanel);
        windows.pack();
        windows.setLocationRelativeTo(null);
        windows.setVisible(true);
        gamePanel.launchGame();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Thread send = new Thread(){
            public void run(){
                try {
                    while (true) {
                        
                        objectOutputStream.writeObject(gamePanel);
                    }
                } catch (Exception ex) {
                    try {
                        socket.close(); 
                        scan.close();
                    } catch (Exception ex1) {
                        ex.printStackTrace();
                        // TODO: handle exception
                    }
                }
            }
        };

        Thread receive = new Thread(){
            public void run(){
                try {
                    while (true) {
                        GamePanel gamePanel2 = new GamePanel(Player1,Player2);

                        // Recevoir le GamePanel du serveur
                        gamePanel2 = (GamePanel) objectInputStream.readObject();

                        // Afficher le GamePanel
                        gamePanel2.setVisible(true);
                        windows.add(gamePanel2);
                        windows.setVisible(true);

                    }
                } catch (Exception e) {
                    try { 
                        socket.close();
                        scan.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        // TODO: handle exception
                    }
                }

            }
        };
        receive.start();
        send.start();
                
    }
}
