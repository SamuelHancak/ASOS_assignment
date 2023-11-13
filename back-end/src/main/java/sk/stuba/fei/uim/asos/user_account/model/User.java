package sk.stuba.fei.uim.asos.user_account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String token;

    private String salt;

    private String name;

    private String surname;

    private Boolean isMedic;

    private String symmetricKey;

    private String asymmetricPrivateKey;

    private String asymmetricPublicKey;
}
