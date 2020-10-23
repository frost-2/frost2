package code.design_model.factory;

/**
 * 抽象工厂类:手机工厂生产手机
 *
 * @author 陈伟平
 * @date 2020-10-23 14:30:59
 */
public interface AbstractFactory {
    <T extends Phone> T productPhone(Class<T> clazz);
}
