package code.basic;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author 陈伟平
 * @date 2020-08-28 2:28:00
 */
@Getter
@Setter
public class Book {

    private String name;
    private int price;

    public Book(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return price == book.price &&
                Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
