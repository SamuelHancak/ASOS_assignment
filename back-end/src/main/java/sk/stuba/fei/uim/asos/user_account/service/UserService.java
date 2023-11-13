package sk.stuba.fei.uim.asos.user_account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.asos.file_encryptor.service.interfaces.IFileEncryptorService;
import sk.stuba.fei.uim.asos.file_encryptor.service.utils.KeyPairString;
import sk.stuba.fei.uim.asos.user_account.controller.body.UserBody;
import sk.stuba.fei.uim.asos.user_account.model.User;
import sk.stuba.fei.uim.asos.user_account.model.repository.UserRepository;
import sk.stuba.fei.uim.asos.user_account.service.interfaces.IPasswordService;
import sk.stuba.fei.uim.asos.user_account.service.interfaces.IUserService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IPasswordService passwordService;

    @Autowired
    private IFileEncryptorService fileEncryptorService;

    @Override
    public UserResult registration(String email, String password, String name, String surname, Boolean isMedic) {
        if(!userRepository.findByEmail(email).isEmpty())
            return new UserResult(false, null, null, "User with email " + email + " already exist.", null);
        switch(passwordService.validatePassword(password)) {
            case LEAKED:
                return new UserResult(false, null, null, "Password was found on leaked passwords list.", null);
            case WEAK:
                return new UserResult(false, null, null, "Password is weak.", null);
            case MODERATE:
                return new UserResult(false, null, null, "Password is moderate.", null);
        }
        String salt = passwordService.generateSalt();
        String hashPassword;
        String token = passwordService.generateToken();
        try {
            hashPassword = passwordService.hashPassword(password, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return new UserResult(false, null, null, e.getMessage(), null);
        }
        String key;
        try {
            key = fileEncryptorService.generateSecretKeyStringAES();
        } catch (NoSuchAlgorithmException e) {
            return new UserResult(false, null, null, e.getMessage(), null);
        }
        KeyPairString keyPair;
        try {
            keyPair = fileEncryptorService.generateKeyPairStringRSA();
        } catch (NoSuchAlgorithmException e) {
            return new UserResult(false, null, null, e.getMessage(), null);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashPassword);
        user.setToken(token);
        user.setSalt(salt);
        user.setName(name);
        user.setSurname(surname);
        user.setIsMedic(isMedic);
        user.setSymmetricKey(key);
        user.setAsymmetricPrivateKey(keyPair.getPrivateKey());
        user.setAsymmetricPublicKey(keyPair.getPublicKey());
        user = userRepository.save(user);
        return new UserResult(true, user.getId(), isMedic, "Registration was successful.", token);
    }

    @Override
    public UserResult login(String email, String password) {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
            List<User> users = userRepository.findByEmail(email);
            if(users.isEmpty())
                return new UserResult(false, null, null, "User with email " + email + "non exist.", null);
            if(passwordService.verifyPassword(password, users.get(0).getSalt(), users.get(0).getPassword())) {
                users.get(0).setToken(passwordService.generateToken());
                userRepository.save(users.get(0));
                return new UserResult(true, users.get(0).getId(), users.get(0).getIsMedic(), "Login was successful.", users.get(0).getToken());
            } else
                return new UserResult(false, null, null, "Login was not successful.", null);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InterruptedException e) {
            return new UserResult(false, null, null, e.getMessage(), null);
        }
    }

    @Override
    public void logout(Long id) {
        Optional<User> users = userRepository.findById(id);
        if (users.isPresent()) {
            users.get().setToken(passwordService.generateToken());
            userRepository.save(users.get());
        }
    }

    @Override
    public Boolean tokenAuthentication(Long id, String token) {
        Optional<User> users = userRepository.findById(id);
        if (users.isPresent())
            return users.get().getToken().equals(token);
        return false;
    }

    @Override
    public List<UserBody> getUsers(Long id, String token) {
        Optional<User> users = userRepository.findById(id);
        if(users.isEmpty())
            return null;
        if(!this.tokenAuthentication(id, token))
            return Collections.emptyList();
        List<User> userList = userRepository.findAll();
        List<UserBody> userBodies = new ArrayList<>();
        for(User tmp : userList) {
            UserBody tmpUserBody  = new UserBody();
            tmpUserBody.setId(tmp.getId());
            tmpUserBody.setEmail(tmp.getEmail());
            tmpUserBody.setName(tmp.getName());
            tmpUserBody.setSurname(tmp.getSurname());
            tmpUserBody.setIsMedic(tmp.getIsMedic());
            userBodies.add(tmpUserBody);
        }
        return userBodies;
    }

    @Override
    public List<UserBody> getMedics(Long id, String token) {
        Optional<User> users = userRepository.findById(id);
        if(users.isEmpty())
            return null;
        if(!this.tokenAuthentication(id, token))
            return Collections.emptyList();
        List<User> userList = userRepository.findByIsMedic(true);
        List<UserBody> userBodies = new ArrayList<>();
        for(User tmp : userList) {
            UserBody tmpUserBody  = new UserBody();
            tmpUserBody.setId(tmp.getId());
            tmpUserBody.setEmail(tmp.getEmail());
            tmpUserBody.setName(tmp.getName());
            tmpUserBody.setSurname(tmp.getSurname());
            tmpUserBody.setIsMedic(tmp.getIsMedic());
            userBodies.add(tmpUserBody);
        }
        return userBodies;
    }

    @Override
    public List<UserBody> getPatients(Long id, String token) {
        Optional<User> users = userRepository.findById(id);
        if(users.isEmpty())
            return null;
        if(!this.tokenAuthentication(id, token))
            return Collections.emptyList();
        List<User> userList = userRepository.findByIsMedic(false);
        List<UserBody> userBodies = new ArrayList<>();
        for(User tmp : userList) {
            UserBody tmpUserBody  = new UserBody();
            tmpUserBody.setId(tmp.getId());
            tmpUserBody.setEmail(tmp.getEmail());
            tmpUserBody.setName(tmp.getName());
            tmpUserBody.setSurname(tmp.getSurname());
            tmpUserBody.setIsMedic(tmp.getIsMedic());
            userBodies.add(tmpUserBody);
        }
        return userBodies;
    }
}
