package code.effective_java3.重写equals方法时遵守通用约定;

/**
 * 单词：
 * Symmetry: n. 对称(性)
 * <p>
 * 本例在类Symmetry中重写Object的equals方法。
 * 但是并不满足对称性：x.equals(y) == y.equals(x)
 *
 * @author 陈伟平
 * @date 2020-8-12 15:09:42
 */
public class Symmetry {

    private final String s;

    public Symmetry(String s) {
        this.s = s;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Symmetry) {
            return s.equalsIgnoreCase(((Symmetry) o).s);
        }
        if (o instanceof String) {
            return s.equalsIgnoreCase((String) o);
        }
        return false;
    }

    public static void main(String[] args) {
        Symmetry cis = new Symmetry("frost2");
        String s = "frost2";

        System.out.println(cis.equals(s));  //true
        System.out.println(s.equals(cis));  //false

        /*
         * String类重写的equals代码如下:
         * 可以看到：当其判断如果接受的对象不是其本身
         * 并且也不是String时，直接返回false，
         * 所以s.equals(cis)为false。
         * */
        /*
            public boolean equals(Object anObject) {
                if (this == anObject) {
                    return true;
                }
                if (anObject instanceof String) {
                    String anotherString = (String)anObject;
                    int n = value.length;
                    if (n == anotherString.value.length) {
                        char v1[] = value;
                        char v2[] = anotherString.value;
                        int i = 0;
                        while (n-- != 0) {
                            if (v1[i] != v2[i])
                                return false;
                            i++;
                        }
                        return true;
                    }
                }
                return false;
            }
        */
    }
}