package sk.stuba.fei.uim.asos.file_encryptor.service.interfaces;

import sk.stuba.fei.uim.asos.file_encryptor.service.utils.FileContentIntegrityViolatedException;
import sk.stuba.fei.uim.asos.file_encryptor.service.utils.KeyPairString;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface IFileEncryptorService {

    String generateSecretKeyStringAES() throws NoSuchAlgorithmException;

    String generateSecretKeyStringDES() throws NoSuchAlgorithmException;

    KeyPairString generateKeyPairStringRSA() throws NoSuchAlgorithmException;

    byte[] encryptFileAES(byte[] inputFile, String key) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    byte[] decryptFileAES(byte[] inputFile, String key) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    byte[] encryptFileRSA_MAC_AES(byte[] inputFile, String publicKey)  throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException;

    byte[] decryptFileRSA_MAC_AES(byte[] inputFile, String privateKey) throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException, FileContentIntegrityViolatedException;

}
