package sk.stuba.fei.uim.asos.user_account.controller.body;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBody {

    private Long id;

    private String email;

    private String name;

    private String surname;

    private Boolean isMedic;
}
