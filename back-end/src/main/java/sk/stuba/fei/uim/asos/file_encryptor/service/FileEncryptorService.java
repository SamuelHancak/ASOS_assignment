/*
 *    Title: Java AES Encryption and Decryption
 *    Author: baeldung
 *    Date: 2022
 *    Code version: 1.0
 *    Availability: https://www.baeldung.com/java-aes-encryption-decryption
 */
/*
 *    Title: Symmetric Encryption Cryptography in Java
 *    Author: nimma_shravan_kumar_reddy
 *    Date: 2022
 *    Code version: 1.0
 *    Availability: https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/
 */
/*
 *    Title: RSA in Java
 *    Author: Krzysztof Majewski
 *    Date: 2022
 *    Code version: 1.0
 *    Availability: https://www.baeldung.com/java-rsa
 */
package sk.stuba.fei.uim.asos.file_encryptor.service;

import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.asos.file_encryptor.service.interfaces.IFileEncryptorService;
import sk.stuba.fei.uim.asos.file_encryptor.service.utils.ByteUtils;
import sk.stuba.fei.uim.asos.file_encryptor.service.utils.FileContentIntegrityViolatedException;
import sk.stuba.fei.uim.asos.file_encryptor.service.utils.KeyPairString;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
public class FileEncryptorService implements IFileEncryptorService {

    private SecretKey generateSecretKeyAES() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, secureRandom);
        return keyGenerator.generateKey();
    }

    private SecretKey generateSecretKeyDES() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(secureRandom);
        return keyGenerator.generateKey();
    }

    private KeyPair generateKeyPairRSA() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, secureRandom);
        return keyPairGenerator.generateKeyPair();
    }

    private String convertSecretKeyToString(SecretKey key) {
        byte[] byteKey = key.getEncoded();
        return Base64.getEncoder().encodeToString(byteKey);
    }

    private SecretKey convertStringToSecretKeyAES(String key) {
        byte[] byteKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(byteKey, 0, byteKey.length, "AES");
    }

    private SecretKey convertStringToSecretKeyDES(String key) {
        byte[] byteKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(byteKey, 0, byteKey.length, "DES");
    }

    private String convertPrivateKeyToString(PrivateKey key) {
        byte[] byteKey = key.getEncoded();
        return Base64.getEncoder().encodeToString(byteKey);
    }

    private String convertPublicKeyToString(PublicKey key) {
        byte[] byteKey = key.getEncoded();
        return Base64.getEncoder().encodeToString(byteKey);
    }

    private PrivateKey convertStringToPrivateKeyRSA(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] byteKey = Base64.getDecoder().decode(key.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(byteKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private PublicKey convertStringToPublicKeyRSA(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] byteKey = Base64.getDecoder().decode(key.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(byteKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    private byte[] generateMAC(byte[] input, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(this.convertStringToSecretKeyDES(key));
        return mac.doFinal(input);
    }

    private byte[] encryptAES(byte[] input, String key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, this.convertStringToSecretKeyAES(key));
        return cipher.doFinal(input);
    }

    private byte[] decryptAES(byte[] input, String key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, this.convertStringToSecretKeyAES(key));
        return cipher.doFinal(input);
    }

    private byte[] encryptRSA(byte[] input, String publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, this.convertStringToPublicKeyRSA(publicKey));
        return cipher.doFinal(input);
    }

    private byte[] decryptRSA(byte[] input, String privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, this.convertStringToPrivateKeyRSA(privateKey));
        return cipher.doFinal(input);
    }

    @Override
    public String generateSecretKeyStringAES() throws NoSuchAlgorithmException {
        return this.convertSecretKeyToString(this.generateSecretKeyAES());
    }

    @Override
    public String generateSecretKeyStringDES() throws NoSuchAlgorithmException {
        return this.convertSecretKeyToString(this.generateSecretKeyDES());
    }

    @Override
    public KeyPairString generateKeyPairStringRSA() throws NoSuchAlgorithmException {
        return new KeyPairString(this.generateKeyPairRSA());
    }

    @Override
    public byte[] encryptFileAES(byte[] inputFile, String key) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return this.encryptAES(inputFile, key);
    }

    @Override
    public byte[] decryptFileAES(byte[] inputFile, String key) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return this.decryptAES(inputFile, key);
    }

    @Override
    public byte[] encryptFileRSA_MAC_AES(byte[] inputFile, String publicKey) throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        SecretKey secretKeyAES = this.generateSecretKeyAES();
        SecretKey secretKeyDES = this.generateSecretKeyDES();
        byte[] byteMessage = this.encryptAES(inputFile, this.convertSecretKeyToString(secretKeyAES));
        byte[] byteMAC = this.encryptRSA(this.generateMAC(byteMessage, this.convertSecretKeyToString(secretKeyDES)), publicKey);
        byte[] byteKeyAES = this.encryptRSA(this.convertSecretKeyToString(secretKeyAES).getBytes(), publicKey);
        byte[] byteKeyDES = this.encryptRSA(this.convertSecretKeyToString(secretKeyDES).getBytes(), publicKey);
        byte[] outputFile = ByteUtils.byteConcat(byteKeyAES, "_____".getBytes() ,byteKeyDES, "_____".getBytes(), byteMAC, "_____".getBytes(), byteMessage);
        return outputFile;
    }

    @Override
    public byte[] decryptFileRSA_MAC_AES(byte[] inputFile, String privateKey) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException, FileContentIntegrityViolatedException {
        List<byte[]> splitted = ByteUtils.byteSplit("_____".getBytes(), inputFile);
        byte[] byteKeyAES = this.decryptRSA(splitted.get(0), privateKey);
        byte[] byteKeyDES = this.decryptRSA(splitted.get(1), privateKey);
        byte[] byteMAC = this.decryptRSA(splitted.get(2), privateKey);
        byte[] byteMessage = this.decryptAES(splitted.get(3), new String(byteKeyAES));
        byte[] currentByteMAC = this.generateMAC(splitted.get(3), new String(byteKeyDES));
        if (!Arrays.equals(byteMAC, currentByteMAC)) {
            throw new FileContentIntegrityViolatedException();
        }
        return byteMessage;
    }

}
