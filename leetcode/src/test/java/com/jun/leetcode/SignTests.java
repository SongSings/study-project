package com.jun.leetcode;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignTests {

    /**
     * 调用HMAC SHA256算法，根据开发者提供的密钥（signingKey）和密文（stringToSign）输出密文摘要，并把结果转换为小写形式的十六进制字符串。
     *
     * @param signingKey   签名密钥
     * @param stringToSign 待签名文本
     */
    public static String hmacSha256Hex(String signingKey, String stringToSign)
            throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(signingKey.getBytes("UTF8"), mac.getAlgorithm()));
            return new String(Hex.encodeHex(mac.doFinal(stringToSign.getBytes("UTF8"))));
        } catch (Exception e) {
            System.out.printf("Fail to generate the signature, {}", e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String s = hmacSha256Hex("1111", "448韩国发出8888999999跟好借好还8888888888888888884");
        System.out.println("s = " + s);
        "asd".contains("as");
    }
}
