package com.hugnew.sps.services.pay.util.app.wechat.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

public class AesUtils {

    public static final String ALGORITHM = "AES";
    private static final String DEFAULT_KEY = "ECPB2CABCDEFGHIJ";  //16位

    @SuppressWarnings("restriction")
    public static Key getKey(String strKey) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        while (strKey.length() < 16) {
            strKey = strKey + "0";
        }
        return getKey(strKey.getBytes());
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为16位 不足16位时后面补0，超出16位只取前16位
     *
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws Exception
     */
    private static Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的16位字节数组（默认值为0）
        byte[] arrB = new byte[16];
        // 将原始字节数组转换为16位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // 生成密钥

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(arrB));
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();

        SecretKeySpec key;
        key = new SecretKeySpec(arrB, "AES");
        return key;
    }

    /**
     * 根据密匙进行AES加密
     *
     * @param keyString  密匙
     * @param srcString 要加密的信息
     * @return String 加密后的信息
     */
    public static String encrypt(String keyString, String srcString) throws Exception {
        // 定义要生成的密文   
        byte[] cipherByte = null;
        Key key = getKey(keyString);
        // 得到加密/解密器   
        Cipher c1 = Cipher.getInstance("AES");
        // 用指定的密钥和模式初始化Cipher对象   
        // 参数:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)  
        c1.init(Cipher.ENCRYPT_MODE, key);
        // 对要加密的内容进行编码处理,   
        cipherByte = c1.doFinal(srcString.getBytes());

        // 返回密文的十六进制形式   
        return byte2hex(cipherByte);
    }

    public static String defaultEncrypt(String srcString) throws Exception {
        return encrypt(DEFAULT_KEY, srcString);
    }


    /**
     * 根据密匙进行AES解密
     *
     * @param keyString   密匙
     * @param encryptedString 要解密的密文
     * @return String 返回解密后信息
     */
    public static String decrypt(String keyString, String encryptedString) throws Exception {
        Key key = getKey(keyString);
        byte[] cipherByte = null;
        // 得到加密/解密器   
        Cipher c1 = Cipher.getInstance("AES");
        // 用指定的密钥和模式初始化Cipher对象   
        c1.init(Cipher.DECRYPT_MODE, key);
        // 对要解密的内容进行编码处理   
        cipherByte = c1.doFinal(hex2byte(encryptedString));

        // return byte2hex(cipherByte);   
        return new String(cipherByte, "gbk");
    }


    public static String defaultDecrypt(String encryptedString) throws Exception {
        return decrypt(DEFAULT_KEY, encryptedString);
    }


    /**
     * 将二进制转化为16进制字符串
     *
     * @param b 二进制字节数组
     * @return String
     */
    public static String byte2hex(byte[] b) {

        if (null == b) {
            return null;
        }

        StringBuffer hs = new StringBuffer("");
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }

    /**
     * 十六进制字符串转化为2进制
     *
     * @param hex
     * @return
     */
    public static byte[] hex2byte(String hex) throws Exception {
        if (null == hex || hex.length() % 2 != 0) {
            System.out.println("[ERROR]DESUtil:string to hex is null or invalid length!");
            throw new Exception();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

    /*
     * 示例
     */
    public static void main(String[] args) {
        String key = "ABCDEFGHIJKLMNOP";
        String srcString = "asdjfasjdkfjsldjflsjdkfs";
        try {
            //加密
            String encryptedString = encrypt(key, srcString);
            //解密
            String decryptedString = decrypt(key, encryptedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}