package com.practice.bank.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.practice.bank.domain.BankAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class JwtUser implements UserDetails {

    private final Long id;

    private final String userName;

    private final String email;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private final Boolean enabled;

    private final List<BankAccount> bankAccounts;

    public JwtUser(Long id,
                   String userName,
                   String email,
                   String password,
                   Collection<? extends GrantedAuthority> authorities,
                   Boolean enabled,
                   List<BankAccount> bankAccounts) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.bankAccounts = bankAccounts;
    }

    public String getEmail() {
        return email;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
