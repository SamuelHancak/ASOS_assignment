package sk.stuba.fei.uim.asos.shared_file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.asos.file_encryptor.service.interfaces.IFileEncryptorService;
import sk.stuba.fei.uim.asos.shared_file.model.SharedFile;
import sk.stuba.fei.uim.asos.shared_file.model.repository.SharedFileRepository;
import sk.stuba.fei.uim.asos.shared_file.service.interfaces.ISharedFileService;
import sk.stuba.fei.uim.asos.user_account.model.User;
import sk.stuba.fei.uim.asos.user_account.model.repository.UserRepository;
import sk.stuba.fei.uim.asos.user_account.service.interfaces.IPasswordService;
import sk.stuba.fei.uim.asos.user_account.service.interfaces.IUserService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class SharedFileService implements ISharedFileService {

    @Autowired
    private SharedFileRepository sharedFileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IPasswordService passwordService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFileEncryptorService fileEncryptorService;

    @Override
    public List<SharedFile> getSharedFilesTo(Long userId, String token) {
        Optional<User> users = userRepository.findById(userId);
        if(users.isEmpty())
            return null;
        if(!userService.tokenAuthentication(userId, token)) {
            return null;
        }
        try {
            List<SharedFile> sharedFiles = sharedFileRepository.findBySharedToUserId(userId);
            for (SharedFile tmp : sharedFiles) {
                    tmp.setFileContent(
                            new String(
                                    fileEncryptorService.decryptFileAES(
                                            Base64.getDecoder().decode(tmp.getFileContent()),
                                            users.get().getSymmetricKey()
                                    )
                            )
                );
            }
            return sharedFiles;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                BadPaddingException | InvalidKeyException e) {
            return null;
        }
    }

    @Override
    public Boolean postSharedFile(Long sharedFromUserId, String token, String fileContent, Long sharedToUserId) {
        Optional<User> userTo = userRepository.findById(sharedToUserId);
        if(userTo.isEmpty())
            return false;
        Optional<User> userFrom = userRepository.findById(sharedFromUserId);
        if(userFrom.isEmpty())
            return false;
        if(!userService.tokenAuthentication(sharedFromUserId, token)) {
            return false;
        }
        try {
            SharedFile sharedFile = new SharedFile();
            sharedFile.setSharedFromUserId(sharedFromUserId);
            sharedFile.setSharedToUserId(sharedToUserId);
            sharedFile.setFileContent(
                    Base64.getEncoder().encodeToString(
                            fileEncryptorService.encryptFileAES(fileContent.getBytes(), userTo.get().getSymmetricKey())
                    )
            );
            sharedFileRepository.save(sharedFile);
            return true;
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                 BadPaddingException | InvalidKeyException e) {
            return null;
        }
    }
}
