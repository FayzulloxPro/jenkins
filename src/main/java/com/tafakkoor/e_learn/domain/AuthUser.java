package com.tafakkoor.e_learn.domain;

import com.tafakkoor.e_learn.enums.Levels;
import com.tafakkoor.e_learn.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class AuthUser extends Auditable {
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Image image;
    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp="^\\S+$", message="Username cannot contain whitespace")
    @Column(unique = true, nullable = false)
    private String username;
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp="^\\S+$", message="Password cannot contain whitespace")
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z]).{8,}$", message="Choose a stronger password")
    @Column(nullable = false)
    private String password;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "auth_user_roles",
            joinColumns = @JoinColumn(name = "auth_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "auth_role_id", referencedColumnName = "id")
    )
    private Collection<AuthRole> authRoles;
    @Builder.Default
    private Status status = Status.INACTIVE;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Levels level = Levels.DEFAULT;
    private LocalDate birthDate; // TODO: 3/12/23 congratulation message on birthday
    private LocalDateTime lastLogin; // TODO: 3/12/23 send email to user if the user didn't login for 3 days
    @Column(nullable = false, columnDefinition = "integer default 0")
    @Builder.Default
    private Integer score = 0;

}
