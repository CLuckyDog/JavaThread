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
 * <b>版权：</b>Copyright (c) 2013 亚信联创.<br>
 * <b>工程：</b>MyTest<br>
 * <b>文件：</b>DES3Test.java<br>
 * <b>创建人：</b> gaojian3<br>
 * <b>创建时间：</b>2013-9-5 下午2:07:52<br>
 * <p>
 * <b>TODO(一句话功能简述).</b><br>
 * TODO(DES3加密).<br>
 * </p>
 *
 * @author gaojian3
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DES3Util {

   //定义加密算法，有DES、DESede(即3DES)、Blowfish
   private static final String Algorithm = "DESede";
   
   public static void main(String[] args) throws IOException {
	   
	   String m = "userID=itvtest1&userToken=03168303891651601516521016112900"
			   + "&productID=productIDa0000000000000000000001&price=1000&timeStamp=20130905100000"
			   + "&notifyUrl=http://192.168.33.33:8080/testnotify.html";
	  
	   String key = "SP0000011234567890123456";
	   m = "userID=wsr4$userToken=88888888$isHotelUser=1$productID=$price=1$PurchaseType=1$ContentType=0$CategoryID=$ContentID=Umai:PROG/2873374@BESTV.SMG.SMG$timeStamp=20170317223719$notifyUrl=http://127.0.0.1:8089/testnotify.html?A=A&B=B$optFlag=EPG";
	   // 1. 加密 
	   // 1.1 加密字符串
	   byte[] e = encryptMode(m.getBytes(), key);
	   
	   // 1.2 BASE64Encoder得到加密后的字符串
	   BASE64Encoder enc = new BASE64Encoder();
	   String eS = enc.encode(e);
	   eS = URLEncoder.encode(eS);
	   System.out.println(eS);
	   
	   // 2. 解密
	   // 2.1 BASE64Decoder加密后的字符串
	   BASE64Decoder dec=new BASE64Decoder();
	   byte[] d = dec.decodeBuffer(eS);
	   // 2.2 解密字符串
	   String ds = new String(decryptMode(d, key));
	   System.out.println(ds);
   }
   
   
   /**
    * @description 通过key，3DES加密msg，返回Base64的结果
    * @author gaojian3
    * @date 2013-9-16 下午2:14:55 
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
		   
		   // 加密 
		   // 1 加密字符串
		   byte[] e = encryptMode(msg.getBytes(), key);
		   
		   // 2 BASE64Encoder得到加密后的字符串
		   BASE64Encoder enc = new BASE64Encoder();
		   return enc.encode(e);
	   } catch (Exception e) {
		   return msg;
	   }
   }

   /**
    * @description 通过key，3DES加密msg，返回Base64的结果
    * @author gaojian3
    * @date 2013-9-16 下午2:16:41 
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
		   
		   // 解密
		   // 1 BASE64Decoder加密后的字符串
		   BASE64Decoder dec=new BASE64Decoder();
		   byte[] d = dec.decodeBuffer(msg);
		   
		   // 2 解密字符串
		   return new String(decryptMode(d, key));
		   
	   } catch (Exception e) {
		   return msg;
	   }
   }
   
   /**
    * 加密方法
    * @param src 源数据的字节数组
    * @return 
    */
   public static byte[] encryptMode(byte[] src, String key) {
       try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(key), Algorithm);    //生成密钥
            Cipher c1 = Cipher.getInstance(Algorithm);    //实例化负责加密/解密的Cipher工具类
            c1.init(Cipher.ENCRYPT_MODE, deskey);    //初始化为加密模式
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
    * 解密函数
    * @param src 密文的字节数组
    * @return
    */
   public static byte[] decryptMode(byte[] src, String key) {
       try {
           SecretKey deskey = new SecretKeySpec(build3DesKey(key), Algorithm);
           Cipher c1 = Cipher.getInstance(Algorithm);
           c1.init(Cipher.DECRYPT_MODE, deskey);    //初始化为解密模式
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
    * 根据字符串生成密钥字节数组 
    * @param keyStr 密钥字符串
    * @return 
    * @throws UnsupportedEncodingException
    */
   public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
       byte[] key = new byte[24];    //声明一个24位的字节数组，默认里面都是0
       byte[] temp = keyStr.getBytes("UTF-8");    //将字符串转成字节数组
       
       /*
        * 执行数组拷贝
        * System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
        */
       if(key.length > temp.length){
           //如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
           System.arraycopy(temp, 0, key, 0, temp.length);
       }else{
           //如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
           System.arraycopy(temp, 0, key, 0, key.length);
       }
       return key;
   } 
}


