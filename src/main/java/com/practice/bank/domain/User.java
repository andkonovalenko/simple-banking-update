package com.practice.bank.domain;

import com.practice.bank.domain.enumerations.UserRole;
import com.practice.bank.domain.enumerations.UserStatus;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "bank_user")
public class User extends BaseEntity{

    @NotBlank(message = "Enter your name!")
    @Length(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    @Column(name = "name", nullable = false)
    private String userName;

    @NotBlank
    @Email(message = "Enter valid e-mail!")
    @Column(name = "e_mail", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Enter password!")
    @Length(min = 4)
    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<UserRole> userRoles;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BankAccount> bankAccounts;
}
