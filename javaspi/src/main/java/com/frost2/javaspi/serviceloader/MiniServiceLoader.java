package com.frost2.javaspi.serviceloader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于约定:
 * 同一个接口文件中别名不能重复
 *
 * @author frost2
 * @date 2022-07-02 23:46
 */
public class MiniServiceLoader {

    //spi文件前缀
    private static final String prefix = "/META-INF/spi/";
    private static volatile MiniServiceLoader instance = null;
    //缓存所有配置文件中的别名和实现类,保存时key值为接口路径+别名
    private final Map<String, String> aliasMap = new ConcurrentHashMap<>();

    private MiniServiceLoader() {
    }

    //DCL
    public static MiniServiceLoader getInstance() {
        if (instance == null) {
            synchronized (MiniServiceLoader.class) {
                if (instance == null) {
                    instance = new MiniServiceLoader();
                }
            }
        }
        return instance;
    }

    /**
     * 根据别名获取对应实现类
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz, String alias) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String key = clazz.getName() + "_" + alias;
        String className = aliasMap.get(key);
        if (null == className) {
            init(clazz);
        }
        Class<?> aClass = Class.forName(aliasMap.get(key));
        return (T) aClass.newInstance();
    }

    //初始化读取配置接口信息
    private <T> void init(Class<T> clazz) {
        InputStream in = this.getClass().getResourceAsStream(prefix + clazz.getName());
        BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        r.lines().forEachOrdered(line -> parseLine(line, clazz.getName()));
    }

    //按:切分行,将别名/实现类保存到aliasMap
    private void parseLine(String line, String name) {
        String[] split = line.split(":");
        aliasMap.put(name + "_" + split[0], split[1]);
    }


}
