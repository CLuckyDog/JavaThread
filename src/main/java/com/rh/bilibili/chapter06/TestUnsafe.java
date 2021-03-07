package com.rh.bilibili.chapter06;   // 解决package

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

import static com.rh.bilibili.utils.Sleeper.sleep;

/**
 * @Title: TestUnsafe
 * @Description: 通过反射来使用Unsafe类
 * @author: pzj
 * @date: 2021/3/7 10:10
 */
@Slf4j(topic = "c.TestUnsafe")
public class TestUnsafe {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        //获取Unsafe类里面的属性
        final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        //因为theUnsafe是private修饰的，所以要设置一下
        theUnsafe.setAccessible(true);
        //获取真正的变量对象,如果这个成员变量是static，则传null即可
        Unsafe unsafe= (Unsafe) theUnsafe.get(null);
        System.out.println(unsafe);

        //1、获取到域的偏移地址
        final long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        final long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher teacher = new Teacher();
        //执行cas操作
        unsafe.compareAndSwapInt(teacher,idOffset,0,1);
        unsafe.compareAndSwapObject(teacher,nameOffset,null,"tomcat");

        //验证
        System.out.println(teacher);
    }
}

@Data
class Teacher{
    volatile int id;
    volatile String name;
}