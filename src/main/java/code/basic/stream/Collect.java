package code.basic.stream;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * @author 陈伟平
 * @date 2020-08-08-下午 2:18
 */
public class Collect {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("s");
        list.add("o");
        list.add("s");

        //字符串拼接
        String concat1 = list.stream().collect(Collectors.joining());
        String concat2 = list.stream().collect(Collectors.joining("-"));
        String concat3 = list.stream().collect(Collectors.joining("-", "(", ")"));

        ArrayList<Person> personList = new ArrayList<>();
        personList.add(new Person("c", "wp"));
        personList.add(new Person("s", "ss"));
        personList.add(new Person("s", "bb"));

        //根据Person的firstName进行分组
        Map<String, List<Person>> map = personList.stream().collect(Collectors.groupingBy(Person::getFirstName));
        System.out.println(map);

        //根据firstName是否为"c",将list进行分区
        Map<Boolean, List<Person>> map1 = personList.stream()
                .collect(Collectors.partitioningBy(person -> person.getFirstName().equals("c")));
        System.out.println(map1);

        //根据firstName是否为"c",将list进行分区,并将两个区根据firstName在进行分组
        Map<Boolean, Map<String, List<Person>>> map2 = personList.stream()
                .collect(Collectors.partitioningBy(person -> person.getFirstName().equals("c"), Collectors.groupingBy(Person::getFirstName)));
        System.out.println(map2);
    }
}
