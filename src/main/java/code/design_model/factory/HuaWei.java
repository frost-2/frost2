package code.design_model.factory;

/**
 * @author 陈伟平
 * @date 2020-10-23 14:41:12
 */
public class HuaWei implements Phone {
    @Override
    public String getName() {
        return "华为";
    }

    @Override
    public String getPrice() {
        return "6000";
    }
}
