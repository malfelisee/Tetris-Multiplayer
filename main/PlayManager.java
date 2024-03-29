package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import main.mimo.Block;
import main.mimo.KeyHandler;
import main.mimo.Mino;
import main.mimo.Mino_Bar;
import main.mimo.Mino_L1;
import main.mimo.Mino_L2;
import main.mimo.Mino_Square;
import main.mimo.Mino_T;
import main.mimo.Mino_Z1;
import main.mimo.Mino_Z2;

public class PlayManager{
    
    public static final String KeyHandler = null;
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;
    KeyHandler TOUCHE;
    Mino currentMino;
    //Mino currentMino2;
    final int MINO_START_X;
    final int MINO_START_Y;

    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    public static int dropInterval = 60;

    boolean gameOver;

    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    int level = 1;
    int lines;
    int scores;

    int positionX,positionleft_x,petit_x;

    public PlayManager(int positionX, int positionleft_x, int petit_x){

        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;
        this.positionX = positionX;
        this.positionleft_x = positionleft_x;
        this.petit_x = petit_x;

        MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = right_x + 90 + petit_x;
        NEXTMINO_Y = top_y + 500;
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X - positionX, MINO_START_Y);

        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

    }
    private Mino pickMino(){
        Mino mino = null;
        int i = new Random().nextInt(7);

        switch (i) {
            case 0: mino = new Mino_L1();break;
            case 1: mino = new Mino_L2();break;
            case 2: mino = new Mino_Square();break;
            case 3: mino = new Mino_Bar();break;
            case 4: mino = new Mino_T();break;
            case 5: mino = new Mino_Z1();break;
            case 6: mino = new Mino_Z2();break;
        }
        return mino;
    }

    public void update(){
        if (currentMino.active == false ) {
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            if(currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y){
                gameOver = true;
            }

            currentMino.desactivating = false;

            currentMino = nextMino;
            currentMino.setXY(MINO_START_X - positionX, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

            CheckDelete();
        }
       
        else{
            currentMino.update();
        }
    }
    private void CheckDelete(){
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;
        

        while (x < right_x && y < bottom_y) {
            for(int i = 0; i < staticBlocks.size(); i++){
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    blockCount++;
                }
            }

            x += Block.SIZE;  

            if (x == right_x) {
                if (blockCount == 12) {

                    effectCounterOn = true;
                    effectY.add(y);

                    for(int i = staticBlocks.size()-1; i > -1 ; i--){
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);    
                        }
                    }

                    lineCount++;
                    lines++;

                    if (lines % 10 == 10 && dropInterval > 1) {
                        level++;
                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        }else{
                            dropInterval -= 1;
                        }
                    }

                    for(int i = 0; i < staticBlocks.size(); i++){
                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }


                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
            if (lineCount > 0) {
                int singleLineScore = 10 * level;
                scores += singleLineScore * lineCount;
            }
            
        }

    }

    public void draw(Graphics2D g2){
        //terrain 1
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4-positionleft_x, top_y-4, WIDTH+8, HEIGHT+8);


        //petit terrain
        int x= right_x + 20;
        int y = bottom_y - 200;
        g2.drawRect(x+petit_x, y, 180, 180);
        g2.setFont(new Font("Arial", Font.PLAIN,20));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x+50 + petit_x, y+60);

        g2.drawRect(x+petit_x, top_y, 220, 250);
        x += 10;
        y = top_y + 90;

        g2.drawString("LEVEL: " + level, x+petit_x, y); y+=70;
        g2.drawString("LINES: " + lines, x+petit_x, y); y+=70;
        g2.drawString("SCORE: " + scores, x+petit_x, y);


        //Mino
        if (currentMino != null) {
            currentMino.draw(g2);
        }
        
        nextMino.draw(g2);

        for(int i = 0; i < staticBlocks.size(); i++){
            staticBlocks.get(i).draw(g2);
        }

        if (effectCounterOn) {
            effectCounter ++;
            g2.setColor(Color.red);
            for(int i = 0; i < effectY.size(); i ++){
                g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
            }

            if (effectCounter == 10) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));

        if (gameOver) {
            x = left_x + 25;
            y = top_y + 320;
            g2.drawString("GAME OVER", x-positionleft_x, y);
        }

        else if (main.mimo.KeyHandler.pausePressed) {
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSED", x-positionleft_x, y);
        }


    }
}
