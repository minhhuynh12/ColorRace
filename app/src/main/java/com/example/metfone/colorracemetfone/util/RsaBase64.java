package com.example.metfone.colorracemetfone.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import android.util.Base64;
import android.util.Log;

import com.example.metfone.colorracemetfone.R;

import org.apache.commons.codec.DecoderException;

public class RsaBase64 {

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgFGVfrY4jQSoZQWWygZ83roKXWD4YeT2x2p41dGkPixe73rT2IW04glagN2vgoZoHuOPqa5and6kAmK2ujmCHu6D1auJhE2tXP+yLkpSiYMQucDKmCsWMnW9XlC5K7OSL77TXXcfvTvyZcjObEz6LIBRzs6+FqpFbUO9SJEfh6wIDAQAB";
    private static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKAUZV+tjiNBKhlBZbKBnzeugpdYPhh5PbHanjV0aQ+LF7vetPYhbTiCVqA3a+Chmge44+prlqd3qQCYra6OYIe7oPVq4mETa1c/7IuSlKJgxC5wMqYKxYydb1eULkrs5IvvtNddx+9O/JlyM5sTPosgFHOzr4WqkVtQ71IkR+HrAgMBAAECgYAkQLo8kteP0GAyXAcmCAkA2Tql/8wASuTX9ITD4lsws/VqDKO64hMUKyBnJGX/91kkypCDNF5oCsdxZSJgV8owViYWZPnbvEcNqLtqgs7nj1UHuX9S5yYIPGN/mHL6OJJ7sosOd6rqdpg6JRRkAKUV+tmN/7Gh0+GFXM+ug6mgwQJBAO9/+CWpCAVoGxCA+YsTMb82fTOmGYMkZOAfQsvIV2v6DC8eJrSa+c0yCOTa3tirlCkhBfB08f8U2iEPS+Gu3bECQQCrG7O0gYmFL2RX1O+37ovyyHTbst4s4xbLW4jLzbSoimL235lCdIC+fllEEP96wPAiqo6dzmdH8KsGmVozsVRbAkB0ME8AZjp/9Pt8TDXD5LHzo8mlruUdnCBcIo5TMoRG2+3hRe1dHPonNCjgbdZCoyqjsWOiPfnQ2Brigvs7J4xhAkBGRiZUKC92x7QKbqXVgN9xYuq7oIanIM0nz/wq190uq0dh5Qtow7hshC/dSK3kmIEHe8z++tpoLWvQVgM538apAkBoSNfaTkDZhFavuiVl6L8cWCoDcJBItip8wKQhXwHp0O3HLg10OEd14M58ooNfpgt+8D8/8/2OOFaR0HzA+2Dm";
    private static final String ENCRYPT_PADDING = "RSA/ECB/PKCS1Padding";
    private static final String ALGORITHM_RSA = "RSA";

