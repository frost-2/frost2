package com.frost2.myspi.spi.impl;

import com.frost2.myspi.spi.IPerson;

/**
 * @author frost2
 * @date 2022-07-04 17:46
 */
public class YellowPerson implements IPerson {
    @Override
    public void think() {
        System.out.println("yellow think");
    }
}
