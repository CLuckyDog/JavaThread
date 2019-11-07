package com.rh.chapter_five.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/7 15:26
 * @description:
 * @modified By:
 */
public class PCDataFactory implements EventFactory<PCData> {

    @Override
    public PCData newInstance() {
        return new PCData();
    }
}
