package com.frost2.javabasic.map;

import java.util.function.Function;

/**
 * @author frost2
 * @date 2023-03-26 14:51
 */
public class FunctionDemo {

    public static void main(String[] args) {
        apply();
        addThen();
    }

    public static void apply() {
        Function<String, String> function = new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s + "1";
            }
        };
        String hello = function.apply("hello");
        System.out.println(hello); //结果:hello1
    }

    public static void addThen() {
        Function<String, String> f1 = new Function<String, String>() {

            @Override
            public String apply(String s) {
                return s + "2";
            }
        };
        Function<String, String> f2 = new Function<String, String>() {

            @Override
            public String apply(String s) {
                return s + "3";
            }
        };
        //将f1.apply结果赋值到f2.apply的入参
        Function<String, String> function = f1.andThen(f2);  //断点到该处并不会执行,会等待先执行f1.apply
        String apply = function.apply("1");
        System.out.println(apply);   //结果:123
    }


}