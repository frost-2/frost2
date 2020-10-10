package code.basic;

import lombok.ToString;

import java.util.Objects;

/**
 * @author 陈伟平
 * @date 2020-08-28 2:27:00
 */
@ToString
public class Student {

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
