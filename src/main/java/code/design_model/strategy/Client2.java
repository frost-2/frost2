package code.design_model.strategy;

/**
 * @author 陈伟平
 * @date 2020-10-22-下午 9:41
 */
public class Client2 {
    public static void main(String[] args) {
        Context2 strategyA = new Context2("strategyA");
        strategyA.printTheS10Result();

        Context2 strategyB = new Context2("strategyB");
        strategyB.printTheS10Result();
    }
}
