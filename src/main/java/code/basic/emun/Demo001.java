package code.basic.emun;

/**
 * @author 陈伟平
 * @date 2020-9-11 15:42:32
 */
public class Demo001 {

    //定义一个枚举属性:披萨状态
    public enum PizzaStatus {
        ORDERED(),      //已订购
        READY(),        //准备ok
        DELIVERED();    //已交付
    }

    //定义一个枚举属性:面条状态
    public enum NoodleStatus {
        ORDERED(),      //已订购
        READY(),        //准备ok
        DELIVERED();    //已交付
    }

    private PizzaStatus pizzaStatus;

    private NoodleStatus noodleStatus;

    public PizzaStatus getPizzaStatus() {
        return pizzaStatus;
    }

    public void setPizzaStatus(PizzaStatus pizzaStatus) {
        this.pizzaStatus = pizzaStatus;
    }

    public NoodleStatus getNoodleStatus() {
        return noodleStatus;
    }

    public void setNoodleStatus(NoodleStatus noodleStatus) {
        this.noodleStatus = noodleStatus;
    }

    /**
     * 知识点一: 使用 == 比较枚举类型
     * (1)由于枚举类型确保JVM中仅存在一个常量实例，因此我们可以安全地使用 "==" 运算符比较两个变量，此外，"==" 运算符可提供编译时和运行时的安全性。
     */
    public boolean isDeliverable() {
        /*
        //使用==判断时,会在编译期判断是否为相同的类型，但是equals编译期就不会
        if (getPizzaStatus() == getNoodleStatus()) {
            return true;
        }
        */
        if (getPizzaStatus().equals(getNoodleStatus())) {
            return true;
        }
        return false;
    }

    //知识点二:switch语句使用枚举
    public int getDeliveryTimeInDays() {
        switch (pizzaStatus) {
            case ORDERED:
                return 5;
            case READY:
                return 2;
            case DELIVERED:
                return 0;
        }
        return 0;
    }

    public static void main(String[] args) {
        Demo001 demo001 = new Demo001();
        demo001.setPizzaStatus(PizzaStatus.ORDERED);
        demo001.setNoodleStatus(NoodleStatus.ORDERED);
        System.out.println(demo001.isDeliverable());
        System.out.println(demo001.getDeliveryTimeInDays());
    }

}
