package com.rh.design_thread.chapter_five.future;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/8 11:03
 * @description:
 * @modified By:
 */
public class Client {
    public Data request(final String queryStr){
        final FutureData future=new FutureData();
        new Thread(){
            @Override
            public void run() {/*RealData的构造很慢，所以在单独的线程中进行*/
                RealData realData=new RealData(queryStr);
                future.setRealData(realData);
            }
        }.start();
        return future;/*FutrueData会被立即返回*/
    }
}
