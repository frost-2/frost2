package com.frost2.javabasic.map;

import java.util.HashMap;
import java.util.Map;

/**
 * @author frost2
 * @date 2023-03-26 16:35
 */
public class MapDefaultMetDemo {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        //当key不存在时，通过Function#apply处理，并新增到map中
        map.computeIfAbsent("4", key -> key);
        //当key不存在时，通过BiFunction#apply处理，并更新原值
        String s1 = map.computeIfPresent("1", (key, value) -> key + value);
        System.out.println("s1 = " + s1);
        System.out.println("map = " + map);
    }
}
