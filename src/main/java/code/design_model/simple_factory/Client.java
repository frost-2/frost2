package code.design_model.simple_factory;

import code.design_model.factory.*;

/**
 * 简单工厂也叫静态工厂,它与工厂模式的区别在于：
 * 简单工厂没有抽象工厂接口(AbstractFactory)，工厂类中的工厂方法(productPhone)为静态的，可通过类名直接调用。
 *
 * @author 陈伟平
 * @date 2020-10-23 15:06:57
 */
public class Client {
    public static void main(String[] args) {
        Phone huaWei = PhoneFactory.productPhone(HuaWei.class);
        Phone iPhone = PhoneFactory.productPhone(IPhone.class);

        System.out.println(huaWei.getName() + " " + huaWei.getPrice() + "块");
        System.out.println(iPhone.getName() + " " + iPhone.getPrice() + "块");
    }
}
