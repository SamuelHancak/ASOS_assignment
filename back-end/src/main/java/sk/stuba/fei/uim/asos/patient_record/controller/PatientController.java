package sk.stuba.fei.uim.asos.patient_record.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.asos.patient_record.controller.body.PatientBody;
import sk.stuba.fei.uim.asos.patient_record.model.Patient;
import sk.stuba.fei.uim.asos.patient_record.service.interfaces.IPatientService;
import sk.stuba.fei.uim.asos.user_account.model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("zadanie-ehealth-api/asos/patient")
//@CrossOrigin("http://localhost:63343")
public class PatientController {

    @Autowired
    private IPatientService patientService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("getPatientsByMedicId/{medicId}/{token}")
    public ResponseEntity<List<PatientBody>> getPatientsByMedicId(@PathVariable("medicId") Long medicId,
                                                                 @PathVariable("token") String token) {
        List<Patient> patients;
        patients = patientService.getPatientsByMedicId(medicId, token );
        List<PatientBody> patientBodies = new ArrayList<>();
        for (Patient tmp : patients) {
            PatientBody tmpPatientBody = new PatientBody();
            tmpPatientBody.setId(tmp.getId());
            tmpPatientBody.setPatientId(tmp.getPatientId());
            tmpPatientBody.setMedicId(tmp.getMedicId());
            tmpPatientBody.setPatientEmail(userRepository.findById(tmp.getPatientId()).get().getEmail());
            tmpPatientBody.setMedicEmail(userRepository.findById(tmp.getMedicId()).get().getEmail());
            tmpPatientBody.setPatientName(userRepository.findById(tmp.getPatientId()).get().getName());
            tmpPatientBody.setMedicName(userRepository.findById(tmp.getMedicId()).get().getName());
            tmpPatientBody.setPatientSurname(userRepository.findById(tmp.getPatientId()).get().getSurname());
            tmpPatientBody.setMedicSurname(userRepository.findById(tmp.getMedicId()).get().getSurname());
            patientBodies.add(tmpPatientBody);
        }
        return new ResponseEntity<List<PatientBody>>(patientBodies, HttpStatus.OK);
    }

    @GetMapping("getPatientsByPatientId/{patientId}/{token}")
    public ResponseEntity<List<PatientBody>> getPatientsByPatientId(@PathVariable("patientId") Long patientId,
                                                     @PathVariable("token") String token) {
        List<Patient> patients;
        patients = patientService.getPatientsByPatientId(patientId, token);
        List<PatientBody> patientBodies = new ArrayList<>();
        for (Patient tmp : patients) {
            PatientBody tmpPatientBody = new PatientBody();
            tmpPatientBody.setId(tmp.getId());
            tmpPatientBody.setPatientId(tmp.getPatientId());
            tmpPatientBody.setMedicId(tmp.getMedicId());
            tmpPatientBody.setPatientEmail(userRepository.findById(tmp.getPatientId()).get().getEmail());
            tmpPatientBody.setMedicEmail(userRepository.findById(tmp.getMedicId()).get().getEmail());
            tmpPatientBody.setPatientName(userRepository.findById(tmp.getPatientId()).get().getName());
            tmpPatientBody.setMedicName(userRepository.findById(tmp.getMedicId()).get().getName());
            tmpPatientBody.setPatientSurname(userRepository.findById(tmp.getPatientId()).get().getSurname());
            tmpPatientBody.setMedicSurname(userRepository.findById(tmp.getMedicId()).get().getSurname());
            patientBodies.add(tmpPatientBody);
        }
        return new ResponseEntity<List<PatientBody>>(patientBodies, HttpStatus.OK);
    }

    @PostMapping("postPatient/{medicId}/{patientId}/{token}")
    public ResponseEntity<Boolean> postPatient(@PathVariable("medicId") Long medicId,
                                               @PathVariable("patientId") Long patientId,
                                               @PathVariable("token") String token) {
        Boolean postedSuccessful = patientService.postPatient(
                medicId,
                patientId,
                token
        );
        return new ResponseEntity<Boolean>(postedSuccessful, HttpStatus.OK);
    }
}
