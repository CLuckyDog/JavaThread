package com.rh.design_thread.chapter_five.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/7 15:28
 * @description:
 * @modified By:
 */
public class Producer {
    /*
    * RingBuffer是一个环形缓冲区，它有一个重要的放啊pushData，将产生的数据推入缓冲区
    * 方法pushData接收一个ByteBuffer对象，在ByteBuffer中可以用来包装任何数据类型
    * 这里用来存储long整数，pushData的功能就是将传入的ByteBuffer中的数据提取出来，并装载到环形缓冲区中
    * */
    private final RingBuffer<PCData> ringBuffer;

    public Producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void pushData(ByteBuffer bb){

        long sequence=ringBuffer.next();

        try {
            PCData event=ringBuffer.get(sequence);
            event.setValue(bb.getLong(0));
        } finally {
            ringBuffer.publish(sequence);
        }

    }
}
