package com.segmentfault.javase.stage1.lesson4.functional;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author pengfei.zhao
 * @date 2020/10/5 16:24
 */
public class PredicateDesignDemo {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(filter(list, condition -> condition % 2 == 0));
        System.out.println(filter(list, condition -> condition % 2 != 0));
    }

    public static <E> Collection<E> filter(Collection<E> source, Predicate<E> predicate) {
        // 请不要直接使用源数据进行操作
        List<E> list = new ArrayList<>(source);
        Iterator<E> iterator = list.iterator();
        while (iterator.hasNext()) {
            E element = iterator.next();
            if (!predicate.test(element)) {
                iterator.remove();
            }
        }
        return Collections.unmodifiableList(list);
    }
}
