package sk.stuba.fei.uim.asos.user_account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.asos.user_account.model.repository.LeakedPasswordRepository;
import sk.stuba.fei.uim.asos.user_account.service.interfaces.IPasswordService;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class PasswordService implements IPasswordService {

    @Autowired
    private LeakedPasswordRepository leakedPasswordRepository;

    @Override
    public String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[24];
        random.nextBytes(randomBytes);
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        return base64Encoder.encodeToString(randomBytes);
    }

    @Override
    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return new String(salt);
    }

    @Override
    public PasswordStrength validatePassword(String password) {
        if(!leakedPasswordRepository.findByPassword(password).isEmpty())
            return PasswordStrength.LEAKED;
        boolean hasLower = false, hasUpper = false, hasDigit = false;
        for (char i : password.toCharArray()) {
            if (Character.isLowerCase(i))
                hasLower = true;
            if (Character.isUpperCase(i))
                hasUpper = true;
            if (Character.isDigit(i))
                hasDigit = true;
        }
        if (hasDigit && hasLower && hasUpper && (password.length() >= 8))
            return PasswordStrength.STRONG;
        else if ((hasLower || hasUpper) && (password.length() >= 6))
            return PasswordStrength.MODERATE;
        else
            return PasswordStrength.WEAK;
    }

    @Override
    public String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return new String(factory.generateSecret(spec).getEncoded());
    }

    @Override
    public Boolean verifyPassword(String password, String salt, String storedHashPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return this.hashPassword(password, salt).equals(storedHashPassword);
    }

}
