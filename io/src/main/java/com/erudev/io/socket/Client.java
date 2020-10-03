package com.erudev.io.socket;

import java.io.*;
import java.net.Socket;

/**
 * Client Socket Sample
 * @author pengfei.zhao
 * @date 2020/10/3 12:48
 */
public class Client {
    public static void main(String[] args) {
        final String QUIT = "quit";
        final String LOCAL_HOST = "127.0.0.1";
        final int DEFAULT_SERVER_PORT = 8888;
        Socket socket = null;

        try {
            socket = new Socket(LOCAL_HOST, DEFAULT_SERVER_PORT);
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            BufferedReader consoleReader =
                    new BufferedReader(new InputStreamReader(System.in));
            String msg = null;
            while (true){
                msg = consoleReader.readLine();
                if (QUIT.equals(msg)){
                    break;
                }
                writer.write(msg + "\n");
                writer.flush();
                String serverMsg = reader.readLine();
                System.out.println(serverMsg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
