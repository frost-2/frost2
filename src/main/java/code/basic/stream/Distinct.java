package code.basic.stream;

import cn.hutool.core.lang.Console;
import com.google.common.collect.Lists;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 当List中为对象时:
 * 使用Stream流中的distinct进行去重。
 * <p>
 * 注：List中的对象必须重写hashCode()和equals()方法。至于为什么相信网上已经有很多文章介绍了。
 *
 * @author 陈伟平
 * @date 2020-8-7 10:21:02
 */
public class Distinct {

    public static void main(String[] args) {

        Student sss1 = new Student().setName("sss").setAge("181");
        Student sss2 = new Student().setName("sss").setAge("18");
        System.out.println("sss1.equals(sss2) = " + sss1.equals(sss2));

        ArrayList<Student> list = Lists.newArrayList(sss1, sss2);
        Console.log(list);
        List<Student> collect = list.stream().distinct().collect(Collectors.toList());
        Console.log(collect);

    }

}

@ToString
class Student {

    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public String getAge() {
        return age;
    }

    public Student setAge(String age) {
        this.age = age;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
