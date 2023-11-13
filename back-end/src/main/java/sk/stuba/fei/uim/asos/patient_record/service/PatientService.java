package sk.stuba.fei.uim.asos.patient_record.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.asos.patient_record.model.Patient;
import sk.stuba.fei.uim.asos.patient_record.model.repository.PatientRepository;
import sk.stuba.fei.uim.asos.patient_record.service.interfaces.IPatientService;
import sk.stuba.fei.uim.asos.user_account.model.User;
import sk.stuba.fei.uim.asos.user_account.model.repository.UserRepository;
import sk.stuba.fei.uim.asos.user_account.service.interfaces.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    @Override
    public List<Patient> getPatientsByMedicId(Long medicId, String token) {
        Optional<User> users = userRepository.findById(medicId);
        if(users.isEmpty())
            return null;
        if(!userService.tokenAuthentication(medicId, token)) {
            return null;
        }
        return patientRepository.findByMedicId(medicId);
    }

    @Override
    public List<Patient> getPatientsByPatientId(Long patientId, String token) {
        Optional<User> users = userRepository.findById(patientId);
        if(users.isEmpty())
            return null;
        if(!userService.tokenAuthentication(patientId, token)) {
            return null;
        }
        return patientRepository.findByPatientId(patientId);
    }

    @Override
    public Boolean postPatient(Long medicId, Long patientId, String token) {
        Optional<User> users = userRepository.findById(medicId);
        if(users.isEmpty())
            return false;
        Optional<Patient> patients = patientRepository.findByMedicIdAndPatientId(medicId, patientId);
        if(patients.isPresent())
            return false;
        if(!userService.tokenAuthentication(medicId, token)) {
            return false;
        }
        Patient patient = new Patient();
        patient.setMedicId(medicId);
        patient.setPatientId(patientId);
        patientRepository.save(patient);
        return true;
    }
}
