package com.rh.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 潘忠健
 * \* Date: 2021/2/26
 * \* Time: 10:04
 * \* Description:
 * \
 */
@Slf4j(topic = "c.MapTest")
public class MapTest {
    public static void main(String[] args) {
        log.debug("------1612520262636--[1]-----" +
                "{" +
                "transactionID:"+111+
                ",ContentID:"+222+
                ",userIP:"+333+
                ",userID:"+444+
                ",requestIp:"+555+
                ",ProductID:"+666+
                ",PayType:"+777+
                ",subscriptionID:"+888
                +"}");
    }
}