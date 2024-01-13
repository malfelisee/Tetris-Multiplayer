package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JPanel;

import main.mimo.KeyHandler;

public class GamePanel extends JPanel implements Runnable{

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;
    PlayManager pm;
    PlayManager pm2;

    public GamePanel(List<Integer> Player1){
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        
        this.setFocusable(true);
        
        this.addKeyListener(new KeyHandler(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_UP));
        this.addKeyListener(new KeyHandler(KeyEvent.VK_Q, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_Z));
        
        pm = new PlayManager(Player1.get(0),Player1.get(1),Player1.get(2));        
    }
    public void launchGame(){
        gameThread = new Thread(this);
        gameThread.start(); 
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1 ){
                update();
                repaint();
                delta--;
            }
        }
    }
    private void update(){
        if (KeyHandler.pausePressed == false && pm.gameOver == false) {
            pm.update();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        pm.draw(g2);
    } 
    
}