    public static PublicKey getPublicKey(String base64PublicKey) {
        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(org.apache.commons.codec.binary.Hex.decodeHex(base64PublicKey.toCharArray()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }


    public static PrivateKey getPrivateKey(String base64PrivateKey) {
        PrivateKey privateKey = null;
        try {

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(org.apache.commons.codec.binary.Hex.decodeHex(base64PrivateKey.toCharArray()));
            KeyFactory keyFactory = null;

            keyFactory = KeyFactory.getInstance("RSA");

            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }


    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }


    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, DecoderException {
        return decrypt(org.apache.commons.codec.binary.Hex.decodeHex(data.toCharArray()), getPrivateKey(base64PrivateKey));
    }

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        try {
            String encryptedString = "";
            String decryptedString = RsaBase64.decrypt(encryptedString, privateKey);
            System.out.println(decryptedString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public static String Decryptt(String dataEncrypted, String privateKey)
            throws Exception {
        Boolean isFile = false;
        RSAPrivateKey _privateKey = LoadPrivateKey(privateKey, isFile);
        Cipher cipher = Cipher.getInstance(ENCRYPT_PADDING);

        cipher.init(2, _privateKey);

        dataEncrypted = dataEncrypted.replace("\r", "");
        dataEncrypted = dataEncrypted.replace("\n", "");
        int dwKeySize = _privateKey.getModulus().bitLength();
        int base64BlockSize = dwKeySize / 8 % 3 != 0 ? dwKeySize / 8 / 3 * 4 + 4 : dwKeySize / 8 / 3 * 4;

        int iterations = dataEncrypted.length() / base64BlockSize;
        ByteBuffer bb = ByteBuffer.allocate(100000);
        for (int i = 0; i < iterations; i++) {
            String sTemp = dataEncrypted.substring(base64BlockSize * i, base64BlockSize * i + base64BlockSize);

            byte[] bTemp = decodeBase64(sTemp);

            bTemp = reverse(bTemp);
            byte[] encryptedBytes = cipher.doFinal(bTemp);
            bb.put(encryptedBytes);
        }
        byte[] bDecrypted = bb.array();
        return new String(bDecrypted).trim();
    }

    private static RSAPrivateKey LoadPrivateKey(String key, Boolean isFile)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String sReadFile = "";
        if (isFile.booleanValue()) {
            File file = new File(key);
            sReadFile = fullyReadFile(file);
        } else {
            sReadFile = key.trim();
        }
        if ((sReadFile.startsWith("-----BEGIN PRIVATE KEY-----")) && (sReadFile.endsWith("-----END PRIVATE KEY-----"))) {
            sReadFile = sReadFile.replace("-----BEGIN PRIVATE KEY-----", "");
            sReadFile = sReadFile.replace("-----END PRIVATE KEY-----", "");
            sReadFile = sReadFile.replace("\n", "");
            sReadFile = sReadFile.replace("\r", "");
            sReadFile = sReadFile.replace(" ", "");
        }
        byte[] b = decodeBase64(sReadFile);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);

        KeyFactory factory = KeyFactory.getInstance(ALGORITHM_RSA);
        return (RSAPrivateKey) factory.generatePrivate(spec);
    }

    private static byte[] decodeBase64(String dataToDecode) {
//        BASE64Decoder b64d = new BASE64Decoder();
        byte[] bDecoded = (byte[]) null;
        //            bDecoded = b64d.decodeBuffer(dataToDecode);
        bDecoded = Base64.decode(dataToDecode, Base64.DEFAULT);
        return bDecoded;
    }

    private static byte[] reverse(byte[] b) {
        int left = 0;
        int right = b.length - 1;
        while (left < right) {
            byte temp = b[left];
            b[left] = b[right];
            b[right] = temp;

            left++;
            right--;
        }
        return b;
    }
    private static String fullyReadFile(File file)
            throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        byte[] bytesOfFile = new byte[(int) file.length()];
        dis.readFully(bytesOfFile);
        dis.close();
        String sRead = new String(bytesOfFile);
        return sRead.trim();
    }


    KeyPairGenerator kpg;
    KeyPair kp;

    byte [] encryptedBytes,decryptedBytes;
    Cipher cipher,cipher1;
    String encrypted,decrypted;
    PrivateKey privateKey1;

    public  String RSADecrypt(final byte[] encryptedBytes) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        cipher1 = Cipher.getInstance("RSA");
        Key key = null;

        KeyFactory keyFac = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey.trim().getBytes(), Base64.DEFAULT));
        try {
            key = keyFac.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

//        privateKey1 = getPrivateKey(privateKey);

        cipher1.init(Cipher.DECRYPT_MODE, key);
        decryptedBytes = cipher1.doFinal(encryptedBytes);
        decrypted = new String(decryptedBytes);
        System.out.println("DDecrypted?????" + decrypted);
        return decrypted;
    }

    public static String decryptM(byte [] ciphertext) throws Exception {
        KeyStore keyStore = getKeyStore();
        PrivateKey privateKey = (PrivateKey) keyStore.getKey("colorRace",
                "Metfone@123".toCharArray());
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPwithSHA1andMGF1Padding","BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] cipherbyte=cipher.doFinal(ciphertext);
        return new String(cipherbyte);
    }

    public static KeyStore getKeyStore() throws Exception {
        String file ="keyStoreColorRace.jks";
        KeyStore keyStore = KeyStore
                .getInstance("JKS");
        String password = "Metfone@123";
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            keyStore.load(in, password.toCharArray());
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return keyStore;
    }
}