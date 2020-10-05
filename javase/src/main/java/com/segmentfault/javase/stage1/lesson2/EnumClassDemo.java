package com.segmentfault.javase.stage1.lesson2;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 枚举类设计
 * @author pengfei.zhao
 * @date 2020/10/5 8:53
 */
public class EnumClassDemo {
    public static void main(String[] args) {
        // 自己实现的 values()
        Stream.of(printCountingMembers()).forEach(System.out::println);
        // Enum 字节码提升 values()
        Stream.of(printCountingEnumMembers()).forEach(System.out::println);
    }

    public static CountingEnum[] printCountingEnumMembers(){
        return Stream.of(CountingEnum.values())
                .collect(Collectors.toList())
                .toArray(new CountingEnum[0]);
    }

    public static Counting[] printCountingMembers(){
        Field[] fields = Counting.class.getDeclaredFields();
        return Stream.of(fields).filter(field -> {
            int modifiers = field.getModifiers();
            return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
        }).map(field -> {
            try {
                return (Counting)field.get(null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList()).toArray(new Counting[0]);
    }
}

class Data{}

/**
 * 可继承，实现接口
 */
abstract class Counting extends Data implements Cloneable{
    private int value;
    public static final Counting ONE = new Counting(1) {
        @Override
        String valueAsString() {
            return String.valueOf(this.getValue());
        }
    };
    public static final Counting TWO = new Counting(2) {
        @Override
        String valueAsString() {
            return String.valueOf(this.getValue());
        }
    };
    public static final Counting THREE = new Counting(3) {
        @Override
        String valueAsString() {
            return String.valueOf(this.getValue());
        }
    };
    public static final Counting FOUR = new Counting(4) {
        @Override
        String valueAsString() {
            return String.valueOf(this.getValue());
        }
    };
    public static final Counting FIVE = new Counting(5) {
        @Override
        String valueAsString() {
            return String.valueOf(this.getValue());
        }
    };

    private Counting(int value) {
        this.value = value;
    }

    abstract String valueAsString();

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Counting :" + value;
    }
}

/**
 * 默认是 final，不可继承，可实现接口
 */
enum CountingEnum implements Cloneable{
    ONE(1){
        @Override
        String valueToString() {
            return String.valueOf(getValue());
        }
    },
    TWO(2){
        @Override
        String valueToString() {
            return String.valueOf(getValue());
        }
    },
    THREE(3){
        @Override
        String valueToString() {
            return String.valueOf(getValue());
        }
    },
    FOUR(4){
        @Override
        String valueToString() {
            return String.valueOf(getValue());
        }
    },
    FIVE(5){
        @Override
        String valueToString() {
            return String.valueOf(getValue());
        }
    };

    private int value;
    /*private*/ CountingEnum(int value){
        this.value = value;
    }

    abstract String valueToString();

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CountingEnum :" + value;
    }
}
