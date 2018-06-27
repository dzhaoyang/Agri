package com.sunsea.parkinghere.framework.utils;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.sunsea.parkinghere.exception.ValidationException;

public class PasswordUtils {
    
    public static final String MD5_PASSWORD_ENCODER = "MD5";
    
    private static Random ranGen = new SecureRandom();
    
    private static Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
    
    // private static ShaPasswordEncoder sha1PasswordEncoder = new
    // ShaPasswordEncoder();
    
    public static String generatePasswordSalt() {
        byte[] aesKey = new byte[16];
        ranGen.nextBytes(aesKey);
        String salt = new String(Base64.encodeBase64(aesKey));
        salt = salt.replace("\r", "");
        salt = salt.replace("\n", "");
        return salt;
    }
    
    public static String generatedSaltedPasswordHash(String password,
                                                     String salt,
                                                     String passwordEncoderName) {
        if (StringUtils.isEmpty(passwordEncoderName)) {
            return md5PasswordEncoder.encodePassword(password, salt);
        }
        if (MD5_PASSWORD_ENCODER.equalsIgnoreCase(passwordEncoderName)) {
            return md5PasswordEncoder.encodePassword(password, salt);
        }
        // if (User.SHA1_PASSWORD_ENCODER.equalsIgnoreCase(passwordEncoderName))
        // {
        // return sha1PasswordEncoder.encodePassword(password, salt);
        // }
        throw new ValidationException(String.format("Unsupported password encoder '%s'",
                                                    passwordEncoderName));
    }
    
}
