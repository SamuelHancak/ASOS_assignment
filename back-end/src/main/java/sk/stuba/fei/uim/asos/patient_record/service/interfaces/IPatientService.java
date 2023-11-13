package sk.stuba.fei.uim.asos.patient_record.service.interfaces;

import sk.stuba.fei.uim.asos.patient_record.model.Patient;

import java.util.List;

public interface IPatientService {

    List<Patient> getPatientsByMedicId(Long medicId, String password);

    public List<Patient> getPatientsByPatientId(Long patientId, String password);

    Boolean postPatient(Long medicId, Long patientId, String password);
}
