package com.erudev.io.bio.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pengfei.zhao
 * @date 2020/10/3 15:34
 */
public class ChatServer {
    private ServerSocket serverSocket;
    public static final int DEFAULT_PORT = 8888;
    public final String QUIT = "quit";
    private Map<Integer, Writer> connectionClients;
    private ExecutorService service;

    public ChatServer() {
        this.connectionClients = new HashMap<>();
        service = Executors.newFixedThreadPool(10);
    }

    public void addClient(Socket socket) throws IOException {
        if (socket != null) {
            Writer writer =
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            int port = socket.getPort();
            this.connectionClients.put(port, writer);
            System.out.println("客户端【" + port + "】已连接到服务器");
        }
    }

    public void removeClient(Socket socket) throws IOException {
        if (socket != null) {
            if (this.connectionClients.containsKey(socket.getPort())) {
                this.connectionClients.get(socket.getPort()).close();
            }
            this.connectionClients.remove(socket.getPort());
            System.out.println("客户端【" + socket.getPort() + "】已断开连接");
        }
    }

    public boolean readyToQuit(String msg) {
        return QUIT.equals(msg);
    }

    public void forwardMessage(Socket socket, String forwardMessage) throws IOException {
        for (Integer id : this.connectionClients.keySet()) {
            int port = socket.getPort();
            if (!id.equals(port)) {
                Writer writer = this.connectionClients.get(id);
                writer.write(forwardMessage);
                writer.flush();
            }
        }
    }

    public void close() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                service.execute(new ChatHandler(this, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start();
        System.out.println("服务器启动成功, 端口：" + DEFAULT_PORT + "...");
    }
}
