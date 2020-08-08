package code.basic.stream;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * filter：对于Stream中包含的元素使用给定的过滤函数进行过滤操作，新生成的Stream只包含符合条件的元素
 * 入参:Predicate -> return true的元素将会被保留,return false的元素将会被过滤。
 *
 * @author 陈伟平
 * @date 2020-8-8 09:26:28
 */
public class Filter {

    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("aaa", "111"));
        personList.add(new Person("aaa", "222"));
        personList.add(new Person("ccc", "111"));

        filterPerson(personList, "aaa", "111");
    }

    /**
     * 从list中元素筛选出与firstName/lastName相同的元素
     *
     * @param personList 目标list
     * @param firstName  姓
     * @param lastName   名
     * @return 筛选后的list
     */
    private static List<Person> filterPerson(List<Person> personList, String firstName, String lastName) {
        //过滤掉list中firstName与参数firstName不同的元素
        List<Person> firstNameList = personList.stream().filter(selectByKey(Person::getFirstName, firstName)).collect(Collectors.toList());
        System.out.println("firstNameList = " + firstNameList);

        //过滤掉list中lastName与参数lastName不同的元素
        List<Person> lastNameList = personList.stream().filter(selectByKey(Person::getLastName, lastName)).collect(Collectors.toList());
        System.out.println("lastNameList = " + lastNameList);
        return null;
    }

    private static <T> Predicate<T> selectByKey(Function<? super T, ?> function, String name) {
        return t -> name == function.apply(t);
    }

}

@Getter
@Setter
@ToString
class Person {

    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}