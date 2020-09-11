package code.basic.generics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过反射可以向list添加任意类型的元素
 *
 * @author 陈伟平
 * @date 2020-8-31 09:38:14
 */
public class Demo001 {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List<Integer> list = new ArrayList<>();
        list.add(12);
        //这里直接添加会报错
//        list.add("a");
        //但是通过反射添加，是可以的
        Class<? extends List> clazz = list.getClass();
        Method add = clazz.getDeclaredMethod("add", Object.class);
        add.invoke(list, "kl");
        add.invoke(list, new Demo001());
        System.out.println(list);
    }

}
