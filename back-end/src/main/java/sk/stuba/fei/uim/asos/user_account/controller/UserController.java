package sk.stuba.fei.uim.asos.user_account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.asos.user_account.controller.body.AuthenticationBody;
import sk.stuba.fei.uim.asos.user_account.controller.body.RegistrationBody;
import sk.stuba.fei.uim.asos.user_account.controller.body.UserBody;
import sk.stuba.fei.uim.asos.user_account.service.UserResult;
import sk.stuba.fei.uim.asos.user_account.service.interfaces.IUserService;

import java.util.List;

@RestController
@RequestMapping("zadanie-ehealth-api/asos/user")
@CrossOrigin("http://localhost:63343")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("login")
    public ResponseEntity<UserResult> loginUser(@RequestBody RegistrationBody registrationBody) {
        UserResult userResult = userService.login(registrationBody.getEmail(), registrationBody.getPassword());
        return new ResponseEntity<UserResult>(userResult, HttpStatus.OK);
    }

    @PostMapping("registration")
    public ResponseEntity<UserResult> registrationUser(@RequestBody RegistrationBody registrationBody) {
        UserResult userResult = userService.registration(registrationBody.getEmail(), registrationBody.getPassword(),
                registrationBody.getName(), registrationBody.getSurname(), registrationBody.getIsMedic());
        return new ResponseEntity<UserResult>(userResult, HttpStatus.OK);
    }

    @PostMapping("logout/{userId}")
    public ResponseEntity<Void> logoutUser(@PathVariable("userId") Long userId) {
        userService.logout(userId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("authentication/{userId}/{token}")
    public ResponseEntity<AuthenticationBody> tokenAuthentication(@PathVariable("userId") Long userId,
                                                                  @PathVariable("token") String token) {
        AuthenticationBody authenticationBody = new AuthenticationBody();
        authenticationBody.setSuccessfulAuthenticated(userService.tokenAuthentication(userId, token));
        return new ResponseEntity<AuthenticationBody>(authenticationBody, HttpStatus.OK);
    }

    @GetMapping("getUsers/{userId}/{token}")
    public ResponseEntity<List<UserBody>> getUsers(@PathVariable("userId") Long userId,
                                                         @PathVariable("token") String token) {
        List<UserBody> userBodies = userService.getUsers(userId, token);
        return new ResponseEntity<List<UserBody>>(userBodies, HttpStatus.OK);
    }

    @GetMapping("getPatientsUser/{userId}/{token}")
    public ResponseEntity<List<UserBody>> getPatientUser(@PathVariable("userId") Long userId,
                                                              @PathVariable("token") String token) {
        List<UserBody> userBodies = userService.getPatients(userId, token);
        return new ResponseEntity<List<UserBody>>(userBodies, HttpStatus.OK);
    }
}
