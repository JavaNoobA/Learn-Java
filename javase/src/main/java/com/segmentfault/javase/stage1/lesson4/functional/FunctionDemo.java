package com.segmentfault.javase.stage1.lesson4.functional;

import java.util.function.Function;

/**
 * @author pengfei.zhao
 * @date 2020/10/5 15:39
 */
public class FunctionDemo {
    public static void main(String[] args) {
        Function<String, Long> string2Long = Long::valueOf;
        System.out.println(string2Long.apply("1"));

        Long value = string2Long.compose(String::valueOf).apply(1L);
    }
}
