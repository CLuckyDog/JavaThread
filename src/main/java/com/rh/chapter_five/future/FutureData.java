package com.rh.chapter_five.future;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/8 11:02
 * @description:
 * @modified By:
 */
public class FutureData implements Data {
    protected RealData realData=null;/*FutureData 是 RealData 的包装*/
    protected boolean isReady=false;
    public synchronized void setRealData(RealData realdata){
        if (isReady){
            return;
        }
        this.realData=realdata;
        isReady=true;
        notify();/*RealData已经被注入，通知getResult（）*/
    }

    @Override
    public String getResult() {/*会等待RealData构造完成*/
        while (!isReady){
            try {
                wait();/*一直等待,直到RealData被注入*/
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.result;/*由RealData实现*/
    }
}
