package com.rh.design_thread.chapter_five;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/6 11:10
 * @description:
 * @modified By:
 */
public class InnerClassTest {

    private InnerClassTest(){
        System.out.println("1111111111111");
    }

    private static class InnerClassHolder{
        private static InnerClassTest instance=new InnerClassTest();
    }

    public static InnerClassTest getInstance(){
        return InnerClassHolder.instance;
    }

    public static void main(String[] args) {

    }
}
