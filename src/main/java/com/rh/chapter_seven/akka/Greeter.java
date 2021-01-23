package com.rh.chapter_seven.akka;

import akka.actor.UntypedActor;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/18 14:24
 * @description: Akka Hello World demo
 * @modified By:
 */
public class Greeter extends UntypedActor {
    public static enum Msg{
        GREET,DONE;
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg==Msg.GREET){
            System.out.println("Hello World!");
            getSender().tell(Msg.DONE,getSelf());

        }else {
            unhandled(msg);
        }
    }
}
