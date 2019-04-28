package com.game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest {

    public static void main(String[] args) throws IOException {
        System.out.println("创建socket...");
        ServerSocket serverSocket = new ServerSocket(23456);
        Socket socket = serverSocket.accept();

        System.out.println("接收到socket...");

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter printWriter = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()));

        String line = null;
        while ((line = bufferedReader.readLine())!=null){
            System.out.println(line);

            if (line.equals("move")){
                String move = move();
                printWriter.write(move+"\n");
                printWriter.flush();
                //printWriter.close();
            }
        }

    }

    private static String move() {
        return "move success!";
    }
}
