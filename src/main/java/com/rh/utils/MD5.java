/*****************************************************************************
 *
 *                      HOPERUN PROPRIETARY INFORMATION
 *
 *          The information contained herein is proprietary to HopeRun
 *           and shall not be reproduced or disclosed in whole or in part
 *                    or used for any design or manufacture
 *              without direct written authorization from HopeRun.
 *
 *            Copyright (coffee) 2016 by HopeRun.  All rights reserved.
 *
 *****************************************************************************/
package com.rh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * ClassName: MD5
 * 
 * @description
 * @author zhu_l
 * @Date Apr 19, 2017
 * 
 */

public class MD5 {

	private static Logger logger = LoggerFactory.getLogger(MD5.class);

	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	public MD5() {
	}

	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	/**
	 * MD5 加密
	 * 
	 * @param strObj
	 *            加密字符串
	 * @return
	 */
	public static String digest(String strObj) {
		String resultString = null;
		try {
			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			logger.error("digest:", ex);
		}
		return resultString;
	}

	/**
	 * 对文件做MD5 加密
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {

		if (!file.isFile()) {
			return null;
		}

		MessageDigest messageDigest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[8192];
		int len;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer)) != -1) {
				messageDigest.update(buffer, 0, len);
			}
			BigInteger bigInt = new BigInteger(1, messageDigest.digest());
			return bigInt.toString(16);

		} catch (Exception e) {
			logger.error("getFileMD5异常", e);
			return null;
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (Exception e) {
				logger.error("getFileMD5异常", e);
			}
		}
	}
	
	public static void main(String[] args) {
	    long time=System.currentTimeMillis();
		String kioskSignature= MD5.digest("xxx" + time + "pgMPlMmrsB8nulj7KtyQOdPnrhWdbeUp");
		System.out.println("System.currentTimeMillis():"+time);
		System.out.println("kioskSignature:"+kioskSignature);
	}

}
