package com.flexi.util;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESEncryption {

    private SecretKey secretKey;
    private Cipher cipher;

    public AESEncryption(SecretKey secretKey, String cipher) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.secretKey = secretKey;
        this.cipher = Cipher.getInstance(cipher);
    }

    public AESEncryption(String cipher) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance(cipher);
    }

    public void generateKey() throws NoSuchAlgorithmException {
        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
    }

    public void generateKey(String path) throws NoSuchAlgorithmException, IOException {
        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
        this.saveToFile(path);
    }

    public String encrypt(String content) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        return encrypted.toString();
    }

    public String decrypt(String content) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] originalBytes = cipher.doFinal(content.getBytes());
        return originalBytes.toString();
    }

    public void saveToFile(String path) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(secretKey);
        out.close();
    }

    public void readFromFile(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream( new FileInputStream(path) );
        secretKey = (SecretKey) in.readObject();
    }

}
