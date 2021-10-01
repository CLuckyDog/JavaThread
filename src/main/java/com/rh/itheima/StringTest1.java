package com.rh.itheima;

/**
 * 字符串常量池的延迟加载示例代码
 * debug模式写，看memory模块的String对象个数进行验证
 */
public class StringTest1 {
    public static void main(String[] args) {
        System.out.println();
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");

        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");
    }
}
