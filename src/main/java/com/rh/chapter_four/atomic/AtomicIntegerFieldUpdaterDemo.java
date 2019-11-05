package com.rh.chapter_four.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/1 16:02
 * @description: AtomicIntegerFieldUpdater线程安全性的示例代码
 *              该类使用时注意点：
 *                  1.Updater只能修改它可见范围内的变量，因为Updater使用反射得到这个变量，
 *                  如果变量不可见就会出错，比如score声明成private就是不行的。
 *                  2.为了确保变量被正确的读取，它必须是volatile类型，如果我们没有做这个声明，
 *                  那么简单的声明下就可以，不会影响原有功能。
 *                  3.由于CAS操作会通过对象实例中的偏移量直接进行赋值，因此，它不支持static字段。
 * @modified By:
 */
public class AtomicIntegerFieldUpdaterDemo {
    public static class Candidate{
        int id;
        volatile int score;
    }

    public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater
            =AtomicIntegerFieldUpdater.newUpdater(Candidate.class,"score");
    /*检查updater是否工作正确*/
    public static AtomicInteger allScore=new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        final Candidate stu=new Candidate();
        Thread[] t=new Thread[10000];
        for (int i=0;i<10000;i++){
            t[i] = new Thread(){
                @Override
                public void run() {
                    if (Math.random()>0.4){/*这个条件的存在，让stu.score小于10000，因为，不是每次循环都进来*/
                        int x=scoreUpdater.incrementAndGet(stu);/*对stu.score变量新增1并获取*/
//                        System.out.println("x---------"+x);
                        int y=allScore.incrementAndGet();/*对allScore变量新增1并获取*/
//                        System.out.println("y---------"+y);
                    }
                }
            };
            t[i].start();
        }
        for (int i=0;i<10000;i++){
            t[i].join();
        }
        /*
        * 多次执行发现
        * 下面两个输出一直都是一样的结果
        * 说明AtomicIntegerFieldUpdater实现了对普通字段的线程安全保证
        * 因为AtomicInteger本身是线程安全的
        * */
        System.out.println("score="+stu.score);
        System.out.println("allScore="+allScore);
    }
}
