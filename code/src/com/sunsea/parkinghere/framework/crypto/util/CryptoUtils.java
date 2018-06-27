package com.sunsea.parkinghere.framework.crypto.util;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.codec.Base64;

import com.sunsea.parkinghere.Configuration;
import com.sunsea.parkinghere.framework.crypto.CryptoException;
import com.sunsea.parkinghere.framework.crypto.CryptoProvider;
import com.sunsea.parkinghere.framework.crypto.CryptoProviderFactory;

public class CryptoUtils {
    
    private static final String salt;
    
    static {
        salt = Configuration.getInstance()
                            .getProperty(CryptoProvider.class.getName() + "#salt",
                                         CryptoProvider.class.getName());
    }
    
    /**
     * @param message
     * @param dn
     * @return
     */
    public static byte[] encrypt(String message, String dn) {
        try {
            if (StringUtils.isEmpty(message)) {
                return ArrayUtils.EMPTY_BYTE_ARRAY;
            }
            return CryptoProviderFactory.getProvider()
                                        .getEncryptor()
                                        .encrypt(message, dn, salt);
        }
        catch (Exception e) {
            throw new CryptoException(CryptoProviderFactory.getProvider()
                                                           .getAlgorithm() + " encrypt failed!",
                                      e);
        }
    }
    
    /**
     * @param encrypted
     * @param dn
     * @return
     */
    public static String decrypt(byte[] encrypted, String dn) {
        try {
            if (encrypted == null || encrypted.length == 0) {
                return null;
            }
            return CryptoProviderFactory.getProvider()
                                        .getDecryptor()
                                        .decrypt(encrypted, dn, salt);
        }
        catch (Exception e) {
            throw new CryptoException(CryptoProviderFactory.getProvider()
                                                           .getAlgorithm() + " decrypt failed!",
                                      e);
        }
    }
    
    /**
     * @param text
     * @return
     */
    public static String md5hash(String text) {
        String result = null;
        try {
            byte[] valueByte = text.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(valueByte);
            result = toHex(md.digest());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * @param buffer
     * @return
     */
    private static String toHex(byte[] buffer) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
        }
        return sb.toString();
    }
    
    /**
     * @param plainBytes
     * @param keyInBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     */
    public static byte[] encryptDES(byte[] plainBytes, byte[] keyInBytes) throws NoSuchAlgorithmException,
                                                                         NoSuchPaddingException,
                                                                         InvalidKeyException,
                                                                         IllegalBlockSizeException,
                                                                         BadPaddingException,
                                                                         InvalidKeySpecException {
        SecretKeySpec desKeySpec = new SecretKeySpec(keyInBytes, "DES");
        SecretKey secretKey = SecretKeyFactory.getInstance("DES")
                                              .generateSecret(desKeySpec);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        // byte[] result = new byte[] {};
        // for (int i = 0; i < plainBytes.length; i += 117) {
        // byte[] subarray = ArrayUtils.subarray(plainBytes, i, i + 117);
        // byte[] doFinal = cipher.doFinal(subarray);
        // result = ArrayUtils.addAll(result, doFinal);
        // }
        // return result;
        return cipher.doFinal(plainBytes);
    }
    
    /**
     * @param plainBytes
     * @param keyInBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     */
    public static byte[] decryptDES(byte[] plainBytes, byte[] keyInBytes) throws NoSuchAlgorithmException,
                                                                         NoSuchPaddingException,
                                                                         InvalidKeyException,
                                                                         IllegalBlockSizeException,
                                                                         BadPaddingException,
                                                                         InvalidKeySpecException {
        SecretKeySpec aesKeySpec = new SecretKeySpec(keyInBytes, "DES");
        SecretKey secretKey = SecretKeyFactory.getInstance("DES")
                                              .generateSecret(aesKeySpec);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        // byte[] result = new byte[] {};
        // for (int i = 0; i < plainBytes.length; i += 117) {
        // byte[] subarray = ArrayUtils.subarray(plainBytes, i, i + 117);
        // byte[] doFinal = cipher.doFinal(subarray);
        // result = ArrayUtils.addAll(result, doFinal);
        // }
        // return result;
        return cipher.doFinal(plainBytes);
    }
    
    /**
     * @param plainBytes
     * @param keyInBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     */
    public static byte[] encryptAES(byte[] plainBytes, byte[] keyInBytes) throws NoSuchAlgorithmException,
                                                                         NoSuchPaddingException,
                                                                         InvalidKeyException,
                                                                         IllegalBlockSizeException,
                                                                         BadPaddingException,
                                                                         InvalidKeySpecException {
        SecretKeySpec aesKeySpec = new SecretKeySpec(keyInBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKeySpec);
        // byte[] result = new byte[] {};
        // for (int i = 0; i < plainBytes.length; i += 117) {
        // byte[] subarray = ArrayUtils.subarray(plainBytes, i, i + 117);
        // byte[] doFinal = cipher.doFinal(subarray);
        // result = ArrayUtils.addAll(result, doFinal);
        // }
        // return result;
        return cipher.doFinal(plainBytes);
    }
    
