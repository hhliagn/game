package com.client;

import java.io.IOException;

public class Client {
    //netty socket
    public static void main(String[] args) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyClientWindow1 frame = null;
                try {
                    frame = new MyClientWindow1();
                    frame.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //socket
    /*public static void main(String[] args) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyClientWindow frame = null;
                try {
                    frame = new MyClientWindow();
                    frame.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/
}
