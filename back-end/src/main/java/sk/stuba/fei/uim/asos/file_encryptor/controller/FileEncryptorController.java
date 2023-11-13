package sk.stuba.fei.uim.asos.file_encryptor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.asos.file_encryptor.controller.body.AsymmetricKeyBody;
import sk.stuba.fei.uim.asos.file_encryptor.controller.body.FileBody;
import sk.stuba.fei.uim.asos.file_encryptor.controller.body.SymmetricKeyBody;
import sk.stuba.fei.uim.asos.file_encryptor.service.interfaces.IFileEncryptorService;
import sk.stuba.fei.uim.asos.file_encryptor.service.utils.FileContentIntegrityViolatedException;
import sk.stuba.fei.uim.asos.file_encryptor.service.utils.KeyPairString;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@RestController
@RequestMapping("zadanie-ehealth-api/asos/fileEncryptor")
//@CrossOrigin("http://localhost:63343")
public class FileEncryptorController {

    @Autowired
    private IFileEncryptorService fileEncryptorService;

    @GetMapping("generateSymmetricKey")
    public ResponseEntity<SymmetricKeyBody> getSymmetricKey() {
        SymmetricKeyBody symmetricKeyBody = new SymmetricKeyBody();
        try {
            symmetricKeyBody.setKey(fileEncryptorService.generateSecretKeyStringAES());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<SymmetricKeyBody>(symmetricKeyBody, HttpStatus.OK);
    }

    @GetMapping("generateAsymmetricKey")
    public ResponseEntity<AsymmetricKeyBody> getAsymmetricKey() {
        AsymmetricKeyBody asymmetricKeyBody = new AsymmetricKeyBody();
        try {
            KeyPairString keyPairString = fileEncryptorService.generateKeyPairStringRSA();
            asymmetricKeyBody.setPrivateKey(keyPairString.getPrivateKey());
            asymmetricKeyBody.setPublicKey(keyPairString.getPublicKey());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<AsymmetricKeyBody>(asymmetricKeyBody, HttpStatus.OK);
    }

    @PostMapping("encryptSymmetricFile")
    public ResponseEntity<FileBody> encryptSymmetricFile(@RequestBody FileBody fileBody) {
        try {
            fileBody.setOutputContent(
                    Base64.getEncoder().encodeToString(
                            fileEncryptorService.encryptFileAES(fileBody.getInputContent().getBytes(), fileBody.getKey())
                    )
            );
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<FileBody>(fileBody, HttpStatus.OK);
    }

    @PostMapping("encryptAsymmetricFile")
    public ResponseEntity<FileBody> encryptAsymmetricFile(@RequestBody FileBody fileBody) {
        try {
            fileBody.setOutputContent(
                    Base64.getEncoder().encodeToString(
                            fileEncryptorService.encryptFileRSA_MAC_AES(fileBody.getInputContent().getBytes(), fileBody.getKey())
                    )
            );
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<FileBody>(fileBody, HttpStatus.OK);
    }

    @PostMapping("decryptSymmetricFile")
    public ResponseEntity<FileBody> decryptSymmetricFile(@RequestBody FileBody fileBody) {
        try {
            fileBody.setOutputContent(
                new String(
                    fileEncryptorService.decryptFileAES(Base64.getDecoder().decode(fileBody.getInputContent()), fileBody.getKey())
                )
            );
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<FileBody>(fileBody, HttpStatus.OK);
    }

    @PostMapping("decryptAsymmetricFile")
    public ResponseEntity<FileBody> decryptAsymmetricFile(@RequestBody FileBody fileBody) {
        try {
            fileBody.setOutputContent(
                    new String(
                            fileEncryptorService.decryptFileRSA_MAC_AES(Base64.getDecoder().decode(fileBody.getInputContent()), fileBody.getKey())
                    )
            );
            fileBody.setIsFileUnchanged(true);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException |
                 InvalidKeyException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (FileContentIntegrityViolatedException | IllegalBlockSizeException | BadPaddingException e) {
            fileBody.setIsFileUnchanged(false);
        }
        return new ResponseEntity<FileBody>(fileBody, HttpStatus.OK);
    }
}
