/*
 * Copyright (c) 2015, Asiainfo-Linkage. All rights reserved.<br>
 * Asiainfo-Linkage PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package com.rh.utils;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * <b>版权：</b>Copyright (c) 2015 亚信.<br>
 * <b>工程：</b>iptvsys<br>
 * <b>文件：</b>DigestUtil.java<br>
 * <b>创建人：</b> zhaoyj 71919<br>
 * <b>创建时间：</b>2015-9-24 上午10:43:29<br>
 * <p>
 * <b>DigestUtil.</b><br>
 * DigestUtil.<br>
 * </p>
 *
 * @author zhaoyj
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DigestUtil {
    /** 加密方式 AES */
    public static final String AES = "AES";
    /** 加密方式 DES */
    public static final String DES = "DES";
    /** 加密方式 MD5 */
    public static final String MD5 = "MD5";
    /** AES密钥长度 128 */
    public static final int AES_KEY_128 = 128;
    /** AES密钥长度 196 */
    public static final int AES_KEY_196 = 196;
    /** AES密钥长度 256 */
    public static final int AES_KEY_256 = 256;
    /** DES密钥长度 32 */
    public static final int DES_KEY_32 = 32;
    /** DES密钥长度 56 */
    public static final int DES_KEY_56 = 56;

    private DigestUtil() {
    }

    /*******************************************************************************
     * 
     * TODO base64
     * 
     *******************************************************************************/

    /**
     * Base64加密, 非标准
     * 
     * @param bytes
     * @return
     * @author: 陈晨<br>
     * @date: 2014-4-24<br>
     */
    /*
     * public static String encodeBase64(byte[] bytes) { if (bytes == null)
     * return null; // 将字节逐个转换为2位十六进制字符 int len = bytes.length; char result[] =
     * new char[len * 2]; for (int i=0; i<len; i++) { String hex2 =
     * ConvertUtil.byte2Hex(bytes[i]); result[i * 2] = hex2.charAt(0); result[i
     * * 2 + 1] = hex2.charAt(1); } return new String(result); }
     */

    /**
     * Base64解密, 非标准
     * 
     * @param res
     *            base64字符
     * @return
     * @author: 陈晨<br>
     * @date: 2014-4-24<br>
     */
    /*
     * public static byte[] decodeBase64(String res) { if (res == null) return
     * null; else if (res.length() % 2 != 0) throw new
     * NullPointerException("加密字符长度必须为2的整数倍"); else if
     * (!res.matches("[0-9|a-z|A-Z]+")) throw new
     * IllegalArgumentException("加密字符必须为十六进制字符");
     * 
     * // 每2位十六进制字符转换为1byte byte[] bytes = new byte[res.length() / 2]; for (int
     * i=0; i<res.length(); i+=2) { bytes[i/2] =
     * ConvertUtil.hex2Byte(res.substring(i, i + 2)); } return bytes; }
     */
    /*******************************************************************************
     * 
     * TODO MD5
     * 
     *******************************************************************************/

    /**
     * 标准MD5加密(MySQL等值，字符为小写)
     * 
     * @param msg
     * @return
     */
    public static String MD5(String msg) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
                'E', 'F' };
        try {
            byte[] strTemp = msg.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest(); // 16位加密
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            String dd = new String(str);
            return dd;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 简单MD5加密
     * 
     * @param res
     *            源字符
     * @return MD5
     */
    public static String MD5Simple(String res) {
        String rtn = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance(MD5);
            md5.update(res.getBytes());
            rtn = new BigInteger(1, md5.digest()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }

    /*******************************************************************************
     * 
     * TODO AES、DES
     * 
     *******************************************************************************/

    /**
     * 构造密钥
     * 
     * @param type
     * @param length
     * @return
     * @throws NoSuchAlgorithmException
     * @author: 陈晨<br>
     * @date: 2014-4-24<br>
     */
    public static String generateKey(String type, int length) throws NoSuchAlgorithmException {
        if (type == null || !type.matches(AES + "|" + DES))
            throw new IllegalArgumentException("密钥类型错误");
        if (type.equals(AES)
                && !(length == AES_KEY_128 || length == AES_KEY_196 || length == AES_KEY_256))
            throw new IllegalArgumentException("AES密钥长度错误");
        if (type.equals(DES) && !(length == DES_KEY_32 || length == DES_KEY_56))
            throw new IllegalArgumentException("DES密钥长度错误");

        // 通过加密类型实例化Key构造器
        KeyGenerator kg = KeyGenerator.getInstance(type);
        // 初始化Key长度
        kg.init(length);
        // 构造Key
        SecretKey skey = kg.generateKey();
        // base64加密Key
        return new String(Base64.encodeBase64(skey.getEncoded()));
    }

    /**
     * 还原密钥
     * 
     * @param key
     * @param type
     * @return SecretKey
     * @author: 陈晨<br>
     * @date: 2014-4-24<br>
     */
    public static Key reductionKey(byte[] key, String type) {
        if (key == null || "".equals(key))
            return null;
        if (type == null || !type.matches(AES + "|" + DES))
            throw new IllegalArgumentException("密钥类型错误");

        // 实例化密钥对象
        return new SecretKeySpec(key, type);
    }

    /**
     * 还原密钥
     * 
     * @author: 陈晨<br>
     * @date: 2014-9-24<br>
     */
    public static Key reductionKey(String key, String type) {
        return reductionKey(Base64.decodeBase64(key.getBytes()), type);
    }

    /**
     * AES/DES加密
     * 
     * @param res
     *            源字符
     * @param key
     *            base64密钥
     * @param type
     *            加密类型
     * @return base64加密字符
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @author: 陈晨<br>
     * @date: 2014-4-25<br>
     */
    public static byte[] encrypt(byte[] res, String key, String type)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        if (res == null)
            return null;
        if (type == null || !type.matches(AES + "|" + DES))
            throw new IllegalArgumentException("密钥类型错误");

        // 还原Key
        Key skey = reductionKey(key, type);
        // 实例化加密对象
        Cipher ci = Cipher.getInstance(type);
        ci.init(Cipher.ENCRYPT_MODE, skey);
        // 加密、构建字符串
        return ci.doFinal(res);
    }

    /**
     * AES/DES加密
     * 
     * @author: 陈晨<br>
     * @throws UnsupportedEncodingException
     * @date: 2014-9-24<br>
     */
    public static String encrypt(String res, String key, String type)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] bt = encrypt(res.getBytes("UTF-8"), key, type);// sgwang 防止中文乱码
        return new String(Base64.encodeBase64(bt));
    }

    /**
     * AES/DES解密
     * 
     * @param res
     *            base64源字符
     * @param key
     *            base64密钥
     * @param type
     *            加密类型
     * @return 解密字符
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @author: 陈晨<br>
     * @date: 2014-4-25<br>
     */
    public static byte[] decrypt(byte[] res, String key, String type)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        if (res == null)
            return null;
        if (type == null || !type.matches(AES + "|" + DES))
            throw new IllegalArgumentException("密钥类型错误");

        // 还原Key
        Key skey = reductionKey(key, type);
        // 实例化加密对象
        Cipher ci = Cipher.getInstance(type);
        ci.init(Cipher.DECRYPT_MODE, skey);
        // 返回解密字符
        return ci.doFinal(res);
    }

    /**
     * AES/DES解密
     * 
     * @author: 陈晨<br>
     * @date: 2014-9-24<br>
     */
    public static byte[] decryptBase64(String res, String key, String type)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        return decrypt(Base64.decodeBase64(res.getBytes()), key, type);
    }

    /**
     * AES/DES解密
     * 
     * @author: 陈晨<br>
     * @throws UnsupportedEncodingException
     * @date: 2014-9-24<br>
     */
    public static String decrypt(String res, String key, String type)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] bt = decryptBase64(res, key, type);// sgwang 防止中文乱码
        return new String(bt, "UTF-8");
    }

    /**
     * AES加密
     * 
     * @param res
     *            源字符
     * @param key
     *            base64密钥
     * @return base64加密字符
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @author: 陈晨<br>
     * @throws UnsupportedEncodingException
     * @date: 2014-4-25<br>
     */
    public static String encryptAES(String res, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        // System.out.println("res::"+res);
        // System.out.println("encrypt(res, key, AES)::"+encrypt(res, key,
        // AES));
        return encrypt(res, key, AES);
    }

    /**
     * AES解密
     * 
     * @param res
     *            base64源字符
     * @param key
     *            base64密钥
     * @return 解密字符
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @author: 陈晨<br>
     * @throws UnsupportedEncodingException
     * @date: 2014-4-25<br>
     */
    public static String decryptAES(String res, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return decrypt(res, key, AES);
    }

    /**
     * DES加密
     * 
     * @param res
     *            源字符
     * @param key
     *            base64密钥
     * @return base64加密字符
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @author: 陈晨<br>
     * @throws UnsupportedEncodingException
     * @date: 2014-4-25<br>
     */
    public static String encryptDES(String res, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return encrypt(res, key, DES);
    }

    /**
     * DES解密
     * 
     * @param res
     *            base64源字符
     * @param key
     *            base64密钥
     * @return 解密字符
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @author: 陈晨<br>
     * @throws UnsupportedEncodingException
     * @date: 2014-4-25<br>
     */
    public static String decryptDES(String res, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return decrypt(res, key, DES);
    }

    /**
     * AES/DES验证
     * 
     * @param res
     *            源字符
     * @param encodeRes
     *            base64加密字符
     * @param key
     *            base64密钥
     * @param type
     *            加密类型
     * @return
     * @author: 陈晨<br>
     * @date: 2014-4-25<br>
     */
    public static boolean validate(String res, String encodeRes, String key, String type) {
        if (res == null || encodeRes == null || key == null)
            return false;
        if (type == null || !type.matches(AES + "|" + DES))
            throw new IllegalArgumentException("密钥类型错误");
        try {
            // 解密
            String decode = decrypt(encodeRes, key, type);
            // 验证
            return res.equals(decode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * AES验证
     * 
     * @param res
     *            源字符
     * @param encodeRes
     *            base64加密字符
     * @param key
     *            base64密钥
     * @return
     * @author: 陈晨<br>
     * @date: 2014-4-25<br>
     */
    public static boolean validateAES(String res, String encodeRes, String key) {
        return validate(res, encodeRes, key, AES);
    }

    /**
     * DES验证
     * 
     * @param res
     *            源字符
     * @param encodeRes
     *            base64加密字符
     * @param key
     *            base64密钥
     * @return
     * @author: 陈晨<br>
     * @date: 2014-4-25<br>
     */
    public static boolean validateDES(String res, String encodeRes, String key) {
        return validate(res, encodeRes, key, DES);
    }

    public static final String decrypt(String data, String secretKey) {
        byte[] decoded = Base64.decodeBase64(data); // Base64解码
        byte[] decrypted = tripleDES(Cipher.DECRYPT_MODE, decoded, secretKey.getBytes());// 3DES解密
        return new String(decrypted);
    }

    private static byte[] tripleDES(int opmode, byte[] data, byte[] secretKey) {
        return cipher("DESede", "DESede/CBC/PKCS5Padding", opmode, data, "01234567".getBytes(),
                secretKey);
    }
    private static final byte[] cipher(String algorithm, String transformation, int opmode, byte[] data, byte[] iv,
                                       byte[] secretKey) {
        try {
            // 转换密钥
            Key key = SecretKeyFactory.getInstance(algorithm)
                    .generateSecret(new DESedeKeySpec(secretKey));
            // 转换初始化向量
            IvParameterSpec spec = new IvParameterSpec(iv);

            // 加密解密器
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(opmode, key, spec);

            // 加密解密操作
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*******************************************************************************
     * 
     * TODO 测试
     * 
     *******************************************************************************/

    public static void main(String[] args) throws Exception {

        // AES
        System.out.println("--------------------------------------------------");
        String res = "-128 3370 & 中文 | 10测试..........";
        System.out.println(res);
        // System.out.println("查看res字符串的编码格式："+CheckCharEncoding.getEncoding(res));

        String key = DigestUtil.generateKey(DigestUtil.AES, AES_KEY_128);
        System.out.println(key + " : " + key.length() * 4);
        System.out.println("AES_KEY_128:" + key);

        // AES_KEY_128:iYml4/SqJf+tvrcpwgO3yw==

        // AES加密
        String dres = DigestUtil.encryptAES(new String(res.getBytes(), "utf-8"), key);
        System.out.println(dres);
        // System.out.println("查看dres字符串的编码格式："+CheckCharEncoding.getEncoding(DigestUtil.encryptAES(res,
        // key)));
        String dres1 = DigestUtil.encryptAES(res, "iYml4/SqJf+tvrcpwgO3yw==");
        System.out.println(dres1);
        /////////////////////////////////////// 查询支付订单信息加密测试---begin///////////////////////////////////////////////////
        /*
         * {"transactionid": "id1232q213", "payment": "yudsd"}
         */
        String dres2 = DigestUtil.encryptAES("id1232q213", "iYml4/SqJf+tvrcpwgO3yw==");
        String dres3 = DigestUtil.encryptAES("yudsd", "iYml4/SqJf+tvrcpwgO3yw==");
        System.out.println("======查询支付订单信息加密测试---begin=======");
        System.out.println("transactionid=" + dres2);
        System.out.println("payment=" + dres3);
        System.out.println("======查询支付订单信息加密测试---end=========");
        /*
         * {"transactionid":"ySjj15nY0C41V4u2+H30ag==",
         * "payment":"vEYOcZiqikcYbWn3ttMqWA=="}
         */
        /////////////////////////////////////// 查询支付订单信息加密测试---end////////////////////////////////////////////////////

        /////////////////////////////////////// 修改支付订单信息退费状态---begin///////////////////////////////////////////////////
        /*
         * {"transactionid": "id1232q213", "refund_status": "yudsd"}
         */
        String dres4 = DigestUtil.encryptAES("id1232q213", "iYml4/SqJf+tvrcpwgO3yw==");
        String dres5 = DigestUtil.encryptAES("1", "iYml4/SqJf+tvrcpwgO3yw==");
        System.out.println("======修改支付订单信息退费状态---begin=======");
        System.out.println("transactionid=" + dres4);
        System.out.println("refund_status=" + dres5);
        System.out.println("======修改支付订单信息退费状态---end=========");
        /*
         * transactionid=ySjj15nY0C41V4u2+H30ag==
         * refund_status=2h/iKJLTyNsWWGPWTjFzRQ==
         */

        /*
         * {"transactionid": "ySjj15nY0C41V4u2+H30ag==", "refund_status":
         * "2h/iKJLTyNsWWGPWTjFzRQ=="}
         */

        String drestransactionid = DigestUtil.encryptAES(
                "{\"transactionid\": \"id1232q213\",\"payment\":\"yudsd\"}",
                "iYml4/SqJf+tvrcpwgO3yw==");
        System.out.println("findPayInfoAPI加密={" + drestransactionid);
        String dresrefund_status = DigestUtil.encryptAES(
                "{\"transactionid\":\"id1232q213\",\"refund_status\":1}",
                "iYml4/SqJf+tvrcpwgO3yw==");
        System.out.println("updateRefundStatus加密={" + dresrefund_status);
        /*
         * findPayInfoAPI加密=
         * N+lZPkk9DkBaIThV7teztpPc6bbXGr0QZxD7AcEVeMu6jHOoTcPg0OkWGe3bpwL1H85/
         * L5XXscVkVFxh4fLfrQ==
         * 
         * updateRefundStatus加密=
         * 
         * N+lZPkk9DkBaIThV7teztnZxWYfwK0paGXNnL6M412MO4cUXbYIuKYDC/
         * YLXTXI0WgAyAl2uMpEaCHu+USiA4w==
         * 
         */

        String s1 = DigestUtil.decryptAES(
                "seuPx/wgbolDZIbKSp1YMfyvXWOdd/z80blo22UioUAE/y3NDYPsocHbBkXyAMb6Oc5cYtzWd/5Orm2fJ1nvjA==",
                "iYml4/SqJf+tvrcpwgO3yw==");
        System.out.println("findPayInfoAPI解密=\n" + s1);
        String s2 = DigestUtil.decryptAES(
                "N+lZPkk9DkBaIThV7teztnZxWYfwK0paGXNnL6M412MO4cUXbYIuKYDC/YLXTXI0WgAyAl2uMpEaCHu+USiA4w==",
                "iYml4/SqJf+tvrcpwgO3yw==");
        System.out.println("updateRefundStatus解密=\n" + s2);

        String rrr = "{" + "\"description\": \"221212232\"," + "\"status\": \"0\","
                + "\"subject\": \"12132\"," + "\"title\": \"title\","
                + "\"transactionid\": \"id1232q213\"," + "\"payment\": \"yudsd\","
                + "\"payid\": \"121323\"," + "\"trans_money\": 100," + "\"payway\": \"手机\","
                + "\"plug_version\": \"v1.0.0.0\"," + "\"pay_type\": 2,"
                + "\"return_url\": \"www.1232.121\"," + "\"notification_url\": \"localhost\","
                + "\"trans_time\": 1441782638000," + "\"comp_time\": 1441782638000,"
                + "\"notify_status\": 1," + "\"refund_status\": 1," + "\"applyadmin\": null,"
                + "\"applytime\": null," + "\"refundadmin\": null," + "\"refundtime\": null,"
                + "\"notify_fail_num\": 0" + "}";

        String s3 = DigestUtil.encryptAES(rrr, "iYml4/SqJf+tvrcpwgO3yw==");

        JSONObject jsonobject = JSONObject
                .fromObject("{\"transactionid\":\"id1232q213\",\"payment\":\"yudsd\"}");

        String s333 = DigestUtil.encryptAES(jsonobject.toString(), "iYml4/SqJf+tvrcpwgO3yw==");
        // s312==ddd
        String ddd = "seuPx/wgbolDZIbKSp1YMfyvXWOdd/z80blo22UioUAE/y3NDYPsocHbBkXyAMb6Oc5cYtzWd/5Orm2fJ1nvjA==";

        String EE = "seuPx/wgbolDZIbKSp1YMfyvXWOdd/z80blo22UioUAE/y3NDYPsocHbBkXyAMb6Oc5cYtzWd/5Orm2fJ1nvjA==";
        JSONObject jsonobjectRefundStatus = JSONObject
                .fromObject("{\"transactionid\":\"id1232q213\",\"refund_status\":1}");
        String RefundStatus = DigestUtil.encryptAES(jsonobjectRefundStatus.toString(),
                "iYml4/SqJf+tvrcpwgO3yw==");
        System.out.println("======TESTupdateRefundStatusAPI 参数加密==========\n" + RefundStatus);
        String ssss2 = DigestUtil.decryptAES(ddd, "iYml4/SqJf+tvrcpwgO3yw==");

        String RETURNupdateRefundStatusAPI = DigestUtil.decryptAES("2h/iKJLTyNsWWGPWTjFzRQ==",
                "iYml4/SqJf+tvrcpwgO3yw==");
        System.out.println("======RETURN--updateRefundStatusAPI->string==========\n"
                + RETURNupdateRefundStatusAPI);

        System.out.println("======TESTjson->string==========\n" + jsonobject.toString());
        // System.out.println("======TESTfindPayInfoAPI加密==========\n"+s3);
        System.out.println("======TESTfindPayInfoAPI 参数加密1==========\n" + ddd);
        System.out.println("======TESTfindPayInfoAPI 参数加密2==========\n" + s333);
        System.out.println("======TESTfindPayInfoAPI解密1==========\n" + ssss2);

        String ssss333 = DigestUtil.decryptAES(s333, "iYml4/SqJf+tvrcpwgO3yw==");
        System.out.println("======TESTfindPayInfoAPI解密2==========\n" + ssss333);
        String ssss2222 = DigestUtil.decryptAES(EE, "iYml4/SqJf+tvrcpwgO3yw==");

        System.out.println("======s3加密->string==========\n" + s3);
        System.out.println("======s3解密->string==========\n"
                + DigestUtil.decryptAES(s3, "iYml4/SqJf+tvrcpwgO3yw=="));
        // System.out.println("查看s3字符串的编码格式："+CheckCharEncoding.getEncoding(DigestUtil.encryptAES(s3,
        // key)));
        System.out.println("java环境默认的字符编码：" + Charset.defaultCharset());

        /////////////////////////////////////// 修改支付订单信息退费状态---end////////////////////////////////////////////////////

        // 解密
        dres = DigestUtil.decryptAES(new String(dres.getBytes(), "utf-8"), key);
        System.out.println(dres);

        dres = DigestUtil.decryptAES(dres1, "iYml4/SqJf+tvrcpwgO3yw==");
        System.out.println("{iYml4/SqJf+tvrcpwgO3yw==}:::::" + dres);

        // byte 2 hex
        System.out.println("--------------------------------------------------");
        res = "中文aaa123123";
        System.out.println(res);
        // dres = ConvertUtil.bytes2Hex(res.getBytes());
        // System.out.println(dres);
        // dres = new String(ConvertUtil.hex2Bytes(dres));
        // System.out.println(dres);

        // -128
        System.out.println("--------------------------------------------------");
        byte b = 127;
        // String binary = ConvertUtil.byte2Binary(b);
        // System.out.println(binary);
        // System.out.println(ConvertUtil.binary2Byte(binary));

        System.out.println("======TESTfindPayInfoAPI解密3==========\n" + ssss2222);
        String requsturl = "0Yjmy5zymWx3u4Qyr++4pl2Pd0hte4Ra1PaptNiDYpAlRQiGJOTmNWWR5n2cDBjlJwKMNAQYGQvdKsFex9WZHqnWzkG3udex+40D8CUsF3y4igEQXazKmsMud1G8qyEnDF8wwnZFFND8omTC3+IfkyYclQJfDuHfGzEw1n3zyRtKAIa9NSW3r9hoJGWs0KLSNK/A3557YxCrlPlNT+PfXO17XbkDAP1nmN3NJnAFdUA6+PR+xio+l95hWX0ZpvOtUmhUxog3fvorw7oXWO1/k5p0iWKI3R0vUzGdqa78QsfYm1o1BxCIC7Ny1N0d0GHa7+Lv1rb7Zlda5uxTR6KoFBHb/+S7Ijcuv+U4dUjb0B+DBRbif1OcrUb6EVJXs+AelCGz+lMDixG47/SwbSRS+N0szeclLJ1PKvhk9JFI/6AtU4otxdxuxdcXrO0CI0VJHNvrx++NSZkdMevjf4k8yQOU0/ITZOTf7RAOCCpBsfYdOdBLdKwJem/FgPiTYCr6snxY8Gi40RJ7TMY2AG7vEGIG7aCYieHHLBVE+MwitFZ4/QDZHF1jmcyvief6YjrAJkg9aJCflD2k4323tQwMRkyfnP0GcTunG7cQSCtF/KxYqb2NxGi8/lVswmR3UXHTX6W9RZvXTbLDRn9qWf2U4vZUpTcLdcxPTfCxeClOEarJHwCjpOjW/KkXY+V7Y0Lpep8/AmsJ/WA8bdqlx+z/MklX7o+0++7LuFPmjtUgfPxg1qQob7mfNh3UEtOtG3Iv54zjeTTjHZU1npEzpf8gY7rB7jfnf0MBzOFiftJlH/RbBUvE0JnXuvTBLLHVCA8ByD8BzBUmwj7V4ECmHg6aJqtR/YU3qVJlDET3EGEB+0RdoyJDyXG74XDL07FUwQs1";
        // System.out.println("查看字符串的编码格式："+CheckCharEncoding.getEncoding(requsturl));
        String requsturl2 = new String(requsturl.getBytes(), "ISO-8859-1");
        // System.out.println("查看转化后字符串的编码格式："+CheckCharEncoding.getEncoding(requsturl2));
        // System.out.println("requsturl====\n"+new String
        // (DigestUtil.decryptAES(requsturl,"iYml4/SqJf+tvrcpwgO3yw==").getBytes("ISO-8859-1"),"GB2312"));
        System.out.println(
                "requsturl====\n" + DigestUtil.decryptAES(requsturl, "iYml4/SqJf+tvrcpwgO3yw=="));
        System.out.println("requsturl编码====\n" + DigestUtil.decryptAES(
                "DJovS/I4gm/n7Y9bZMgF5bjAA+Kmp9XECkqlKnOXqI4=", "iYml4/SqJf+tvrcpwgO3yw=="));

        System.out
                .println("***********************************************************************");

        System.out.println(DigestUtil.decryptAES(
                "0Yjmy5zymWx3u4Qyr++4pm7RFmmLXGV6RqBEQFlX/7lpfA0fmCaCyQsP8McF6RfCmFHaWmbFDkoVl3NxQcvjpbYOTrcuUux8F+3lwuzYXZS7qMmehRiXmVkhrCk/prF0AFu9176CS8hIuErHRgZKYJM71WXF5pt/OYAq9caqJK1HZ4PQmX1RcvdWdpX2tPBKGuEz6/npjdvYd1tYAD/vq22P24Iv8kft51wBvzPT8ezufl7HhHe5B8jkeacfvwvcuUjGbVL/LWBIJ9q91Qt8l0ykpT/gXxsh19Q6RF1oHcvCiGbLbkFFHf9kV4jKv9p1Mc0fT0CseB3xqTwQ0QUtKpRrOy4zMzgiRu5kd6mVE6Ot72VDQUjQIlHpsy+vvCKbK3mRGYrXpRVhtoqUApXsOQ2WQCgImFnemejePfWfRIodKI4j/9jPmeAA9F7uCfErHba1kg16ySrzA2Hq2s56069RKtJifdFKvy8Bc5rDYH+wYQn6MS2ReWeRQh+ihVIzldns+O9/vTdrqesL6YVUzyte1rXv5bktzu+Jr5wNI5ETgTGBKJAHu+V+ZaHdsIPtlTHtHqBQSvYE7eave56G1+ljoLQpHWQie7KJJEPCB3xUrBhxy0uqFT2Ig/HnkEvwiAyl9149l4cSokAkveHXI9A7XR6Cz2KEeNALzaq3SER+mFCzQ3HUycAz2lE620qo/MqPv2grbQ4lq4sVl9trA9gNTFWLhb/k+ikpK7EEpqEJJTrlU47hvXB9uZ6HIp7ZrlSyY1fBRwv2HQe6TG0OqSpMDmwYnF2anyXtbvKi5RC5LSOoSk0WGUfvcEITVbrxn0MU/oPGFRGalKxZdVh7r2DfpBPG/r+So2eSviFbb7LTritaCBA8yu18/R7qgKXPa8zBq3GDypwgaTlqb9P0NMuQU6ckcnRZwW2GnS7RGAkiW5Bs34OdxaWtORLx8PeyS5srKum2+1te9VmWxU2jVZvGC6jM5hZbw/tkJHp6LmBO8nBMGXR4tXe1ief1Ao2ybmKE7bk73alzH13+y+Dw3gj6Up0gaq477BEf8jTrxGFfqISTc7I/wl2UyT9QtMvVVsoWWJqX1T9FLor4nHDgDCGnRltgwDWSONa1/sMYb57aDq7KgsSniQgS85+WeemCWb+Erf12nVDMSuo5KabIyBmHwxJiue90jk5ZVqc+b+s=",
                "iYml4/SqJf+tvrcpwgO3yw=="));
        // String dres11 = DigestUtil.encryptAES("",
        // "iYml4/SqJf+tvrcpwgO3yw==");
        // System.out.println(dres11);
        System.out
                .println(
                        DigestUtil.decryptAES(
                                new String(
                                        "0Yjmy5zymWx3u4Qyr++4pm7RFmmLXGV6RqBEQFlX/7lpfA0fmCaCyQsP8McF6RfCmFHaWmbFDkoVl3NxQcvjpbYOTrcuUux8F+3lwuzYXZS7qMmehRiXmVkhrCk/prF0AFu9176CS8hIuErHRgZKYJM71WXF5pt/OYAq9caqJK1HZ4PQmX1RcvdWdpX2tPBKGuEz6/npjdvYd1tYAD/vq22P24Iv8kft51wBvzPT8ezufl7HhHe5B8jkeacfvwvcuUjGbVL/LWBIJ9q91Qt8l0ykpT/gXxsh19Q6RF1oHcvCiGbLbkFFHf9kV4jKv9p1Mc0fT0CseB3xqTwQ0QUtKpRrOy4zMzgiRu5kd6mVE6Ot72VDQUjQIlHpsy+vvCKbK3mRGYrXpRVhtoqUApXsOQ2WQCgImFnemejePfWfRIodKI4j/9jPmeAA9F7uCfErHba1kg16ySrzA2Hq2s56069RKtJifdFKvy8Bc5rDYH+wYQn6MS2ReWeRQh+ihVIzldns+O9/vTdrqesL6YVUzyte1rXv5bktzu+Jr5wNI5ETgTGBKJAHu+V+ZaHdsIPtlTHtHqBQSvYE7eave56G1+ljoLQpHWQie7KJJEPCB3xUrBhxy0uqFT2Ig/HnkEvwiAyl9149l4cSokAkveHXI9A7XR6Cz2KEeNALzaq3SER+mFCzQ3HUycAz2lE620qo/MqPv2grbQ4lq4sVl9trA9gNTFWLhb/k+ikpK7EEpqEJJTrlU47hvXB9uZ6HIp7ZrlSyY1fBRwv2HQe6TG0OqSpMDmwYnF2anyXtbvKi5RC5LSOoSk0WGUfvcEITVbrxn0MU/oPGFRGalKxZdVh7r2DfpBPG/r+So2eSviFbb7LTritaCBA8yu18/R7qgKXPa8zBq3GDypwgaTlqb9P0NMuQU6ckcnRZwW2GnS7RGAkiW5Bs34OdxaWtORLx8PeyS5srKum2+1te9VmWxU2jVZvGC6jM5hZbw/tkJHp6LmBO8nBMGXR4tXe1ief1Ao2ybmKE7bk73alzH13+y+Dw3gj6Up0gaq477BEf8jTrxGFfqISTc7I/wl2UyT9QtMvVVsoWWJqX1T9FLor4nHDgDCGnRltgwDWSONa1/sMYb57aDq7KgsSniQgS85+WeemCWb+Erf12nVDMSuo5KabIyBmHwxJiue90jk5ZVqc+b+s="
                                                .getBytes("GB2312"),
                                        "utf-8"),
                                "iYml4/SqJf+tvrcpwgO3yw=="));
        // System.out.println(DigestUtil.decryptAES("0Yjmy5zymWx3u4Qyr++4pm7RFmmLXGV6RqBEQFlX/7lpfA0fmCaCyQsP8McF6RfCmFHaWmbFDkoVl3NxQcvjpbYOTrcuUux8F+3lwuzYXZS7qMmehRiXmVkhrCk/prF0AFu9176CS8hIuErHRgZKYJM71WXF5pt/OYAq9caqJK1HZ4PQmX1RcvdWdpX2tPBKGuEz6/npjdvYd1tYAD/vq22P24Iv8kft51wBvzPT8ezufl7HhHe5B8jkeacfvwvcuUjGbVL/LWBIJ9q91Qt8l0ykpT/gXxsh19Q6RF1oHcvCiGbLbkFFHf9kV4jKv9p1Mc0fT0CseB3xqTwQ0QUtKpRrOy4zMzgiRu5kd6mVE6Ot72VDQUjQIlHpsy+vvCKbK3mRGYrXpRVhtoqUApXsOQ2WQCgImFnemejePfWfRIodKI4j/9jPmeAA9F7uCfErHba1kg16ySrzA2Hq2s56069RKtJifdFKvy8Bc5rDYH+wYQn6MS2ReWeRQh+ihVIzldns+O9/vTdrqesL6YVUzyte1rXv5bktzu+Jr5wNI5ETgTGBKJAHu+V+ZaHdsIPtlTHtHqBQSvYE7eave56G1+ljoLQpHWQie7KJJEPCB3xUrBhxy0uqFT2Ig/HnkEvwiAyl9149l4cSokAkveHXI9A7XR6Cz2KEeNALzaq3SER+mFCzQ3HUycAz2lE620qo/MqPv2grbQ4lq4sVl9trA9gNTFWLhb/k+ikpK7EEpqEJJTrlU47hvXB9uZ6HIp7ZrlSyY1fBRwv2HQe6TG0OqSpMDmwYnF2anyXtbvKi5RC5LSOoSk0WGUfvcEITVbrxn0MU/oPGFRGalKxZdVh7r2DfpBPG/r+So2eSviFbb7LTritaCBA8yu18/R7qgKXPa8zBq3GDypwgaTlqb9P0NMuQU6ckcnRZwW2GnS7RGAkiW5Bs34OdxaWtORLx8PeyS5srKum2+1te9VmWxU2jVZvGC6jM5hZbw/tkJHp6LmBO8nBMGXR4tXe1ief1Ao2ybmKE7bk73alzH13+y+Dw3gj6Up0gaq477BEf8jTrxGFfqISTc7I/wl2UyT9QtMvVVsoWWJqX1T9FLor4nHDgDCGnRltgwDWSONa1/sMYb57aDq7KgsSniQgS85+WeemCWb+Erf12nVDMSuo5KabIyBmHwxJiue90jk5ZVqc+b+s=",
        // "iYml4/SqJf+tvrcpwgO3yw=="));
        // AwmjeyTnwdD7wKW5v1SobhIxcCJDN3GKgL3jzHJYfs2aIi1XkiVwH9CNQr8lmcgC3A/PZrNEQG5VWBu4ex0LHg1/AvGzxirY+2S2PQRXKqIGaKKRQ23Pmi+SvmghqZ3M4/mUxBY7LU06MoJSFC2YQDHdLBsaVGS0T+xek+tsepXVnQ7/FxrgADPOv0GZmmwPlGtMUvnfOmGwkRQBkueOXx3sVDmsrEnRRH3CvbLL5jyxVYSKZwJIhb9gz3LtNvXizEMBsjQSMpv8Xw3oEjk0M4yoV8ICIkjy35yWgTT1EpD0AurUnDduAq+saapmy/XMw8Je1n/ZM7s+tywerVHktTyR9RDboiG458ZtrjAKn1PjR1Vr86MC5ZBZ2T6bsXX+NeWj4uTXWzeLJCnBIpeSJOJ3M8Wd9i0qvwaNVuBBZPBuw+/X7LE2VnZj/AaSQHl7LP4ttFr226cejYtVSb8+RFbw5xku0ybnpPHFlDhx3XHivAhM/fce70EtCJyLK+FpphApVF3HIs0Cj29D3zaeMNrrkgkFtMUWF3FA3bXwlbu2ImAwY6mlXNP8cF0q0iOQB8oMXJkzrZlKbo3EpZQWDV6z34IMmXt5kGDy4p2kAySgl8MLcqRanSPMgOWQ33t/
        // 0Yjmy5zymWx3u4Qyr++4pm7RFmmLXGV6RqBEQFlX/7lpfA0fmCaCyQsP8McF6RfCmFHaWmbFDkoVl3NxQcvjpbYOTrcuUux8F+3lwuzYXZS7qMmehRiXmVkhrCk/prF0AFu9176CS8hIuErHRgZKYJM71WXF5pt/OYAq9caqJK0XTaaWE1VR+rvnOwIblCxyfSF4yffOriKSdYwObL5XzzmRuBGFdMALqWBDRp7jWOHxkSCjhLuRLokU5KIloZpDlP3Xsr8qojpAC+Oz1sWL6YtUQR7g5RR0/5m6qLdxDDxrTYcKMerELuXtk2RRbOM5Edu362JHruJ3OGtUg/xKI6EE6fLGOJ/FH8eGCAkt8jg500R9T2VkxJIbCOCFdb83yyYmiKxZqDEcKms46equ7UBfFs+7zoTjQFNnM6xZ2ZVlEMr5CUvemnaGuTo3EC1q/TEEJ7ZMG9DrBtH0AaXF6gFp+zxFo3w+9y15xR3o/FP3MRXg5Hkzi5ClaS0gHGXlavMLTRKHoeV+JeldQXxRRgmeM5G9Szmp9UbBuGuEFzFMoBIPnTDGFwI7bD1tYKfpRFb3jO25eGqnkG7vFVEqBgF5wO7FD+7e54nZ/p05qSKmQv0HL+z9ELiR7/ywsw2BNfBEjsn9BOwl7zsRNyWBb/KNQPHIUW6d6RngeD++2BxDTrxnNcMPGoAWV4ohrISmAHmi+adSj+Xyuyw1M5XUrdaZUrX8COCLZqjrjTdFZv9Q1/VHU9zu1jcErdjg4qu5XCFWYtPXaP7/ii+nllxPh6Q5LCdiuv21PRBhRJrhBM2wjpaPgim8ZgUYOqKpdyzL+ul5j9IjlRviHdSitjHPW45dER4zw6S1sjLwN7mYBIMV+8Vp8BUBs+hQN1H7NVx08k3dpKgcRQHiVgq7+ixgfLUtbbm4loPGOdxeJrq/50Ii/W+w0mtJ4qeZ/uuUgPwJ/6ohZEn3d78M5k914xZTo+5JJ1b0lFf9frb2q+mzn4qJXe3c6f8hvykH3VG5/i/jnH6Nz1Ex+6lRYptiWI2H1t2zGL8uv0rojzSL4YJyAQ0Rdzz0Qwn0LEeEVNFrrzKAi4F3SPhLiR9UpPqlXq1JS49q11wp9OVIDxTFr0zZW7RuIc0uVeAE43Lxi749OF2ROVT7bfwsYbKqR8KTxKGsTXGmqTLUuzXJzq49sTnhBfQ5zlxi3NZ3/k6ubZ8nWe+M

        String necrypefindPayInfo = DigestUtil.encryptAES(
                "{\"transactionid\":\"id1232\",\"payment\":\"yudsd\",\"begintrans_time\":\"2015-09-09 15:10:38\",\"endtrans_time\":\"2015-09-09 15:10:38\"}",
                "iYml4/SqJf+tvrcpwgO3yw==");
        System.out.println("findPayInfoAPI加密={" + necrypefindPayInfo);
        String necrypefindPayInfos = DigestUtil.encryptAES("{\"payment\":\"yudsd\"}",
                "iYml4/SqJf+tvrcpwgO3yw==");

        System.out.println("findPayInfoAPIs加密={" + necrypefindPayInfos);

        System.out.println("test:" + DigestUtil.decryptAES(
                "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",
                "iYml4/SqJf+tvrcpwgO3yw=="));

    }
}
