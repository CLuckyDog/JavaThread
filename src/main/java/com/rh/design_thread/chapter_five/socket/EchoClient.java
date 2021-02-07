package com.rh.design_thread.chapter_five.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/13 11:04
 * @description: socket client 代码
 * @modified By:
 */
public class EchoClient {
    public static void main(String[] args) {
        Socket client=null;
        PrintWriter writer=null;
        BufferedReader reader=null;

        try {
            client=new Socket();
            client.connect(new InetSocketAddress("localhost",8000));
            writer=new PrintWriter(client.getOutputStream(),true);
            writer.println("a");
            writer.flush();

            reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("from server:"+reader.readLine());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer!=null)writer.close();
                if (reader!=null)reader.close();
                if (client!=null)client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
