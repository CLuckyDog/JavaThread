package com.rh.bilibili.chapter06;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TestAtomicRefence {
    public static void main(String[] args) {
        DecimalAccount account = new DecimalAccountCas(new BigDecimal("10000"));
        DecimalAccount.demo(account);
    }
}

class DecimalAccountCas implements DecimalAccount{
//    private BigDecimal balance;
    private AtomicReference<BigDecimal> balance;

    public DecimalAccountCas(BigDecimal balance) {
//        this.balance = balance;
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while (true){
            final BigDecimal prev = balance.get();
            final BigDecimal next = prev.subtract(amount);
            if (balance.compareAndSet(prev,next)){
                break;
            }
        }
    }
}

interface DecimalAccount{
    BigDecimal getBalance();
    void withdraw(BigDecimal amount);

    static void  demo(DecimalAccount account){
        List<Thread> ts= new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(()->{
                account.withdraw(BigDecimal.TEN);
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
