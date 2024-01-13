package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    private BufferedWriter w = null;
    private BufferedReader r = null;
    public static Scanner scan = new Scanner(System.in);
    public SocketClient(Socket socket) throws IOException{
        this.w =  new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream())
        );

        this.r = new BufferedReader(
            new InputStreamReader(
                socket.getInputStream()
            )
        );
        this.EcrireMessage();
        
    }
    public void EcrireMessage(){
        Thread tw = new Thread(){
            public void run(){
                try {
                    while (true) {
                        System.out.println("Elisee : ");
                        String m = scan.nextLine();
                        w.write(m);
                        w.newLine();
                        w.flush();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        tw.start();
    }
    public void LireMessage(){
        Thread tr = new Thread(){
            public void run(){
                try {
                    while (true) {
                        String m = r.readLine();
                        System.out.println("Israel : "+m);
                        w.write(m);
                        w.newLine();
                        w.flush();
                    }
                } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            };
        tr.start();
        };

        
    }

