package code.design_model.strategy;

/**
 * 封装角色类:简单工厂模式+策略模式实现
 *
 * @author 陈伟平
 * @date 2020-10-22 19:21:20
 */
public class Context2 {
    //抽象角色
    private Strategy strategy = null;

    //将实例化具体策略的过程从高层模块转移到Context类中。简单工厂的应用
    public Context2(String strategyType) {
        switch (strategyType) {
            case "strategyA":
                this.strategy = new ConcreateStrategyA();
                break;
            case "strategyB":
                this.strategy = new ConcreateStrategyB();
                break;
        }
    }

    //调用策略方法
    public void printTheS10Result() {
        strategy.TES_VS_DWG();
    }
}
