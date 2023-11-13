package sk.stuba.fei.uim.asos.patient_record.service.interfaces;

import sk.stuba.fei.uim.asos.patient_record.model.PatientRecord;

import java.util.List;

public interface IPatientRecordService {

    List<PatientRecord> getPatientRecordsByMedicId(Long medicId, String token);

    List<PatientRecord> getPatientRecordsByMedicId(Long medicId, Long patientId, String token);

    List<PatientRecord> getPatientRecordsByPatientId(Long patientId, String token);

    List<PatientRecord> getPatientRecordsByPatientId(Long patientId, Long medicId, String token);

    List<PatientRecord> getPatientRecordsByMedicIdAndTitleContains(String title, Long medicId, String token);

    List<PatientRecord> getPatientRecordsByPatientIdAndTitleContains(String title, Long patientId, String token);

    Boolean postPatientRecord(String title, String content, Long medicId, Long patientId, String token);
}
