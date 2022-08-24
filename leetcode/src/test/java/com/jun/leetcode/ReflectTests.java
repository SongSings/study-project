package com.jun.leetcode;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTests {

    /**
     * 获取字节码的方式测试
     */
    @Test
    public void test1() throws ClassNotFoundException {
        //1.通过类路径获取对象，通常用于文件的配置
        Class cla1 = Class.forName("com.datajpa.jpademo.domain.Book");
        System.out.println("cla1 = " + cla1);
        //2.通过类获取
        Class cla2 = Book.class;
        System.out.println("cla2 = " + cla2);
        //3.通过对象获取
        Book book = new Book();
        Class cla3 = book.getClass();
        System.out.println("cla3 = " + cla3);
    }

    /**
     * Class对象的功能
     * 获取成员变量
     */
    @Test
    public void test2() throws NoSuchFieldException, IllegalAccessException {
        Book book = new Book();
        //1 getField：获取public修饰的成员属性
        Field name = Book.class.getField("name");
        Object bookObj = name.get(book);
        System.out.println("bookObj = " + bookObj);
        name.set(book,"平凡的世界");
        System.out.println("book = " + book);


        //2.getFields：获取所有public修饰的成员变量
        Field[] fields = Book.class.getFields();
        for (Field field : fields) {
            System.out.println("field = " + field);
        }

        //3.getDeclaredField:：获取成员变量
        Field id = Book.class.getDeclaredField("id");
        //忽略访问权限修饰符的安全检查
        id.setAccessible(true);//暴力反射
        bookObj = id.get(book);
        System.out.println("bookObj = " + bookObj);
        id.set(book,Long.valueOf(1));
        System.out.println("book = " + book);

        //4.getDeclaredFields：获取所有成员变量
        Field[] declaredFields = Book.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println("declaredField = " + declaredField);
        }
        //5.获取类名
        System.out.println("book.getClass().getName() = " + book.getClass().getName());
    }

    /**
     * 构造方法
     */
    @Test
    public void test3() throws Exception {
        Book book = new Book();
        //getConstructor
        Constructor constructor = book.getClass().getConstructor(Long.class,String.class,String.class,String.class);
        //创建对象

        //1.newInstance
        Book book1 = Book.class.newInstance();
        System.out.println("book1 = " + book1);

        Object newInstance = constructor.newInstance((long) 1, "活着","余华","...");
        System.out.println("newInstance = " + newInstance);
    }

    /**
     * 获取成员方法
     * @throws Exception
     */
    @Test
    public void test4() throws Exception {
        Book book = new Book();
        Method eat = Book.class.getMethod("eat");
        eat.invoke(book);

        Method eat2 = Book.class.getMethod("eat",String.class);
        eat2.invoke(book,"鱼");

        Method[] methods = Book.class.getMethods();
        for (Method method : methods) {
            System.out.println("method = " + method);
        }

        Method talk = Book.class.getDeclaredMethod("talk", String.class);
        talk.setAccessible(true);
        talk.invoke(book, " about");
    }
}
class Book{
    private long eat;
    private String name;

    public long getEat() {
        System.out.println("eat = " + eat);
        return eat;
    }
}
