package com.client;

import java.io.IOException;

public class Client {


    public static void main(String[] args) throws IOException {
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
    }
}
