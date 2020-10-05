package com.segmentfault.javase.stage1.lesson4.functional;

import java.io.File;
import java.util.function.Predicate;

/**
 * @author pengfei.zhao
 * @date 2020/10/5 16:23
 */
public class PredicateDemo {
    public static void main(String[] args) {
        Predicate<File> predicate = File::canRead;
    }
}
