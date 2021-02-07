package com.rh.design_thread.chapter_five.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/7 14:58
 * @description:
 * @modified By:
 */
public class Consumer implements WorkHandler<PCData> {

    @Override
    public void onEvent(PCData event) throws Exception {
        System.out.println(Thread.currentThread().getId()+":Event:--"+event.getValue()*event.getValue()+"--");
    }

}
