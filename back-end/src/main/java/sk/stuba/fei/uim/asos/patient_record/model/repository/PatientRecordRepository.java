package sk.stuba.fei.uim.asos.patient_record.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.stuba.fei.uim.asos.patient_record.model.PatientRecord;

import java.util.List;

@Repository
public interface PatientRecordRepository extends JpaRepository<PatientRecord, Long> {

    List<PatientRecord> findByMedicId(Long medicId);

    List<PatientRecord> findByPatientId(Long patientId);

    List<PatientRecord> findByMedicIdAndPatientId(Long medicId, Long patientId);

    List<PatientRecord> findByMedicIdAndTitleContainingIgnoreCase(Long medicId, String title);

    List<PatientRecord> findByPatientIdAndTitleContainingIgnoreCase(Long patientId, String title);
}
