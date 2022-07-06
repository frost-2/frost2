package com.frost2.myspi;

import com.frost2.myspi.spi.IPerson;
import com.frost2.myspi.spi.ISay;

/**
 * @author frost2
 * @date 2022-07-05 20:39
 */
public class MiniServiceLoaderTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        MiniServiceLoader miniServiceLoader = MiniServiceLoader.getInstance();
        IPerson yellow = miniServiceLoader.get(IPerson.class, "yellow");
        yellow.think();
        ISay say = miniServiceLoader.get(ISay.class, "yellow");
        say.say();
    }
}
