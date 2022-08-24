package com.jun.leetcode;

import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;


public class ProxyTests {
    /**
     * JDK动态代理
     */
    @Test
    public void test1() {
        //参数说明
        // 1. 类加载器ClassLoader:借用类加载器，创建代理类的Class对象
        // 2. 原始类的getClass().getInterfaces()方法 获取方法
        // 3. 额外功能
        final Book book = new Book();
        Object o = Proxy.newProxyInstance(ProxyTests.class.getClassLoader(), book.getClass().getInterfaces(), new InvocationHandler(){
            //1: 实现类
            //2：执行的方法
            //3: 方法参数
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                //方法执行前可操作的内容
                System.out.println("SpringTests.proxy [[begin]]");
                //原始方法运行
                Object invoke = method.invoke(book, objects);
                //方法执行结果
                return invoke;
            }
        });
        Book bk = (Book) o;
        bk.getEat();
    }

    /**
     * CGlib动态代理
     */
    @Test
    public void test2() {
        Book pizza = new Book();
        Enhancer enhancer = new Enhancer();
        //1.类加载器
        enhancer.setClassLoader(this.getClass().getClassLoader());
        //2.父类
        enhancer.setSuperclass(Book.class);//or enhancer.setSuperclass(pizza.getClass());
        //3.额外功能
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("ProxyTests.intercept");
            Object invoke = method.invoke(pizza, objects);
            return invoke;
        });
        //代理创建
        Book cheesePizza = (Book) enhancer.create();
        cheesePizza.getEat();
    }
}
