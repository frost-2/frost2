package code.design_model.factory;

/**
 * @author 陈伟平
 * @date 2020-10-23 14:34:55
 */
public class PhoneFactory implements AbstractFactory {

    @Override
    public <T extends Phone> T productPhone(Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("生产手机失败");
        }
        return t;
    }
}
