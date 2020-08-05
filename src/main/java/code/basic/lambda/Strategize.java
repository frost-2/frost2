package code.basic.lambda;

/**
 * 该例来源于《On Java8》第十三章
 *
 * @author 陈伟平
 * @date 2020-08-05-下午 2:58
 */
interface Strategy {
    String approach(String msg);
}

class Soft implements Strategy {
    public String approach(String msg) {
        return msg.toLowerCase() + "?";
    }
}

class Unrelated {
    static String twice(String msg) {
        return msg + " " + msg;
    }
}

public class Strategize {
    private Strategy strategy;
    private String msg;

    private Strategize(String msg) {
        strategy = new Soft(); // [1]
        this.msg = msg;
    }

    private void communicate() {
        System.out.println(strategy.approach(msg));
    }

    private void changeStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public static void main(String[] args) {
        Strategy[] strategies = {
                new Strategy() { // [2]
                    public String approach(String msg) {
                        return msg.toUpperCase() + "!";
                    }
                },
                msg -> msg.substring(0, 5), // [3]
                Unrelated::twice // [4]
        };
        Strategize s = new Strategize("Hello there");
        s.communicate();
        for (Strategy newStrategy : strategies) {
            s.changeStrategy(newStrategy); // [5]
            s.communicate(); // [6]
        }
    }
}

