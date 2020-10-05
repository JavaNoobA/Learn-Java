package com.segmentfault.javase.stage1.lesson4.functional;

import java.util.function.Supplier;

/**
 * @author pengfei.zhao
 * @date 2020/10/5 15:42
 */
public class SupplierDemo {
    public static void main(String[] args) {
        Supplier<Long> supplier = getLong();
    }

    private static Supplier<Long> getLong() {
        return System::currentTimeMillis;
    }
}
