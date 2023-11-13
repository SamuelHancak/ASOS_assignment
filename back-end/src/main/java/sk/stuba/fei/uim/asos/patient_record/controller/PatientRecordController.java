package sk.stuba.fei.uim.asos.patient_record.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.asos.patient_record.controller.body.PatientRecordBody;
import sk.stuba.fei.uim.asos.patient_record.model.PatientRecord;
import sk.stuba.fei.uim.asos.patient_record.service.interfaces.IPatientRecordService;
import sk.stuba.fei.uim.asos.user_account.model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("zadanie-ehealth-api/asos/patientRecord")
//@CrossOrigin("http://localhost:63343")
public class PatientRecordController {

    @Autowired
    private IPatientRecordService patientRecordService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("getPatientRecordsByMedicId/{medicId}/{token}")
    public ResponseEntity<List<PatientRecordBody>> getPatientRecordsByMedicId(@PathVariable("medicId") Long medicId,
                                                               @PathVariable("token") String token) {
        List<PatientRecordBody> patientRecordBodies = new ArrayList<>();
        List<PatientRecord> patientRecords = patientRecordService.getPatientRecordsByMedicId(medicId, token);
        for (PatientRecord tmp : patientRecords) {
            PatientRecordBody tmpPatientRecordBody = new PatientRecordBody();
            tmpPatientRecordBody.setId(tmp.getId());
            tmpPatientRecordBody.setTitle(tmp.getTitle());
            tmpPatientRecordBody.setContent(tmp.getContent());
            tmpPatientRecordBody.setPatientEmail(userRepository.findById(tmp.getPatientId()).get().getEmail());
            tmpPatientRecordBody.setPatientName(userRepository.findById(tmp.getPatientId()).get().getName());
            tmpPatientRecordBody.setPatientSurname(userRepository.findById(tmp.getPatientId()).get().getSurname());
            tmpPatientRecordBody.setMedicEmail(userRepository.findById(medicId).get().getEmail());
            tmpPatientRecordBody.setMedicName(userRepository.findById(medicId).get().getName());
            tmpPatientRecordBody.setMedicSurname(userRepository.findById(medicId).get().getSurname());
            patientRecordBodies.add(tmpPatientRecordBody);
        }
        return new ResponseEntity<List<PatientRecordBody>>(patientRecordBodies, HttpStatus.OK);
    }

    @GetMapping("getPatientRecordsByPatientId/{patientId}/{token}")
    public ResponseEntity<List<PatientRecordBody>> getPatientRecordsByPatientId(@PathVariable("patientId") Long patientId,
                                                                              @PathVariable("token") String token) {
        List<PatientRecordBody> patientRecordBodies = new ArrayList<>();
        List<PatientRecord> patientRecords = patientRecordService.getPatientRecordsByPatientId(patientId, token);
        for (PatientRecord tmp : patientRecords) {
            PatientRecordBody tmpPatientRecordBody = new PatientRecordBody();
            tmpPatientRecordBody.setId(tmp.getId());
            tmpPatientRecordBody.setTitle(tmp.getTitle());
            tmpPatientRecordBody.setContent(tmp.getContent());
            tmpPatientRecordBody.setPatientEmail(userRepository.findById(patientId).get().getEmail());
            tmpPatientRecordBody.setPatientName(userRepository.findById(patientId).get().getName());
            tmpPatientRecordBody.setPatientSurname(userRepository.findById(patientId).get().getSurname());
            tmpPatientRecordBody.setMedicEmail(userRepository.findById(tmp.getMedicId()).get().getEmail());
            tmpPatientRecordBody.setMedicName(userRepository.findById(tmp.getMedicId()).get().getName());
            tmpPatientRecordBody.setMedicSurname(userRepository.findById(tmp.getMedicId()).get().getSurname());
            patientRecordBodies.add(tmpPatientRecordBody);
        }
        return new ResponseEntity<List<PatientRecordBody>>(patientRecordBodies, HttpStatus.OK);
    }

    @GetMapping("getPatientRecordsByMedicId/{medicId}/{patientId}/{token}")
    public ResponseEntity<List<PatientRecordBody>> getPatientRecordsByMedicId(@PathVariable("medicId") Long medicId,
                                                            @PathVariable("patientId") Long patientId,
                                                            @PathVariable("token") String token) {
        List<PatientRecordBody> patientRecordBodies = new ArrayList<>();
        List<PatientRecord> patientRecords = patientRecordService.getPatientRecordsByMedicId(medicId, patientId, token);
        for (PatientRecord tmp : patientRecords) {
            PatientRecordBody tmpPatientRecordBody = new PatientRecordBody();
            tmpPatientRecordBody.setId(tmp.getId());
            tmpPatientRecordBody.setTitle(tmp.getTitle());
            tmpPatientRecordBody.setContent(tmp.getContent());
            tmpPatientRecordBody.setPatientEmail(userRepository.findById(tmp.getPatientId()).get().getEmail());
            tmpPatientRecordBody.setPatientName(userRepository.findById(tmp.getPatientId()).get().getName());
            tmpPatientRecordBody.setPatientSurname(userRepository.findById(tmp.getPatientId()).get().getSurname());
            tmpPatientRecordBody.setMedicEmail(userRepository.findById(medicId).get().getEmail());
            tmpPatientRecordBody.setMedicName(userRepository.findById(medicId).get().getName());
            tmpPatientRecordBody.setMedicSurname(userRepository.findById(medicId).get().getSurname());
            patientRecordBodies.add(tmpPatientRecordBody);
        }
        return new ResponseEntity<List<PatientRecordBody>>(patientRecordBodies, HttpStatus.OK);
    }

