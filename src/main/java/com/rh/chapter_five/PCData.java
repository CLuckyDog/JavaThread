package com.rh.chapter_five;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/6 16:47
 * @description:
 * @modified By:
 */
public class PCData {/*任务相关的数据*/
    private final int intData;/*数据*/

    public PCData(int d) {
        this.intData = d;
    }

    public PCData(String d) {
        this.intData = Integer.valueOf(d);
    }

    public int getData() {
        return intData;
    }

    @Override
    public String toString() {
        return "data:"+intData;
    }
}
