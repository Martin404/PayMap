package com.hugnew.sps.services.pay.util.web.unionpay.upop.sdk;

import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.regex.Pattern;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class PinUtils {
    private static final Pattern PIN_PATTERN = Pattern.compile("^\\d{4,12}$");
    private static final Pattern PAN_PATTERN = Pattern.compile("^\\d+$");

    private static String getEncodedPin(String pin) throws Exception {
        if (!PIN_PATTERN.matcher(pin).matches()) {
            throw new Exception("Invalid pin");
        }
        StringBuilder sb = new StringBuilder();
        int length = pin.length();
        sb.append("0").append(Integer.toHexString(length));
        sb.append(pin);
        for (int i = 2 + length; i < 16; i++) {
            sb.append("F");
        }
        return sb.toString();
    }

    private static String getEncodedPan(String pan) throws Exception {
        if (!PAN_PATTERN.matcher(pan).matches()) {
            throw new Exception("Invalid pan");
        }
        int panLength = pan.length();
        int beginIndex = panLength - 13;
        beginIndex = beginIndex > 0 ? beginIndex : 0;
        String subPan = pan.substring(beginIndex, panLength - 1);
        int subPanLength = subPan.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16 - subPanLength; i++) {
            sb.append("0");
        }
        sb.append(subPan);
        return sb.toString();
    }

    private static long getPinBlock(String pin, String pan) throws Exception {
        String encryptedPinStr = getEncodedPin(pin);
        String encryptedPanStr = getEncodedPan(pan);

        long encryptedPin = Long.parseLong(encryptedPinStr, 16);
        long encryptedPan = Long.parseLong(encryptedPanStr, 16);
        long pinBlock = encryptedPin ^ encryptedPan;
        return pinBlock;
    }

    private static byte[] parsePinBlock2Bytes(long number) {
        long temp = number;
        byte[] bytes = new byte[8];
        for (int i = bytes.length - 1; i >= 0; i--) {
            bytes[i] = new Long(temp & 0xff).byteValue();//
            temp = temp >> 8;
        }
        return bytes;
    }

    public static String getEncryptedPin(String pin, String pan, String certFile) throws Exception {
        long pinBlock = getPinBlock(pin, pan);
        byte[] pinBlockBytes = parsePinBlock2Bytes(pinBlock);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream fis = new FileInputStream(certFile);
        Certificate certificate = cf.generateCertificate(fis);
        PublicKey publicKey = certificate.getPublicKey();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(pinBlockBytes);
        String result = Base64.encodeBase64String(encryptedBytes);
        return result;
    }

    public static String getEncryptedPan(String pan, String certFile) throws Exception {

        byte[] panBlockBytes = pan.getBytes();

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream fis = new FileInputStream(certFile);
        Certificate certificate = cf.generateCertificate(fis);
        PublicKey publicKey = certificate.getPublicKey();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(panBlockBytes);
        String result = Base64.encodeBase64String(encryptedBytes);

        return result;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("PIN BLOCK=" + Long.toHexString(getPinBlock("111111", "4160098111110001")));
        System.out.println("Encrypted Pin=" + getEncryptedPin("111111", "4160098111110001", "D:\\test_enc.cer"));
        System.out.println("Encrypted Pan=" + getEncryptedPan("6212341111111111111", "D:\\test_enc.cer"));
    }
}
