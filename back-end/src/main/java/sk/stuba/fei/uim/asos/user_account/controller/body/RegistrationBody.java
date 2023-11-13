package sk.stuba.fei.uim.asos.user_account.controller.body;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationBody {

    private String email;

    private String password;

    private String name;

    private String surname;

    private Boolean isMedic;
}
