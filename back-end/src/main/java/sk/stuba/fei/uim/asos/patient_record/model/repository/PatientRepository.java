package sk.stuba.fei.uim.asos.patient_record.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.stuba.fei.uim.asos.patient_record.model.Patient;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByMedicId(Long medicId);

    List<Patient> findByPatientId(Long patientId);

    Optional<Patient> findByMedicIdAndPatientId(Long medicId, Long patientId);
}
