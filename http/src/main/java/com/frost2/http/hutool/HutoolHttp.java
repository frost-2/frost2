package com.frost2.http.hutool;

import cn.hutool.http.HttpUtil;

/**
 * @author frost2
 * @date 2022-07-14 15:46
 */
public class HutoolHttp {
    public static void main(String[] args) {
        String body = HttpUtil.createPost("")
                .header("", "")
                .execute().body();
        System.out.println(body);
    }
}
