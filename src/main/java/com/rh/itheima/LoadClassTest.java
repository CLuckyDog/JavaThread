package com.rh.itheima;

public class LoadClassTest {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(LoadClassTest.class.getClassLoader());
        Class<?> aClass = LoadClassTest.class.getClassLoader().loadClass("com.rh.itheima.H");
        System.out.println(aClass.getClassLoader());
    }
}
