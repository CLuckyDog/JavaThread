package com.rh.chapter_five.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/14 14:52
 * @description:
 * @modified By:
 */
public class NioEchoClient {
    private Selector selector;
    public void init(String ip,int port) throws IOException {
        /*创建一个channel*/
        SocketChannel channel=SocketChannel.open();
        /*设置channel为非阻塞模式*/
        channel.configureBlocking(false);
        /*创建一个selector实例*/
        this.selector= SelectorProvider.provider().openSelector();
        /*将channel绑定到socket上*/
        channel.connect(new InetSocketAddress(ip,port));
        /*将channel和selector进行绑定，并注册感兴趣的时间作为连接(OP_CONNECT)*/
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void working() throws IOException {
        while (true){
            if (!selector.isOpen()){
                break;
            }
            selector.select();
            Iterator<SelectionKey> ite=this.selector.selectedKeys().iterator();
            while(ite.hasNext()){
                SelectionKey key=ite.next();
                ite.remove();
                if (key.isConnectable()){
                    connet(key);
                }else if (key.isReadable()){
                    read(key);
                }
            }
        }
    }

    public void connet(SelectionKey key) throws IOException {
        SocketChannel channel= (SocketChannel) key.channel();
        if (channel.isConnectionPending()){
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        channel.write(ByteBuffer.wrap(new String("hello server!\r\n").getBytes()));
        channel.register(this.selector,SelectionKey.OP_READ);
    }

    public void read(SelectionKey key) throws IOException {
        SocketChannel channel= (SocketChannel) key.channel();
        ByteBuffer buffer=ByteBuffer.allocate(1000);
        channel.read(buffer);
        byte[] data=buffer.array();
        String msg=new String(data).trim();
        System.out.println("客户端收到信息："+msg);
        channel.close();
        key.selector().close();
    }
    public static void main(String[] args) throws IOException {
        NioEchoClient client=new NioEchoClient();
        client.init("localhost",8000);
    }
}