    /**
     * @param plainBytes
     *            the length must be 16
     * @param keyInBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     */
    public static byte[] decryptAES(byte[] plainBytes, byte[] keyInBytes) throws NoSuchAlgorithmException,
                                                                         NoSuchPaddingException,
                                                                         InvalidKeyException,
                                                                         IllegalBlockSizeException,
                                                                         BadPaddingException,
                                                                         InvalidKeySpecException {
        SecretKeySpec aesKeySpec = new SecretKeySpec(keyInBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, aesKeySpec);
        // byte[] result = new byte[] {};
        // for (int i = 0; i < plainBytes.length; i += 117) {
        // byte[] subarray = ArrayUtils.subarray(plainBytes, i, i + 117);
        // byte[] doFinal = cipher.doFinal(subarray);
        // result = ArrayUtils.addAll(result, doFinal);
        // }
        // return result;
        return cipher.doFinal(plainBytes);
    }
    
    /**
     * @param plainBytes
     * @param publicKeyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encryptRSAByPublicKey(byte[] plainBytes,
                                               byte[] publicKeyBytes) throws NoSuchAlgorithmException,
                                                                     NoSuchPaddingException,
                                                                     InvalidKeySpecException,
                                                                     InvalidKeyException,
                                                                     IllegalBlockSizeException,
                                                                     BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        PublicKey publicKey = KeyFactory.getInstance("RSA")
                                        .generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = new byte[] {};
        for (int i = 0; i < plainBytes.length; i += 117) {
            byte[] subarray = ArrayUtils.subarray(plainBytes, i, i + 117);
            byte[] doFinal = cipher.doFinal(subarray);
            result = ArrayUtils.addAll(result, doFinal);
        }
        return result;
    }
    
    /**
     * @param plainBytes
     * @param privateKeyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encryptRSAByPrivateKey(byte[] plainBytes,
                                                byte[] privateKeyBytes) throws NoSuchAlgorithmException,
                                                                       NoSuchPaddingException,
                                                                       InvalidKeySpecException,
                                                                       InvalidKeyException,
                                                                       IllegalBlockSizeException,
                                                                       BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        PKCS8EncodedKeySpec pkcs8Spec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = KeyFactory.getInstance("RSA")
                                          .generatePrivate(pkcs8Spec);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = new byte[] {};
        for (int i = 0; i < plainBytes.length; i += 117) {
            byte[] subarray = ArrayUtils.subarray(plainBytes, i, i + 117);
            byte[] doFinal = cipher.doFinal(subarray);
            result = ArrayUtils.addAll(result, doFinal);
        }
        return result;
    }
    
    /**
     * @param encryptedBytes
     * @param privateKeyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decryptRSAByPrivateKey(byte[] encryptedBytes,
                                                byte[] privateKeyBytes) throws NoSuchAlgorithmException,
                                                                       NoSuchPaddingException,
                                                                       InvalidKeySpecException,
                                                                       InvalidKeyException,
                                                                       IllegalBlockSizeException,
                                                                       BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        PKCS8EncodedKeySpec pkcs8Spec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = KeyFactory.getInstance("RSA")
                                          .generatePrivate(pkcs8Spec);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        byte[] result = new byte[] {};
        for (int i = 0; i < encryptedBytes.length; i += 128) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(encryptedBytes,
                                                                i,
                                                                i + 128));
            result = ArrayUtils.addAll(result, doFinal);
        }
        return result;
    }
    
    /**
     * @param encryptedBytes
     * @param publicKeyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decryptRSAByPublicKey(byte[] encryptedBytes,
                                               byte[] publicKeyBytes) throws NoSuchAlgorithmException,
                                                                     NoSuchPaddingException,
                                                                     InvalidKeySpecException,
                                                                     InvalidKeyException,
                                                                     IllegalBlockSizeException,
                                                                     BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        PublicKey publicKey = KeyFactory.getInstance("RSA")
                                        .generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        
        byte[] result = new byte[] {};
        for (int i = 0; i < encryptedBytes.length; i += 128) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(encryptedBytes,
                                                                i,
                                                                i + 128));
            result = ArrayUtils.addAll(result, doFinal);
        }
        return result;
    }
    
    public static void main(String[] args) throws Exception {
        String plainText = "public static byte[] decryptDES(byte[] plainBytes, byte[] keyInBytes) throws NoSuchAlgorithmException,public static byte[] decryptDES(byte[] plainBytes, byte[] keyInBytes) throws NoSuchAlgorithmException,";
        String key = "abcdefghijklmn4X";
        byte[] encyrptedbytes = encryptDES(plainText.getBytes(), key.getBytes());
        System.out.println(new String(Base64.encode(encyrptedbytes)));
        
        byte[] decryptedbytes = decryptDES(encyrptedbytes, key.getBytes());
        System.out.println(new String(decryptedbytes));
    }
    
}
