package com.rh.chapter_seven.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/18 14:36
 * @description:
 * @modified By:
 */
public class HelloWorld extends UntypedActor {
    ActorRef greeeter;

    @Override
    public void preStart() throws Exception {
        greeeter=getContext().actorOf(Props.create(Greeter.class),"greeter");
        System.out.println("Greeter Actor Path:"+greeeter.path());
        greeeter.tell(Greeter.Msg.GREET,getSelf());
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg==Greeter.Msg.DONE){
            greeeter.tell(Greeter.Msg.GREET,getSelf());
            getContext().stop(getSelf());
        }else {
            unhandled(msg);
        }
    }
}
