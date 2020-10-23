package code.design_model.factory;

/**
 * @author 陈伟平
 * @date 2020-10-23 15:06:57
 */
public class Client {
    public static void main(String[] args) {
        AbstractFactory phoneFactory = new PhoneFactory();
        Phone huaWei = phoneFactory.productPhone(HuaWei.class);
        Phone iPhone = phoneFactory.productPhone(IPhone.class);

        System.out.println(huaWei.getName() + " " + huaWei.getPrice() + "块");
        System.out.println(iPhone.getName() + " " + iPhone.getPrice() + "块");
    }
}
