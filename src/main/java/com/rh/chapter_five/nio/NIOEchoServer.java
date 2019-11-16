package com.rh.chapter_five.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/13 14:26
 * @description: 用NIO实现Echo Socket服务器
 * @modified By:
 */
public class NIOEchoServer {
    private Selector selector;
    private ExecutorService tp= Executors.newCachedThreadPool();
    public static Map<Socket,Long> time_stat=new HashMap<>(10240);

    class HandleMsg implements Runnable{
        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey sk, ByteBuffer bb) {
            this.sk = sk;
            this.bb = bb;
        }

        @Override
        public void run() {
            EchoClient echoClient= (EchoClient) sk.attachment();
            echoClient.enqueue(bb);
            sk.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
            selector.wakeup();/*强制selector立即返回*/
        }
    }

    private void startServer() throws IOException {
        selector= SelectorProvider.provider().openSelector();/*通过工厂方法获得一个selector对象实例*/
        ServerSocketChannel ssc=ServerSocketChannel.open();/*获得表示服务端的SocketChannel实例*/
        /*
        * 将SocketChannel设置为非阻塞模式
        * 在这种模式下，我们才可以想Channel注册感兴趣的时间，并且在数据准备好时，得到必要的通知
        * */
        ssc.configureBlocking(false);
        /*将这个Channel绑定在8000端口*/
        InetSocketAddress isa=new InetSocketAddress(InetAddress.getLocalHost(),8000);
//        InetSocketAddress isa=new InetSocketAddress(8000);
        ssc.socket().bind(isa);
        /*
        * 将这个ServerSocketChannel绑定到Selector上，并注册它感兴趣的时间为Accept
        * 这样，Selector就能为这个Channel服务了。
        * 当Selector发现ServerSocketChannel有新的客户端连接时，就会通知ServerSocketChannel进行处理。
        * 方法register()的返回值是一个SelectionKey，SelectionKey表示一对Selector和Channel的关系。
        * 当Channel注册到Selector上时，就相当于确立了两者的服务关系，那么SelectionKey就是契约
        * 当Selector或者Channel被关闭时，他们对应的SelectionKey就会失效。
        * */
        SelectionKey acceptKey=ssc.register(selector,SelectionKey.OP_ACCEPT);
        for (;;){/*等待分发网络消息*/
            /*
            * select()方法是一个阻塞方法
            * 如果当前没有任何数据准备好，他就会等待。
            * 一旦有数据可读，他就返回
            * 返回值是已经准备就绪的SelectionKey数量，这里简单的将其忽略
            * */
            selector.select();
            /*
            * 获取哪些准备好的SelectionKey
            * 因为Selector同时为多个Channel服务，因此已经准备就绪的Channel就有可能是多个
            * 所以，这里得到的自然是一个集合
            * 得到这个就绪集合后，剩下的就是比那里这个集合，挨个处理所有的Channel数据
            * */
            Set readyKeys=selector.selectedKeys();
            Iterator i=readyKeys.iterator();
            long e=0;

            while (i.hasNext()){
                /*获得一个集合内的SelectionKey实例*/
                SelectionKey sk= (SelectionKey) i.next();
                /*将这个元素移除，这步很重要，否则就会重复处理相同的SelectionKey*/
                i.remove();
                /*判断当前SelectionKey所代表的Channel是否在Acceptable状态，如果是，就进行客户端的接收*/
                if (sk.isAcceptable()){
                    doAccept(sk);
                /*判读Channel是否已经可读*/
                }else if (sk.isValid() && sk.isReadable()){
                    /*统计系统处理每一个连接的时间*/
                    if(!time_stat.containsKey(((SocketChannel)sk.channel()).socket())){
                        time_stat.put(((SocketChannel)sk.channel()).socket(),System.currentTimeMillis());
                        doRead(sk);
                    }
                /*判断通道是否准备好，是否可写*/
                }else if (sk.isValid() && sk.isWritable()){
                    doWrite(sk);
                    e=System.currentTimeMillis();
                    long b=time_stat.remove(((SocketChannel)sk.channel()).socket());
                    System.out.println("spend:"+(e-b)+"ms");
                }
            }

        }


    }

    private void doAccept(SelectionKey sk){
        ServerSocketChannel server= (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;

        try {
            /*表示和客户端通信的通道*/
            clientChannel=server.accept();
            /*将通道配置为非阻塞模式，也就是要求系统在准备好IO后，在通知我们的线程来读取或者写入*/
            clientChannel.configureBlocking(false);
            /*
            * 将新生成的Channel注册到selector选择器上，并告诉Selector，我现在对读 OP_READ 操作感兴趣
            * 这样，当Selector发现这个Channel已经准备好读时，就能给线程一个通知
            * */
            SelectionKey clientKey=clientChannel.register(selector,SelectionKey.OP_READ);
            /*新建一个对象实例，一个EchoClient实例代表一个客户端*/
            EchoClient echoClient=new EchoClient();
            /*将这个客户端实例作为附件，附加到表示这个连接的SelectionKey上
            * 这样在整个连接处理过程中，我们都可以共享这个EchoClient实例
            * */
            clientKey.attach(echoClient);

            InetAddress clientAddress=clientChannel.socket().getInetAddress();
            System.out.println("Accept connection from "+clientAddress.getHostAddress()+".");

        } catch (IOException e) {
            System.out.println("Failed to accept new client!");
            e.printStackTrace();
        }

    }

    private void doRead(SelectionKey sk){
        SocketChannel channel= (SocketChannel) sk.channel();
        ByteBuffer bb=ByteBuffer.allocate(8192);
        int len;

        try {
            len=channel.read(bb);
            if(len < 0){
                channel.close();
                return;
            }
        } catch (IOException e) {
            System.out.println("Failed to read from client.");
            e.printStackTrace();
            try {
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        }

        bb.flip();
        tp.execute(new HandleMsg(sk,bb));
    }

    private void doWrite(SelectionKey sk){
        SocketChannel channel= (SocketChannel) sk.channel();
        EchoClient echoClient= (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outq=echoClient.getOutputQueue();

        ByteBuffer bb=outq.getLast();
        try {
            int len=channel.write(bb);
            if (len == -1){
                channel.close();
                return;
            }

            if (bb.remaining() == 0){
                /*如果全部发送完成，则移除这个缓存对象*/
                outq.removeLast();
            }
        } catch (IOException e) {
            System.out.println("Failed to write to client.");
            e.printStackTrace();
            try {
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        /*
        * 在全部数据发送完成后，也就是outq长度为0，需要将写事件OP_WRITE从感兴趣的操作中移除
        * 避免每次Channel准备好写时，都会来执行doWrite()方法，而实际上，你又无数据可写。
        * */
        if (outq.size() == 0){
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    public static void main(String[] args) throws IOException {
        NIOEchoServer server=new NIOEchoServer();
        server.startServer();
    }

}
