package com.frost2.myspi;

import com.frost2.myspi.entity.ImplInfo;
import com.frost2.myspi.spi.IPerson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口文件使用|分割:
 * 0:执行顺序
 * 1:实现类简称
 * 2:实现类全路径
 * <p>
 * 注：简称不予许重复
 *
 * @author frost2
 * @date 2022-07-02 23:46
 */
public class ServiceLoaderPlus {

    private static final String prefix = "/META-INF/spi/";

    /**
     * @param clazz spi接口
     * @warn Raw use of parameterized class 'Class' :要求Class使用泛型
     */
    public <T> List<T> load(Class<T> clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ArrayList<T> result = new ArrayList<>();
        BufferedReader r = getBr(clazz);
        List<ImplInfo> list = r.lines().map(this::parseLine).sorted(new Comparator<ImplInfo>() {
            @Override
            public int compare(ImplInfo o1, ImplInfo o2) {
                //TODO:按ImplInfo.order进行排序
                return 0;
            }
        }).collect(Collectors.toList());

        for (ImplInfo implInfo : list) {
            Class<?> aClass = Class.forName(implInfo.getImplPath());
            T t = (T) aClass.newInstance();
            result.add(t);
        }
        return result;
    }

    public <T> T get(Class<T> clazz, String alias) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        BufferedReader r = getBr(clazz);
        List<ImplInfo> list = r.lines()
                .map(this::parseLine)
                .filter(implInfo -> implInfo.getAlias().equalsIgnoreCase(alias))
                .collect(Collectors.toList());
        ImplInfo implInfo = list.get(0);
        Class<?> aClass = Class.forName(implInfo.getImplPath());
        return (T) aClass.newInstance();
    }

    //TODO 按|切分行
    private ImplInfo parseLine(String line) {
        ImplInfo implInfo = new ImplInfo();
        String[] split = line.split("\\|");
        implInfo.setOrder(Integer.parseInt(split[0]));
        implInfo.setAlias(split[1]);
        implInfo.setImplPath(split[2]);
        return implInfo;
    }

    private BufferedReader getBr(Class clazz) {
        InputStream in = this.getClass().getResourceAsStream(prefix + clazz.getName());
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        ServiceLoaderPlus serviceLoaderPlus = new ServiceLoaderPlus();
        IPerson yellow = serviceLoaderPlus.get(IPerson.class, "yellow");
        yellow.think();
        List<IPerson> load = serviceLoaderPlus.load(IPerson.class);
        for (IPerson iPerson : load) {
            iPerson.think();
        }
    }
}
