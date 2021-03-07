package com.rh.bilibili.chapter06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  用CAS实现无锁并发
 */
public class TestAcount {
    public static void main(String[] args) {
        Account account1 = new AccountCas(10000);
        Account.demo(account1);

//        Account account = new AccountUnsafe(10000);
//        Account.demo(account);

    }
}

class AccountCas implements Account{

    private AtomicInteger balance;

    public AccountCas(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
//        while (true){
//            int prev = balance.get();
//            int next = prev - amount;
//            if (balance.compareAndSet(prev,next)){
//                break;
//            }
//        }
        //下面这种写法等价于上面的效果
        balance.getAndAdd(-1 * amount);
    }
}

class AccountUnsafe implements Account{

    private Integer balance;

    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        synchronized (this) {
            return this.balance;
        }
    }

    @Override
    public void withdraw(Integer amount) {
        synchronized (this) {
            this.balance -= amount;
        }
    }
}

interface Account{
    // 获取余额
    Integer getBalance();

    // 取款
    void withdraw(Integer amount);

    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void  demo(Account account){
        List<Thread> ts= new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(()->{
                account.withdraw(10);
            }));
        }

        final long start = System.currentTimeMillis();

        ts.forEach(Thread::start);
        ts.forEach(t ->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        final long end = System.currentTimeMillis();
        System.out.println(account.getBalance()
                + " cost: " + (end-start) + " ms");
    }
}
