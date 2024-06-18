package com.backend.tlg.depgirpro.services.auth;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;
import java.util.Base64;

@Service
public class CryptoService {

    @Autowired
    public CryptoService(BouncyCastleProvider bouncyCastleProvider) {
        Security.addProvider(bouncyCastleProvider);
    }


    public String encrypt(String plaintext, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
        Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String ciphertext, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
        Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes);
    }
}
