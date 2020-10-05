package com.segmentfault.javase.stage1.lesson3.generic;

import com.sun.org.apache.xpath.internal.operations.String;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author pengfei.zhao
 * @date 2020/10/5 10:38
 */
public class GenericParameterTypeDemo {
    public static void main(String[] args) {
        Container s = new Container("String");
        Container<StringBuffer> i = new Container("String");
        System.out.println(i);

        add(new ArrayList<>(), "aa");
        add(new ArrayList<>(), 1);
    }

    // 声明上界限，必须是 CharSequence 子类
    public static class Container<E extends CharSequence> {
        private E element;

        public Container(E element) {
            this.element = element;
        }
    }

    // 多界限泛型参数类型
    public static class C {
    }

    public static interface I {
    }

    public static interface I2 {
    }

    public static class Template<T extends C & I & I2> {
    }

    public static class TClass /*extends C*/ implements I, I2 {
    }

    public static <C extends Collection<E>, E extends Serializable> void add(C target, E element) {
        target.add(element);
    }
}
