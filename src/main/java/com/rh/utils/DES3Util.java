/*
 * Copyright (c) 2013, Asiainfo-Linkage. All rights reserved.<br>
 * Asiainfo-Linkage PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package com.rh.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <p>
 * <b>��Ȩ��</b>Copyright (c) 2013 ��������.<br>
 * <b>���̣�</b>MyTest<br>
 * <b>�ļ���</b>DES3Test.java<br>
 * <b>�����ˣ�</b> gaojian3<br>
 * <b>����ʱ�䣺</b>2013-9-5 ����2:07:52<br>
 * <p>
 * <b>TODO(һ�仰���ܼ���).</b><br>
 * TODO(DES3����).<br>
 * </p>
 *
 * @author gaojian3
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
public class DES3Util {

   //��������㷨����DES��DESede(��3DES)��Blowfish
   private static final String Algorithm = "DESede";
   
   public static void main(String[] args) throws IOException {
	   
	   String m = "userID=itvtest1&userToken=03168303891651601516521016112900"
			   + "&productID=productIDa0000000000000000000001&price=1000&timeStamp=20130905100000"
			   + "&notifyUrl=http://192.168.33.33:8080/testnotify.html";
	  
	   String key = "SP0000011234567890123456";
	   m = "userID=wsr4$userToken=88888888$isHotelUser=1$productID=$price=1$PurchaseType=1$ContentType=0$CategoryID=$ContentID=Umai:PROG/2873374@BESTV.SMG.SMG$timeStamp=20170317223719$notifyUrl=http://127.0.0.1:8089/testnotify.html?A=A&B=B$optFlag=EPG";
	   // 1. ���� 
	   // 1.1 �����ַ���
	   byte[] e = encryptMode(m.getBytes(), key);
	   
	   // 1.2 BASE64Encoder�õ����ܺ���ַ���
	   BASE64Encoder enc = new BASE64Encoder();
	   String eS = enc.encode(e);
	   eS = URLEncoder.encode(eS);
	   System.out.println(eS);
	   
	   // 2. ����
	   // 2.1 BASE64Decoder���ܺ���ַ���
	   BASE64Decoder dec=new BASE64Decoder();
	   byte[] d = dec.decodeBuffer(eS);
	   // 2.2 �����ַ���
	   String ds = new String(decryptMode(d, key));
	   System.out.println(ds);
   }
   
   
   /**
    * @description ͨ��key��3DES����msg������Base64�Ľ��
    * @author gaojian3
    * @date 2013-9-16 ����2:14:55 
    * @version 1.0.0
    * @param msg
    * @param key
    * @return 
    * @return String
    */
   public static String encryptMsg(String msg, String key){
	   
	   try {
		   if (null == msg || null == key) {
			   return null;
		   }
		   
		   // ���� 
		   // 1 �����ַ���
		   byte[] e = encryptMode(msg.getBytes(), key);
		   
		   // 2 BASE64Encoder�õ����ܺ���ַ���
		   BASE64Encoder enc = new BASE64Encoder();
		   return enc.encode(e);
	   } catch (Exception e) {
		   return msg;
	   }
   }

   /**
    * @description ͨ��key��3DES����msg������Base64�Ľ��
    * @author gaojian3
    * @date 2013-9-16 ����2:16:41 
    * @version 1.0.0
    * @param msg
    * @param key
    * @return 
    * @return String
    */
   public static String decryptMsg(String msg, String key){
	   
	   try {
		   if (null == msg || null == key) {
			   return null;
		   }
		   
		   // ����
		   // 1 BASE64Decoder���ܺ���ַ���
		   BASE64Decoder dec=new BASE64Decoder();
		   byte[] d = dec.decodeBuffer(msg);
		   
		   // 2 �����ַ���
		   return new String(decryptMode(d, key));
		   
	   } catch (Exception e) {
		   return msg;
	   }
   }
   
   /**
    * ���ܷ���
    * @param src Դ���ݵ��ֽ�����
    * @return 
    */
   public static byte[] encryptMode(byte[] src, String key) {
       try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(key), Algorithm);    //������Կ
            Cipher c1 = Cipher.getInstance(Algorithm);    //ʵ�����������/���ܵ�Cipher������
            c1.init(Cipher.ENCRYPT_MODE, deskey);    //��ʼ��Ϊ����ģʽ
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }


   /**
    * ���ܺ���
    * @param src ���ĵ��ֽ�����
    * @return
    */
   public static byte[] decryptMode(byte[] src, String key) {
       try {
           SecretKey deskey = new SecretKeySpec(build3DesKey(key), Algorithm);
           Cipher c1 = Cipher.getInstance(Algorithm);
           c1.init(Cipher.DECRYPT_MODE, deskey);    //��ʼ��Ϊ����ģʽ
           return c1.doFinal(src);
       } catch (java.security.NoSuchAlgorithmException e1) {
           e1.printStackTrace();
       } catch (javax.crypto.NoSuchPaddingException e2) {
           e2.printStackTrace();
       } catch (Exception e3) {
           e3.printStackTrace();
       }
       return null;
    }
   
   
   /*
    * �����ַ���������Կ�ֽ����� 
    * @param keyStr ��Կ�ַ���
    * @return 
    * @throws UnsupportedEncodingException
    */
   public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
       byte[] key = new byte[24];    //����һ��24λ���ֽ����飬Ĭ�����涼��0
       byte[] temp = keyStr.getBytes("UTF-8");    //���ַ���ת���ֽ�����
       
       /*
        * ִ�����鿽��
        * System.arraycopy(Դ���飬��Դ�������￪ʼ������Ŀ�����飬��������λ)
        */
       if(key.length > temp.length){
           //���temp����24λ���򿽱�temp�����������ȵ����ݵ�key������
           System.arraycopy(temp, 0, key, 0, temp.length);
       }else{
           //���temp����24λ���򿽱�temp����24�����ȵ����ݵ�key������
           System.arraycopy(temp, 0, key, 0, key.length);
       }
       return key;
   } 
}


