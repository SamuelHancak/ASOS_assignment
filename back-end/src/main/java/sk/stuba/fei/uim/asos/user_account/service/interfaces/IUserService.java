package sk.stuba.fei.uim.asos.user_account.service.interfaces;

import sk.stuba.fei.uim.asos.user_account.controller.body.UserBody;
import sk.stuba.fei.uim.asos.user_account.service.UserResult;

import java.util.List;

public interface IUserService {

    UserResult registration(String email, String password, String name, String surname, Boolean isMedic);

    UserResult login(String email, String password);

    void logout(Long id);

    Boolean tokenAuthentication(Long id, String token);

    List<UserBody> getUsers(Long id, String token);

    List<UserBody> getMedics(Long id, String token);

    List<UserBody> getPatients(Long id, String token);
}
