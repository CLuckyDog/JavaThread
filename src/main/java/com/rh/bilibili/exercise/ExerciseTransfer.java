package com.rh.bilibili.exercise;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j(topic = "c.ExerciseTransfer")
public class ExerciseTransfer {

    public static void main(String[] args) throws InterruptedException {
        Account a=new Account(1000);
        Account b=new Account(1000);
        /**
         * 这个案例和卖票的案例有所区别
         * 这里有两个共享实例变量a,b
         *  如果这时候，你在方法上加sync，等价于sync(this)
         *  但是，这里两个共享的实例变量是两个this，所以，你只锁住this，是锁住当前实例，不能起到作用
         *  所以，要锁住实例的类，sync(Account.class)
         *  因为实例是根据class创建的，所以，对应a,b两个实例来说，他们公用Account.class
         *  所以，用Account.class做他们的锁，正好合适
         *  但是，这样的方式效率不好，因为所有的实例变量都要等这个锁
         */
        Thread t1 = new Thread(()->{
            for (int i=0;i<1000;i++){
                a.transfer(b,randomAmount());
            }
        },"t1");

        Thread t2 = new Thread(()->{
            for (int i=0;i<1000;i++){
                b.transfer(a,randomAmount());
            }
        },"t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        // 查看转账2000次后的总金额
        log.debug("total:{}", (a.getMoney() + b.getMoney()));
    }

    // Random 为线程安全
    static Random random = new Random();

    // 随机 1~100
    public static int randomAmount() {
        return random.nextInt(100) + 1;
    }
    // 账户
    static class Account {
        private int money;

        public Account(int money) {
            this.money = money;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        // 转账
        public void transfer(Account target, int amount) {
            synchronized (Account.class){
                if (this.money >= amount){
                    this.setMoney(this.getMoney()-amount);
                    target.setMoney(target.getMoney()+amount);
                }
            }
        }
    }
}
