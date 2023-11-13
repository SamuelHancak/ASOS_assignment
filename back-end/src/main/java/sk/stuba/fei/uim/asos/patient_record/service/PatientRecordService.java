package sk.stuba.fei.uim.asos.patient_record.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.asos.file_encryptor.service.interfaces.IFileEncryptorService;
import sk.stuba.fei.uim.asos.patient_record.model.PatientRecord;
import sk.stuba.fei.uim.asos.patient_record.model.repository.PatientRecordRepository;
import sk.stuba.fei.uim.asos.patient_record.service.interfaces.IPatientRecordService;
import sk.stuba.fei.uim.asos.user_account.model.User;
import sk.stuba.fei.uim.asos.user_account.model.repository.UserRepository;
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
public class PatientRecordService implements IPatientRecordService {

    @Autowired
    private PatientRecordRepository patientRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFileEncryptorService fileEncryptorService;

    @Override
    public List<PatientRecord> getPatientRecordsByMedicId(Long medicId, String token) {
        Optional<User> users = userRepository.findById(medicId);
        if(users.isEmpty())
            return null;
        if(!userService.tokenAuthentication(medicId, token)) {
            return null;
        }
        try {
            List<PatientRecord> patientRecords = patientRecordRepository.findByMedicId(medicId);
            for (PatientRecord tmp : patientRecords) {
                tmp.setContent(
                        new String(
                                fileEncryptorService.decryptFileAES(
                                        Base64.getDecoder().decode(tmp.getContent()),
                                        users.get().getSymmetricKey()
                                )
                        )
                );
            }
            return patientRecords;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            return null;
        }
    }

    @Override
    public List<PatientRecord> getPatientRecordsByMedicId(Long medicId, Long patientId, String token) {
        Optional<User> users = userRepository.findById(medicId);
        if(users.isEmpty())
            return null;
        if(!userService.tokenAuthentication(medicId, token)) {
            return null;
        }
        try {
            List<PatientRecord> patientRecords = patientRecordRepository.findByMedicIdAndPatientId(medicId, patientId);
            for (PatientRecord tmp : patientRecords) {
                tmp.setContent(
                        new String(
                                fileEncryptorService.decryptFileAES(
                                        Base64.getDecoder().decode(tmp.getContent()),
                                        users.get().getSymmetricKey()
                                )
                        )
                );
            }
            return patientRecords;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            return null;
        }
    }

    @Override
    public List<PatientRecord> getPatientRecordsByPatientId(Long patientId, String token) {
        Optional<User> users = userRepository.findById(patientId);
        if(users.isEmpty())
            return null;
        if(!userService.tokenAuthentication(patientId, token)) {
            return null;
        }
        try {
            List<PatientRecord> patientRecords = patientRecordRepository.findByPatientId(patientId);
            for (PatientRecord tmp : patientRecords) {
                tmp.setContent(
                        new String(
                                fileEncryptorService.decryptFileAES(
                                        Base64.getDecoder().decode(tmp.getContent()),
                                        userRepository.findById(tmp.getMedicId()).get().getSymmetricKey()
                                )
                        )
                );
            }
            return patientRecords;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            return null;
        }
    }

    @Override
    public List<PatientRecord> getPatientRecordsByPatientId(Long patientId, Long medicId, String token) {
        Optional<User> users = userRepository.findById(patientId);
        if(users.isEmpty())
            return null;
        if(!userService.tokenAuthentication(patientId, token)) {
            return null;
        }
        try {
            List<PatientRecord> patientRecords = patientRecordRepository.findByMedicIdAndPatientId(medicId, patientId);
            for (PatientRecord tmp : patientRecords) {
                tmp.setContent(
                        new String(
                                fileEncryptorService.decryptFileAES(
                                        Base64.getDecoder().decode(tmp.getContent()),
                                        userRepository.findById(tmp.getMedicId()).get().getSymmetricKey()
                                )
                        )
                );
            }
            return patientRecords;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            return null;
        }
    }

    @Override
    public List<PatientRecord> getPatientRecordsByMedicIdAndTitleContains(String title, Long medicId, String token) {
        Optional<User> users = userRepository.findById(medicId);
        if(users.isEmpty())
            return null;
        if(!userService.tokenAuthentication(medicId, token)) {
            return null;
        }
        try {
            List<PatientRecord> patientRecords;
            if(title.equals(""))
                patientRecords = patientRecordRepository.findByMedicId(medicId);
            else
                patientRecords = patientRecordRepository.findByMedicIdAndTitleContainingIgnoreCase(medicId, title);
            for (PatientRecord tmp : patientRecords) {
                tmp.setContent(
                        new String(
                                fileEncryptorService.decryptFileAES(
                                        Base64.getDecoder().decode(tmp.getContent()),
                                        userRepository.findById(tmp.getMedicId()).get().getSymmetricKey()
                                )
                        )
                );
            }
            return patientRecords;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            return null;
        }
    }

    @Override
    public List<PatientRecord> getPatientRecordsByPatientIdAndTitleContains(String title, Long patientId, String token) {
        Optional<User> users = userRepository.findById(patientId);
        if(users.isEmpty())
            return null;
        if(!userService.tokenAuthentication(patientId, token)) {
            return null;
        }
        try {
            List<PatientRecord> patientRecords;
            if(title.equals(""))
                patientRecords = patientRecordRepository.findByPatientId(patientId);
            else
                patientRecords = patientRecordRepository.findByPatientIdAndTitleContainingIgnoreCase(patientId, title);
            for (PatientRecord tmp : patientRecords) {
                tmp.setContent(
                        new String(
                                fileEncryptorService.decryptFileAES(
                                        Base64.getDecoder().decode(tmp.getContent()),
                                        userRepository.findById(tmp.getMedicId()).get().getSymmetricKey()
                                )
                        )
                );
            }
            return patientRecords;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            return null;
        }
    }

    @Override
    public Boolean postPatientRecord(String title, String content, Long medicId, Long patientId, String token) {
        Optional<User> users = userRepository.findById(medicId);
        if(users.isEmpty())
            return false;
        if(!userService.tokenAuthentication(medicId, token)) {
            return null;
        }
        try {
            PatientRecord patientRecord = new PatientRecord();
            patientRecord.setMedicId(medicId);
            patientRecord.setPatientId(patientId);
            patientRecord.setTitle(title);
            patientRecord.setContent(
                    Base64.getEncoder().encodeToString(
                            fileEncryptorService.encryptFileAES(content.getBytes(), users.get().getSymmetricKey())
                    )
            );
            patientRecordRepository.save(patientRecord);
            return true;
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                 BadPaddingException | InvalidKeyException e) {
            return null;
        }
    }
}
