package com.frost2.javabasic.map;

/**
 * @author frost2
 * @date 2023-03-26 15:13
 */
public interface IDefaultMethod {
    abstract void method();

    default void method1() {
        System.out.println("默认方法子类可以不重写");
        method2();
    }

    static void method2() {
        System.out.println("静态方法只能供接口使用，子类无法重写调用");
    }
}

class IDefaultMethodImpl implements IDefaultMethod {
    @Override
    public void method() {
        System.out.println("普通方法子类必须实现");
    }

    public static void main(String[] args) {
        IDefaultMethodImpl method = new IDefaultMethodImpl();
        method.method1();
    }
}
