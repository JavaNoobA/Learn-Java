package com.erudev.io.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server Socket Sample
 * @author pengfei.zhao
 * @date 2020/10/3 12:48
 */
public class Server {
    public static void main(String[] args) {
        final String QUIT = "quit";
        final int DEFAULT_PORT = 8888;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("服务器已启动，端口" + DEFAULT_PORT);
            Socket socket = serverSocket.accept();
            System.out.println("客户端【" + socket.getPort() + "】 已连接");
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String msg = null;
            while ((msg = reader.readLine()) != null){
                System.out.println("客户端【" + socket.getPort() + "】" + "消息：" + msg);

                if (QUIT.equals(msg)){
                    break;
                }
                writer.write("服务器 " + msg + "\n");
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
