package main.mimo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class KeyHandler implements KeyListener{
    public static  boolean upPressed,downPressed,leftPressed,rightPressed,pausePressed;
    public int leftKey, rightKey, downKey, rotateKey;
    public KeyHandler( int leftKey, int rightKey, int downKey, int rotateKey) {
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.downKey = downKey;
        this.rotateKey= rotateKey;
    }
    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == rotateKey) {
            upPressed = true;
        }
        if (code == leftKey) {
            leftPressed = true;
        }
        if (code == downKey) {
            downPressed = true;
        }
        if (code == rightKey) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            if (pausePressed) {
                pausePressed = false;
            }
            else{
                pausePressed = true;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }
    
}
