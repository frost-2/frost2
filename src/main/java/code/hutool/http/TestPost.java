package code.hutool.http;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpUtil;

/**
 * @author 陈伟平
 * @date 2020-04-01 11:17:00
 */
public class TestPost {

    public static void post(){
        String url = "http://localhost:9527/admin/login";
        String requestBody = "{\"phone\":\"13000000000\", \"password\": \"123456\"}";

        String responseBody = HttpUtil.createPost(url)      //创建一个post请求
                .body(requestBody)                          //添加请求体
                .header("dz-usr","1")          //添加请求头
                .execute()                                  //执行请求
                .body();                                    //获取响应体内容

        Console.log(responseBody);
    }

    public static void main(String[] args) {
        post();
    }

}
