package com.rh.chapter_five.future;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/8 11:02
 * @description:
 * @modified By:
 */
public class RealData implements Data {

    protected final String result;

    public RealData(String para) {
        /*RealData 的构造可能很慢，需要用户等待很久，这里使用sleep模拟*/
        StringBuffer sb=new StringBuffer();
        for (int i=0;i<10;i++){
            sb.append(para);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.result =sb.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}
