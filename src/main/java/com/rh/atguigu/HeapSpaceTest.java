package com.rh.atguigu;

import java.util.ArrayList;
import java.util.List;

/**
 * OutOfMemoryError: Java heap space
 */
public class HeapSpaceTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        String str = "1111111111111111111111111111";
        StringBuffer tempStr = new StringBuffer();
        while (true){
            tempStr.append(str).append("\r\n");
//            list.add(str);
        }
    }
}
