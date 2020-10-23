package code.design_model.strategy;

/**
 * 高层模块类
 *
 * @author 陈伟平
 * @date 2020-10-22 19:21:38
 */
public class Client1 {
    public static void main(String[] args) {

        //实例化具体抽象类A、B
        ConcreateStrategyA concreateStrategyA = new ConcreateStrategyA();
        ConcreateStrategyB concreateStrategyB = new ConcreateStrategyB();

        //实例化一个封装角色类
        Context1 context1 = new Context1();
        //调用封装角色类
        context1.setStrategy(concreateStrategyA).printTheS10Result();
        context1.setStrategy(concreateStrategyB).printTheS10Result();

    }
}
