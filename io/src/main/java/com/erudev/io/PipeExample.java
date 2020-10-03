package com.erudev.io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author pengfei.zhao
 * @date 2020/10/2 16:27
 */
public class PipeExample {
    public static void main(String[] args) throws IOException {
        final PipedOutputStream pos = new PipedOutputStream();
        final PipedInputStream pis = new PipedInputStream(pos);

        Thread t1 = new Thread(() -> {
            try {
                pos.write("hello io".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                int data = -1;
                while ((data = pis.read()) != -1) {
                    System.out.println((char) data);
                    //data = pis.read();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }
}
