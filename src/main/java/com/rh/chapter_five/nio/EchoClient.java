package com.rh.chapter_five.nio;

import java.nio.ByteBuffer;
import java.util.LinkedList;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/13 14:49
 * @description: 用NIO实现Echo Socket客户端
 * @modified By:
 */
public class EchoClient {
    private LinkedList<ByteBuffer> outq;

    public EchoClient() {
        outq=new LinkedList<>();
    }

    public LinkedList<ByteBuffer> getOutputQueue() {
        return outq;
    }

    public  void enqueue(ByteBuffer bb){
        outq.addFirst(bb);
    }

}
