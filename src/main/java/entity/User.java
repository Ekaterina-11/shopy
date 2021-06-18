package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String patronymic;
    private String address;
    private String password;
    private String confirmPassword;
    private String activationcode;
    @Enumerated(EnumType.STRING)
    private Role role;
}
