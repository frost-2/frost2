package code.design_model.strategy;

/**
 * 封装角色类
 *
 * @author 陈伟平
 * @date 2020-10-22 19:21:20
 */
public class Context {
    //抽象角色
    private Strategy strategy = null;

    //通过构造方法设置具体的策略,也可用其他方式如：set方法
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    //调用策略方法
    public void printTheS10Result() {
        strategy.TES_VS_DWG();
    }
}
