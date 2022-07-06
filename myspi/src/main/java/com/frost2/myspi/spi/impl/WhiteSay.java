package com.frost2.myspi.spi.impl;

import com.frost2.myspi.spi.ISay;

/**
 * @author frost2
 * @date 2022-07-04 17:46
 */
public class WhiteSay implements ISay {
    @Override
    public void say() {
        System.out.println("White say");
    }
}
