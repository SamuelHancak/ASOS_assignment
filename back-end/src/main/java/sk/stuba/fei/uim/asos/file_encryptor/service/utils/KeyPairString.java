package sk.stuba.fei.uim.asos.file_encryptor.service.utils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class KeyPairString {

    private String publicKey;
    private String privateKey;

    public KeyPairString(KeyPair keyPair) {
        this.publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        this.privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

}
