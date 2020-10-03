package com.erudev.io.bio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author pengfei.zhao
 * @date 2020/10/3 15:34
 */
public class ChatHandler implements Runnable {
    private ChatServer chatServer;
    private Socket socket;

    public ChatHandler(ChatServer chatServer, Socket socket) {
        this.chatServer = chatServer;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            this.chatServer.addClient(socket);
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String msg = null;
            while ((msg = reader.readLine()) != null){
                String fwdMsg = "客户端【" + socket.getPort() + "】" + msg + "\n";
                System.out.println(fwdMsg);
                this.chatServer.forwardMessage(socket, fwdMsg);

                if (this.chatServer.readyToQuit(msg)){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.chatServer.removeClient(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
