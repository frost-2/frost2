package code.design_model.strategy;

/**
 * 封装角色类:set方式+build模式
 *
 * @author 陈伟平
 * @date 2020-10-22 19:21:20
 */
public class Context1 {
    //抽象角色
    private Strategy strategy = null;

    //通过set方法给Strategy赋值,并返回对象本身
    public Context1 setStrategy(Strategy strategy) {
        this.strategy = strategy;
        return this;
    }

    //调用策略方法
    public Context1 printTheS10Result() {
        strategy.TES_VS_DWG();
        return this;
    }
}
