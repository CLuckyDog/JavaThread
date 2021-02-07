package com.rh.design_thread.chapter_five.jdkfuture;

import java.util.concurrent.Callable;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/8 14:57
 * @description:
 * @modified By:
 */
public class RealData implements Callable<String> {
    private String para;

    public RealData(String para) {
        this.para = para;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb=new StringBuffer();
        for (int i=0;i<10;i++){
            sb.append(para);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
