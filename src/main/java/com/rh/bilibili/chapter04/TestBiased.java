package com.rh.bilibili.chapter04;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

@Slf4j(topic = "c.TestBiased")
public class TestBiased {
    public static void main(String[] args) {
        Dog d=new Dog();
        System.out.println(ClassLayout.parseInstance(d).toPrintable(d));

        synchronized (d){
            System.out.println(ClassLayout.parseInstance(d).toPrintable(d));
        }

        System.out.println(ClassLayout.parseInstance(d).toPrintable(d));
    }
}

class Dog{

}
