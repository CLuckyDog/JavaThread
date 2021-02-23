/*
 * MD5 - Decompiled by JODE Visit http://jode.sourceforge.net/
 */
package com.rh.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 * <b>版权：</b>Copyright (c) 2013 亚信联创.<br>
 * <b>工程：</b>MyTest<br>
 * <b>文件：</b>MD5Util.java<br>
 * <b>创建人：</b> gaojian3<br>
 * <b>创建时间：</b>2013-9-5 下午4:59:55<br>
 * <p>
 * <b>TODO(一句话功能简述).</b><br>
 * TODO(MD5加密).<br>
 * </p>
 *
 * @author gaojian3
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MD5Util {

    private static byte[] padding = { -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0};

    private InputStream in = null;

    private boolean stringp = false;

    private int[] state = null;

    private long count = 0L;

    private byte[] buffer = null;

    private byte[] digest = null;

    private static String stringify(byte[] buf) {
        StringBuffer sb = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++ ) {
            int h = (buf[i] & 0xf0) >> 4;
            int l = buf[i] & 0xf;
            sb.append(new Character((char) (h > 9 ? 97 + h - 10 : 48 + h)));
            sb.append(new Character((char) (l > 9 ? 97 + l - 10 : 48 + l)));
        }
        return sb.toString();
    }

    private final int F(int x, int y, int z) {
        return x & y | (x ^ 0xffffffff) & z;
    }

    private final int G(int x, int y, int z) {
        return x & z | y & (z ^ 0xffffffff);
    }

    private final int H(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private final int I(int x, int y, int z) {
        return y ^ (x | z ^ 0xffffffff);
    }

    private final int rotate_left(int x, int n) {
        return x << n | x >>> 32 - n;
    }

    private final int FF(int a, int b, int c, int d, int x, int s, int ac) {
        a += F(b, c, d) + x + ac;
        a = rotate_left(a, s);
        a += b;
        return a;
    }

    private final int GG(int a, int b, int c, int d, int x, int s, int ac) {
        a += G(b, c, d) + x + ac;
        a = rotate_left(a, s);
        a += b;
        return a;
    }

    private final int HH(int a, int b, int c, int d, int x, int s, int ac) {
        a += H(b, c, d) + x + ac;
        a = rotate_left(a, s);
        a += b;
        return a;
    }

    private final int II(int a, int b, int c, int d, int x, int s, int ac) {
        a += I(b, c, d) + x + ac;
        a = rotate_left(a, s);
        a += b;
        return a;
    }

    private final void decode(int[] output, byte[] input, int off, int len) {
        int i = 0;
        for (int j = 0; j < len; j += 4) {
            output[i] = (input[off + j] & 0xff
                         | (input[off + j + 1] & 0xff) << 8
                         | (input[off + j + 2] & 0xff) << 16 | (input[off + j
                                                                      + 3] & 0xff) << 24);
            i++ ;
        }
    }

    private final void transform(byte[] block, int offset) {
        int a = state[0];
        int b = state[1];
        int c = state[2];
        int d = state[3];
        int[] x = new int[16];
        decode(x, block, offset, 64);
        a = FF(a, b, c, d, x[0], 7, -680876936);
        d = FF(d, a, b, c, x[1], 12, -389564586);
        c = FF(c, d, a, b, x[2], 17, 606105819);
        b = FF(b, c, d, a, x[3], 22, -1044525330);
        a = FF(a, b, c, d, x[4], 7, -176418897);
        d = FF(d, a, b, c, x[5], 12, 1200080426);
        c = FF(c, d, a, b, x[6], 17, -1473231341);
        b = FF(b, c, d, a, x[7], 22, -45705983);
        a = FF(a, b, c, d, x[8], 7, 1770035416);
        d = FF(d, a, b, c, x[9], 12, -1958414417);
        c = FF(c, d, a, b, x[10], 17, -42063);
        b = FF(b, c, d, a, x[11], 22, -1990404162);
        a = FF(a, b, c, d, x[12], 7, 1804603682);
        d = FF(d, a, b, c, x[13], 12, -40341101);
        c = FF(c, d, a, b, x[14], 17, -1502002290);
        b = FF(b, c, d, a, x[15], 22, 1236535329);
        a = GG(a, b, c, d, x[1], 5, -165796510);
        d = GG(d, a, b, c, x[6], 9, -1069501632);
        c = GG(c, d, a, b, x[11], 14, 643717713);
        b = GG(b, c, d, a, x[0], 20, -373897302);
        a = GG(a, b, c, d, x[5], 5, -701558691);
        d = GG(d, a, b, c, x[10], 9, 38016083);
        c = GG(c, d, a, b, x[15], 14, -660478335);
        b = GG(b, c, d, a, x[4], 20, -405537848);
        a = GG(a, b, c, d, x[9], 5, 568446438);
        d = GG(d, a, b, c, x[14], 9, -1019803690);
        c = GG(c, d, a, b, x[3], 14, -187363961);
        b = GG(b, c, d, a, x[8], 20, 1163531501);
        a = GG(a, b, c, d, x[13], 5, -1444681467);
        d = GG(d, a, b, c, x[2], 9, -51403784);
        c = GG(c, d, a, b, x[7], 14, 1735328473);
        b = GG(b, c, d, a, x[12], 20, -1926607734);
        a = HH(a, b, c, d, x[5], 4, -378558);
        d = HH(d, a, b, c, x[8], 11, -2022574463);
        c = HH(c, d, a, b, x[11], 16, 1839030562);
        b = HH(b, c, d, a, x[14], 23, -35309556);
        a = HH(a, b, c, d, x[1], 4, -1530992060);
        d = HH(d, a, b, c, x[4], 11, 1272893353);
        c = HH(c, d, a, b, x[7], 16, -155497632);
        b = HH(b, c, d, a, x[10], 23, -1094730640);
        a = HH(a, b, c, d, x[13], 4, 681279174);
        d = HH(d, a, b, c, x[0], 11, -358537222);
        c = HH(c, d, a, b, x[3], 16, -722521979);
        b = HH(b, c, d, a, x[6], 23, 76029189);
        a = HH(a, b, c, d, x[9], 4, -640364487);
        d = HH(d, a, b, c, x[12], 11, -421815835);
        c = HH(c, d, a, b, x[15], 16, 530742520);
        b = HH(b, c, d, a, x[2], 23, -995338651);
        a = II(a, b, c, d, x[0], 6, -198630844);
        d = II(d, a, b, c, x[7], 10, 1126891415);
        c = II(c, d, a, b, x[14], 15, -1416354905);
        b = II(b, c, d, a, x[5], 21, -57434055);
        a = II(a, b, c, d, x[12], 6, 1700485571);
        d = II(d, a, b, c, x[3], 10, -1894986606);
        c = II(c, d, a, b, x[10], 15, -1051523);
        b = II(b, c, d, a, x[1], 21, -2054922799);
        a = II(a, b, c, d, x[8], 6, 1873313359);
        d = II(d, a, b, c, x[15], 10, -30611744);
        c = II(c, d, a, b, x[6], 15, -1560198380);
        b = II(b, c, d, a, x[13], 21, 1309151649);
        a = II(a, b, c, d, x[4], 6, -145523070);
        d = II(d, a, b, c, x[11], 10, -1120210379);
        c = II(c, d, a, b, x[2], 15, 718787259);
        b = II(b, c, d, a, x[9], 21, -343485551);
        state[0] += a;
        state[1] += b;
        state[2] += c;
        state[3] += d;
    }

    private final void update(byte[] input, int len) {
        int index = (int) (count >> 3) & 0x3f;
        count += (long) (len << 3);
        int partLen = 64 - index;
        int i = 0;
        if (len >= partLen) {
            System.arraycopy(input, 0, buffer, index, partLen);
            transform(buffer, 0);
            for (i = partLen; i + 63 < len; i += 64)
                transform(input, i);
            index = 0;
        } else
            i = 0;
        System.arraycopy(input, i, buffer, index, len - i);
    }

    private byte[] end() {
        byte[] bits = new byte[8];
        for (int i = 0; i < 8; i++ )
            bits[i] = (byte)(int) (count >>> i * 8 & 0xffL);
        int index = (int) (count >> 3) & 0x3f;
        int padlen = index < 56 ? 56 - index : 120 - index;
        update(padding, padlen);
        update(bits, 8);
        return encode(state, 16);
    }

    private byte[] encode(int[] input, int len) {
        byte[] output = new byte[len];
        int i = 0;
        for (int j = 0; j < len; j += 4) {
            output[j] = (byte) (input[i] & 0xff);
            output[j + 1] = (byte) (input[i] >> 8 & 0xff);
            output[j + 2] = (byte) (input[i] >> 16 & 0xff);
            output[j + 3] = (byte) (input[i] >> 24 & 0xff);
            i++ ;
        }
        return output;
    }

    public byte[] getDigest()
        throws IOException {
        byte[] buffer = new byte[1024];
        int got = -1;
        if (digest != null)
            return digest;
        while ( (got = in.read(buffer)) > 0)
            update(buffer, got);
        digest = end();
        return digest;
    }

    public byte[] processString() {
        if ( !stringp)
            throw new RuntimeException(this.getClass().getName()
                                       + "[processString]" + " not a string.");
        try {
            return getDigest();
        } catch (IOException ioexception) {
            throw new RuntimeException(this.getClass().getName()
                                       + "[processString]"
                                       + ": implementation error.");
        }
    }

    public String getStringDigest() {
        if (digest == null)
            try {
                getDigest();
            } catch (IOException e) {
                throw new RuntimeException(this.getClass().getName()
                    + "[processString]"
                    + ": implementation error.");
            }
        return stringify(digest);
    }

    public MD5Util(String input, String enc) {
        byte[] bytes = null;
        try {
            bytes = input.getBytes(enc);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("no " + enc + " encoding!!!");
        }
        stringp = true;
        in = new ByteArrayInputStream(bytes);
        state = new int[4];
        buffer = new byte[64];
        count = 0L;
        state[0] = 1732584193;
        state[1] = -271733879;
        state[2] = -1732584194;
        state[3] = 271733878;
    }

    public MD5Util(String input) {
        this(input, "UTF8");
    }

    public MD5Util(InputStream in) {
        stringp = false;
        this.in = in;
        state = new int[4];
        buffer = new byte[64];
        count = 0L;
        state[0] = 1732584193;
        state[1] = -271733879; 
        state[2] = -1732584194;
        state[3] = 271733878;
    }
    
    public static String getMD5Abstract(String msg, String key){
    	MD5Util md5 = new MD5Util( msg + "$" + key);
    	return md5.getStringDigest();
    }
    

    public static void main(String[] args)
        throws IOException {
    	
    	//密钥
    	String key = "1234";
    	//待加密串
    	String m = "transactionID=sp000001678943065000001&result=0";
    	
        MD5Util md5 = new MD5Util( m + "$" + key);
        System.out.println(md5.getStringDigest());
    }
    
    public static String getMD5Sign(String msg){
    	MD5Util md5 = new MD5Util(msg);
    	return md5.getStringDigest();
    }
}
