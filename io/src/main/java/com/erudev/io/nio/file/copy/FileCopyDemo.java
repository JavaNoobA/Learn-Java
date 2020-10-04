package com.erudev.io.nio.file.copy;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author pengfei.zhao
 * @date 2020/10/4 8:43
 */

interface FileCopyRunner {
    void copyFile(File source, File target);
}

public class FileCopyDemo {

    public static final int ROUND = 5;

    public static void benchmark(FileCopyRunner runner, File source, File target) {
        long escaped = 0L;
        for (int i = 0; i < ROUND; i++) {
            long startTime = System.currentTimeMillis();
            runner.copyFile(source, target);
            escaped += System.currentTimeMillis() - startTime;
            target.delete();
        }
        System.out.println(runner + ":" + escaped / ROUND);
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // 不使用缓冲区，Stream流实现
        FileCopyRunner noBufferStreamCopy = new FileCopyRunner() {
            @Override
            public void copyFile(File source, File target) {
                InputStream fis = null;
                OutputStream fos = null;

                try {
                    fis = new FileInputStream(source);
                    fos = new FileOutputStream(target);
                    int result;
                    while ((result = fis.read()) != -1) {
                        fos.write(result);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    close(fis);
                    close(fos);
                }
            }

            @Override
            public String toString() {
                return "noBufferStreamCopy";
            }
        };
        // 使用缓存区
        FileCopyRunner bufferedStreamCopy = new FileCopyRunner() {
            @Override
            public void copyFile(File source, File target) {
                InputStream fis = null;
                OutputStream fos = null;

                try {
                    fis = new BufferedInputStream(new FileInputStream(source));
                    fos = new BufferedOutputStream(new FileOutputStream(target));
                    byte[] buffer = new byte[1024];
                    int result;
                    while ((result = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, result);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String toString() {
                return "bufferedStreamCopy";
            }
        };
        FileCopyRunner nioFileCopy = new FileCopyRunner() {
            @Override
            public void copyFile(File source, File target) {
                FileChannel fin = null;
                FileChannel fout = null;
                try {
                    fin = new FileInputStream(source).getChannel();
                    fout = new FileOutputStream(target).getChannel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    while (fin.read(buffer) != -1) {
                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            fout.write(buffer);
                        }
                        buffer.clear();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    close(fin);
                    close(fout);
                }
            }

            @Override
            public String toString() {
                return "nioFileCopy";
            }
        };
        FileCopyRunner nioTransferCopy = new FileCopyRunner() {
            @Override
            public void copyFile(File source, File target) {
                FileChannel fin = null;
                FileChannel fout = null;

                try {
                    fin = new FileInputStream(source).getChannel();
                    fout = new FileOutputStream(target).getChannel();
                    long transferred = 0L;
                    long size = fin.size();
                    while (transferred != size) {
                        transferred += fin.transferTo(0, fin.size(), fout);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    close(fin);
                    close(fout);
                }
            }

            @Override
            public String toString() {
                return "nioTransferCopy";
            }
        };

        System.out.println("---------Copy Small File----------");
        File smallFile = new File("C:\\Code\\do\\2020\\202010\\Learn-Java\\io\\src\\main\\resources\\tmp\\small.zip");
        File smallTargetFile = new File("C:\\Code\\do\\2020\\202010\\Learn-Java\\io\\src\\main\\resources\\tmp\\small-copy.zip");
        benchmark(noBufferStreamCopy, smallFile, smallTargetFile);
        benchmark(bufferedStreamCopy, smallFile, smallTargetFile);
        benchmark(nioFileCopy, smallFile, smallTargetFile);
        benchmark(nioTransferCopy, smallFile, smallTargetFile);

        System.out.println("---------Copy Big File----------");
        File bigFile = new File("C:\\Code\\do\\2020\\202010\\Learn-Java\\io\\src\\main\\resources\\tmp\\big.zip");
        File bigTargetFile = new File("C:\\Code\\do\\2020\\202010\\Learn-Java\\io\\src\\main\\resources\\tmp\\big-copy.zip");
        //benchmark(noBufferStreamCopy, bigFile, bigTargetFile);
        benchmark(bufferedStreamCopy, bigFile, bigTargetFile);
        benchmark(nioFileCopy, bigFile, bigTargetFile);
        benchmark(nioTransferCopy, bigFile, bigTargetFile);

        System.out.println("---------Copy Huge File----------");
        File hugeFile = new File("C:\\Code\\do\\2020\\202010\\Learn-Java\\io\\src\\main\\resources\\tmp\\huge.vep");
        File hugeTargetFile = new File("C:\\Code\\do\\2020\\202010\\Learn-Java\\io\\src\\main\\resources\\tmp\\huge-copy.vep");
        //benchmark(noBufferStreamCopy, bigFile, bigTargetFile);
        benchmark(bufferedStreamCopy, hugeFile, hugeTargetFile);
        benchmark(nioFileCopy, hugeFile, hugeTargetFile);
        benchmark(nioTransferCopy, hugeFile, hugeTargetFile);
    }

}
