package code.basic.creatObject;

import cn.hutool.core.clone.CloneRuntimeException;
import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


/**
 * 浅克隆：
 * (1)对于基本类型的变量是值克隆，新的对象会重新创建这些变量，并保留其原来的值，这是真的克隆，不会和原来的对象造成冲突。
 * (2)对于引用类型的变量，其克隆的过程只是克隆引用，并没有克隆该引用所指向的对象，这时通过引用修改拷贝出来的对象时，发现原来的对象也跟着改变了，这是浅克隆。
 * 深克隆：将基本类型和引用类型都克隆一份出来，修改克隆出来的对象不会影响与原来的对象。
 *
 * @author 陈伟平
 * @date 2020-8-27 19:02:51
 */
public class CloneDemo001 {

    public static void main(String[] args) throws CloneNotSupportedException {
        String[] book = new String[]{"think in java", "think in c++"};

        //浅克隆:实现jdk提供的Cloneable
        CloneObj cloneObj = new CloneObj(100, book);
        CloneObj clone = (CloneObj) cloneObj.clone();
        clone.setPrice(200);
        String[] book1 = clone.getBook();
        book1[0] = "on java8";
        System.out.println("cloneObj = " + cloneObj);
        System.out.println("clone = " + clone);
        System.out.println("----------");

        //浅克隆:实现Hutool提供的Cloneable
        CloneHutool cloneHutool = new CloneHutool(50, book);
        CloneHutool cloneHutoolCopy = cloneHutool.clone();
        cloneHutoolCopy.setPrice(100);
        String[] book2 = cloneHutoolCopy.getBook();
        book2[1] = "on java9";
        System.out.println("cloneHutool = " + cloneHutool);
        System.out.println("cloneHutoolCopy = " + cloneHutoolCopy);
        System.out.println("----------");

        //深克隆:使用hutool实现深度克隆
        CloneObj cloneByStream = ObjectUtil.cloneByStream(cloneObj);
        String[] book3 = cloneByStream.getBook();
        book3[0] = "on java11";
        System.out.println("cloneObj999 = " + cloneObj);
        System.out.println("cloneByStream999 = " + cloneByStream);

        //深克隆:也可通过重写clone放实现深克隆，例如CloneObj对象中注释掉的clone()方法
    }
}

@Getter
@Setter
@ToString
class CloneObj implements Cloneable, Serializable {
    private int price;
    private String[] book;

    public CloneObj(int price, String[] book) {
        this.price = price;
        this.book = book;
    }

    //浅克隆实现
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    //深克隆实现:将引用类型的变量同时克隆一份,然后复制给克隆得到的对象
//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        CloneObj cloneObj = (CloneObj) super.clone();
//        cloneObj.book = book.clone();
//        return cloneObj;
//    }
}

@Getter
@Setter
@ToString
class CloneHutool implements cn.hutool.core.clone.Cloneable<CloneHutool> {
    private int price;
    private String[] book;

    public CloneHutool(int price, String[] book) {
        this.price = price;
        this.book = book;
    }

    @Override
    public CloneHutool clone() {
        try {
            return (CloneHutool) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneRuntimeException(e);
        }
    }
}