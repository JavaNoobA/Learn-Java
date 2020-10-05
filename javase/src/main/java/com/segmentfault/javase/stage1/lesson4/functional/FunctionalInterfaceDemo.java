package com.segmentfault.javase.stage1.lesson4.functional;

/**
 * @author pengfei.zhao
 * @date 2020/10/5 15:14
 */
public class FunctionalInterfaceDemo {

    public static void main(String[] args) {
        Function1 function1 = () -> {};

        Function2 function2 = () -> "aaa";
    }

    @FunctionalInterface
    public static interface Function1{
        public abstract void execute();

        // 不能出现两次抽象方法定义
        //void execute3();

        default String execute2(){
            return String.valueOf(this);
        };
    }

    // @FunctionalInterface 并非必选的
    public interface Function2{
        String execute();
    }
}
