package com.rh.itheima;

import javax.persistence.criteria.CriteriaBuilder;

public class Singleton {
    private static Singleton instance;
    public static Integer x = new Integer(1);
    private Singleton (){}

    public static Singleton getInstance() {
        if (instance == null) {
            System.out.println("==================");
            instance = new Singleton();
        }
        return instance;
    }
}
