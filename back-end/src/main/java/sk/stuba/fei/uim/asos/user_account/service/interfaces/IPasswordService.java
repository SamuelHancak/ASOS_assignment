package sk.stuba.fei.uim.asos.user_account.service.interfaces;

import sk.stuba.fei.uim.asos.user_account.service.PasswordStrength;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface IPasswordService {

    String generateToken();

    String generateSalt();

    public PasswordStrength validatePassword(String password);

    String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException;

    Boolean verifyPassword(String password, String salt, String storedHashPassword) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
