package com.frost2.javaspi.serviceloader;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * 基于约定:
 * 同一个接口文件中别名不能重复
 *
 * @author frost2
 * @date 2022-07-02 23:46
 */
public class MiniServiceLoader {

    private static volatile MiniServiceLoader instance = null;
    //缓存所有配置文件中的别名和实现类,保存时key值为接口路径+别名
    private final ConcurrentHashMap<String, String> aliasMap = new ConcurrentHashMap<>();

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
            //方式一
            ObtainInterFaceProp(clazz);
        }
        Class<?> aClass = Class.forName(aliasMap.get(key));
        return (T) aClass.newInstance();
    }

    /**
     * 通过classloader读取文件信息是,路径不能以/开头
     */
    private <T> void ObtainInterFaceProp(Class<T> clazz) {
        String PREFIX = "/META-INF/extension/";
        InputStream in = this.getClass().getResourceAsStream(PREFIX + clazz.getName());
        BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        r.lines().forEachOrdered(line -> parseLine(line, clazz.getName()));
    }

    /**
     * SpringFactoriesLoader读取文件采用如下方式
     * 注:
     * (1)通过classloader读取文件信息是,路径不能以/开头
     * (2)这种方式可以支持多个value,使用逗号分隔即可
     */
    private <T> Map<String, List<String>> ObtainInterFaceProp1(Class<T> clazz) {

        String PREFIX = "META-INF/extension/";
        Map<String, List<String>> result = new HashMap<>();
        try {
            Enumeration<URL> urls = this.getClass().getClassLoader().getResources(PREFIX + clazz.getName());

            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                UrlResource resource = new UrlResource(url);
                //根据:=和空格切分行生成Properties
                Properties properties = PropertiesLoaderUtils.loadProperties(resource);
                for (Map.Entry<?, ?> entry : properties.entrySet()) {
                    String factoryTypeName = ((String) entry.getKey()).trim();
                    String[] factoryImplementationNames =
                            StringUtils.commaDelimitedListToStringArray((String) entry.getValue());
                    for (String factoryImplementationName : factoryImplementationNames) {
                        result.computeIfAbsent(factoryTypeName, key -> new ArrayList<>())
                                .add(factoryImplementationName.trim());
                    }
                }
            }

        } catch (IOException ignored) {

        }
        return result;
    }


    //按:切分行,将别名/实现类保存到aliasMap
    private void parseLine(String line, String name) {
        String[] split = line.split(":");
        aliasMap.put(name + "_" + split[0], split[1]);
    }


}
