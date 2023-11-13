package sk.stuba.fei.uim.asos.patient_record.controller.body;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientRecordBody {

    private Long id;

    private String title;

    private String content;

    private String medicEmail;

    private String medicName;

    private String medicSurname;

    private String patientEmail;

    private String patientName;

    private String patientSurname;
}
