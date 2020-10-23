package code.design_model.factory;

/**
 * @author 陈伟平
 * @date 2020-10-23 14:41:48
 */
public class IPhone implements Phone{
    @Override
    public String getName() {
        return "苹果";
    }

    @Override
    public String getPrice() {
        return "5000";
    }
}
