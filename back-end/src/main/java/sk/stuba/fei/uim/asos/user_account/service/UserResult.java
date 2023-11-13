package sk.stuba.fei.uim.asos.user_account.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResult {

    private Boolean result;

    private Long userId;

    private Boolean isMedic;

    private String message;

    private String token;
}
