package code.basic.stream;

import code.basic.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 根据list中对象的某一个属性进行去重
 *
 * @author 陈伟平
 * @date 2020-8-7 13:47:56
 */
public class DistinctByKey {
    public static void main(String[] args) {
        List<Book> list = new ArrayList<>();
        {
            list.add(new Book("Core Java", 200));
            list.add(new Book("Core Java", 300));
            list.add(new Book("Learning Freemarker", 150));
            list.add(new Book("Spring MVC", 200));
            list.add(new Book("Hibernate", 300));
        }

        //根据名称去重:让filter中的Book都执行distinctByKey的逻辑
        list.stream().filter(distinctByKey(Book::getName))
                .forEach(b -> System.out.println(b.getName() + "," + b.getPrice()));

        System.out.println("--------------分割线------------");

        //根据价格去重
        list.stream().filter(distinctByKey(Book::getPrice))
                .forEach(b -> System.out.println(b.getName() + "," + b.getPrice()));

        System.out.println("--------------分割线------------");

        //分解上面的过程
        //1.生成函数式接口Predicate
        Predicate<Book> bookPredicate = distinctByKey(new Function<Book, Object>() {
            @Override
            public Object apply(Book book) {
                return book.getName();
            }
        });
        //2.生成流，让filter中的Book对象都执行bookPredicate中的逻辑,进行判定是否保存
        list.stream().filter(bookPredicate)
                .forEach(b -> System.out.println(b.getName() + "," + b.getPrice()));

    }

    /**
     * List中保存对象时：
     * 通过conCurrentHashMap实现根据属性去重
     *
     * @param function 流中元素需要执行的函数
     * @param <T>      流中元素
     * @return Predicate
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> function) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(function.apply(t), Boolean.TRUE) == null;
    }

}

class test {
    public static void main(String[] args) {
        Function<Book, Integer> f = Book::getPrice;
        Object java = f.apply(new Book("java", 100));
        System.out.println(java);
    }
}

