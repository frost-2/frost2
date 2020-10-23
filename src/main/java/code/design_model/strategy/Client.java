package code.design_model.strategy;

/**
 * 高层模块类
 *
 * @author 陈伟平
 * @date 2020-10-22 19:21:38
 */
public class Client {
    public static void main(String[] args) {
        //执行具体抽象类A
        ConcreateStrategyA concreateStrategyA = new ConcreateStrategyA();
        Context contextA = new Context(concreateStrategyA);
        contextA.printTheS10Result();

        //执行具体抽象类B
        ConcreateStrategyB concreateStrategyB = new ConcreateStrategyB();
        Context contextB = new Context(concreateStrategyB);
        contextB.printTheS10Result();

    }
}
