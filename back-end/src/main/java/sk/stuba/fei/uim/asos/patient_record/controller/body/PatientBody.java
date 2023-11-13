package sk.stuba.fei.uim.asos.patient_record.controller.body;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientBody {

    private Long id;

    private Long patientId;

    private Long medicId;

    private String patientEmail;

    private String medicEmail;

    private String patientName;

    private String medicName;

    private String patientSurname;

    private String medicSurname;
}
