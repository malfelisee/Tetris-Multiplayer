package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame windows = new JFrame("Tetris");
        List<Integer> Player1 = new ArrayList<>();
        List<Integer> Player2 = new ArrayList<>();

        Player1.add(0);
        Player1.add(0);
        Player1.add(0);
        Player1.add(0);

        Player2.add(390);
        Player2.add(400);
        Player2.add(220);
        Player2.add(1);

        windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windows.setResizable(false);

        GamePanel gp = new GamePanel(Player1);
        windows.add(gp);
        windows.pack();

        windows.setLocationRelativeTo(null);
        windows.setVisible(true);
        gp.launchGame();
    }
}