    @GetMapping("getPatientRecordsByPatientId/{medicId}/{patientId}/{token}")
    public ResponseEntity<List<PatientRecordBody>> getPatientRecordsByPatientId(@PathVariable("medicId") Long medicId,
                                                                              @PathVariable("patientId") Long patientId,
                                                                              @PathVariable("token") String token) {
        List<PatientRecordBody> patientRecordBodies = new ArrayList<>();
        List<PatientRecord> patientRecords = patientRecordService.getPatientRecordsByPatientId(patientId, medicId, token);
        for (PatientRecord tmp : patientRecords) {
            PatientRecordBody tmpPatientRecordBody = new PatientRecordBody();
            tmpPatientRecordBody.setId(tmp.getId());
            tmpPatientRecordBody.setTitle(tmp.getTitle());
            tmpPatientRecordBody.setContent(tmp.getContent());
            tmpPatientRecordBody.setPatientEmail(userRepository.findById(tmp.getPatientId()).get().getEmail());
            tmpPatientRecordBody.setPatientName(userRepository.findById(tmp.getPatientId()).get().getName());
            tmpPatientRecordBody.setPatientSurname(userRepository.findById(tmp.getPatientId()).get().getSurname());
            tmpPatientRecordBody.setMedicEmail(userRepository.findById(medicId).get().getEmail());
            tmpPatientRecordBody.setMedicName(userRepository.findById(medicId).get().getName());
            tmpPatientRecordBody.setMedicSurname(userRepository.findById(medicId).get().getSurname());
            patientRecordBodies.add(tmpPatientRecordBody);
        }
        return new ResponseEntity<List<PatientRecordBody>>(patientRecordBodies, HttpStatus.OK);
    }

    @GetMapping("getPatientRecordsByMedicIdAndByTitleContains/{medicId}/{title}/{token}")
    public ResponseEntity<List<PatientRecordBody>> getPatientRecordsByMedicIdAndByTitleContains(
                                                                                @PathVariable("medicId") Long medicId,
                                                                                @PathVariable("title") String tittle,
                                                                                @PathVariable("token") String token) {
        List<PatientRecordBody> patientRecordBodies = new ArrayList<>();
        List<PatientRecord> patientRecords = patientRecordService.getPatientRecordsByMedicIdAndTitleContains(tittle, medicId, token);
        for (PatientRecord tmp : patientRecords) {
            PatientRecordBody tmpPatientRecordBody = new PatientRecordBody();
            tmpPatientRecordBody.setId(tmp.getId());
            tmpPatientRecordBody.setTitle(tmp.getTitle());
            tmpPatientRecordBody.setContent(tmp.getContent());
            tmpPatientRecordBody.setPatientEmail(userRepository.findById(tmp.getPatientId()).get().getEmail());
            tmpPatientRecordBody.setPatientName(userRepository.findById(tmp.getPatientId()).get().getName());
            tmpPatientRecordBody.setPatientSurname(userRepository.findById(tmp.getPatientId()).get().getSurname());
            tmpPatientRecordBody.setMedicEmail(userRepository.findById(medicId).get().getEmail());
            tmpPatientRecordBody.setMedicName(userRepository.findById(medicId).get().getName());
            tmpPatientRecordBody.setMedicSurname(userRepository.findById(medicId).get().getSurname());
            patientRecordBodies.add(tmpPatientRecordBody);
        }
        return new ResponseEntity<List<PatientRecordBody>>(patientRecordBodies, HttpStatus.OK);
    }

    @GetMapping("getPatientRecordsByPatientIdAndByTitleContains/{medicId}/{title}/{token}")
    public ResponseEntity<List<PatientRecordBody>> getPatientRecordsByPatientIdAndByTitleContains(
                                                                                @PathVariable("medicId") Long patientId,
                                                                                @PathVariable("title") String tittle,
                                                                                @PathVariable("token") String token) {
        List<PatientRecordBody> patientRecordBodies = new ArrayList<>();
        List<PatientRecord> patientRecords = patientRecordService.getPatientRecordsByPatientIdAndTitleContains(tittle, patientId, token);
        for (PatientRecord tmp : patientRecords) {
            PatientRecordBody tmpPatientRecordBody = new PatientRecordBody();
            tmpPatientRecordBody.setId(tmp.getId());
            tmpPatientRecordBody.setTitle(tmp.getTitle());
            tmpPatientRecordBody.setContent(tmp.getContent());
            tmpPatientRecordBody.setPatientEmail(userRepository.findById(patientId).get().getEmail());
            tmpPatientRecordBody.setPatientName(userRepository.findById(patientId).get().getName());
            tmpPatientRecordBody.setPatientSurname(userRepository.findById(patientId).get().getSurname());
            tmpPatientRecordBody.setMedicEmail(userRepository.findById(tmp.getMedicId()).get().getEmail());
            tmpPatientRecordBody.setMedicName(userRepository.findById(tmp.getMedicId()).get().getName());
            tmpPatientRecordBody.setMedicSurname(userRepository.findById(tmp.getMedicId()).get().getSurname());
            patientRecordBodies.add(tmpPatientRecordBody);
        }
        return new ResponseEntity<List<PatientRecordBody>>(patientRecordBodies, HttpStatus.OK);
    }

    @PostMapping("postPatientRecord/{medicId}/{patientId}/{token}")
    public ResponseEntity<Boolean> postPatientRecord(@PathVariable("medicId") Long medicId,
                                                     @PathVariable("patientId") Long patientId,
                                                     @PathVariable("token") String token,
                                                     @RequestBody PatientRecordBody patientRecordBody) {
        Boolean postedSuccessful = patientRecordService.postPatientRecord(
                patientRecordBody.getTitle(),
                patientRecordBody.getContent(),
                medicId,
                patientId,
                token
        );
        return new ResponseEntity<Boolean>(postedSuccessful, HttpStatus.OK);
    }
}
