package com.rh.chapter_three;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Administrator on 2019/10/4.
 *
 * CyclicBarrier 循环栅栏示例代码
 * 模拟司令命令士兵完成任务的场景
 */
public class CyclicBarrierDemo {
    public static class Soldier implements Runnable{
        private String soldier;
        private final CyclicBarrier cyclic;

        public Soldier(String soldier, CyclicBarrier cyclic) {
            this.soldier = soldier;
            this.cyclic = cyclic;
        }

        @Override
        public void run() {
            try {
                /*等待所有士兵到齐*/
                cyclic.await();
                doWork();
                /*等待所有士兵完成任务*/
                cyclic.await();
//                doWork();
//                cyclic.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWork(){
            try {
                Thread.sleep(Math.abs(new Random().nextInt()%10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier+":任务完成！");
        }
    }

    public static class BarrierRun implements Runnable{
        boolean flag;
        int N;

        public BarrierRun(boolean flag, int n) {
            this.flag = flag;
            N = n;
        }

        @Override
        public void run() {
            if (flag){
                System.out.println("司令：【士兵】"+N+"个，任务完成！");
            }else{
                System.out.println("司令：【士兵】"+N+"个，集合完毕！");
                flag=true;
            }
        }
    }

    public static void main(String[] args) {
        final int N=10;
        Thread[] allSoldier=new Thread[N];
        boolean flag=false;
        CyclicBarrier cyclic=new CyclicBarrier(N,new BarrierRun(flag,N));
        System.out.println("开始集合队伍！");
        for (int i=0;i<N;i++){
            System.out.println("士兵"+i+"报到！");
            allSoldier[i]=new Thread(new Soldier("士兵"+i,cyclic));
            allSoldier[i].start();
        }
    }
}
