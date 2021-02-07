package com.rh.design_thread.chapter_six.future;

import java.util.concurrent.CompletableFuture;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/18 9:51
 * @description: CompletableFuture 示例代码
 * @modified By:
 */
public class AskThread implements Runnable{
    CompletableFuture<Integer> re = null;


    public AskThread(CompletableFuture<Integer> re) {
        this.re = re;
    }

    @Override
    public void run() {
        int myRe=0;
        try {
            myRe=re.get()*re.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("myRe:"+myRe);
    }

    public static void main(String[] args) throws InterruptedException {
        final CompletableFuture<Integer> future=new CompletableFuture<>();
        new Thread(new AskThread(future)).start();
        Thread.sleep(1000);
        future.complete(60);
    }
}
