package com.segmentfault.javase.stage1.lesson3.generic;

import org.springframework.core.ResolvableType;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author pengfei.zhao
 * @date 2020/10/5 14:17
 */
public class QuestionAndAnswer<T extends Serializable> {

    private List<Map<String, List<String>>> values = Collections.emptyList();

    public static void main(String[] args) throws Exception {
        question1();
    }

    private static void question1() throws Exception {
        // 获取成员泛型类型参数
        Field field = QuestionAndAnswer.class.getDeclaredField("values");
        ResolvableType resolvableType = ResolvableType.forField(field);
        System.out.println(resolvableType.getGeneric(0));

        // 获取类泛型参数
        TypeVariable[] parameters = QuestionAndAnswer.class.getTypeParameters();
        for (TypeVariable parameter : parameters) {
            System.out.println(parameter);
        }

        // 具体参数类型
        QuestionAndAnswer<String> a = new QuestionAndAnswer<String>();
        parameters = a.getClass().getTypeParameters();

        for (TypeVariable parameter : parameters) {
            System.out.println(parameter);
        }
    }
}
