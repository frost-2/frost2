package code.basic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陈伟平
 * @date 2020-08-28 2:28:00
 */
@Getter
@Setter
@ToString
public class Person {

    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
