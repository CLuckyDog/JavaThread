package com.rh.chapter_seven.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/18 14:54
 * @description:
 * @modified By:
 */
public class HelloMainSimple {
    public static void main(String[] args) {
        ActorSystem system=ActorSystem.create("Hello", ConfigFactory.load("samplehello.conf"));
        ActorRef a=system.actorOf(Props.create(HelloWorld.class),"helloWorld");
        System.out.println("HelloWorld Actor Path:"+a.path());
    }
}
