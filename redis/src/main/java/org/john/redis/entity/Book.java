package org.john.redis.entity;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Book implements Serializable {

    private String title;
    private String author;

    // 每次调用create方法时，会自动产生一个Book的对象，对象模拟数据是使用javafaker来模拟生成的
    public static Book create() {
        com.github.javafaker.Book fakerBook = Faker.instance().book();
        Book book = new Book();
        book.setTitle(fakerBook.title());
        book.setAuthor(fakerBook.author());
        return book;
    }
}