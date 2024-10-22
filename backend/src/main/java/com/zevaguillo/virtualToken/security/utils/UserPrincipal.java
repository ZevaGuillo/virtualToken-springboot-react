package com.zevaguillo.virtualToken.security.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.zevaguillo.virtualToken.persistence.entity.UserEntity;

import lombok.Getter;

public class UserPrincipal implements UserDetails {
    @Getter
    private Long id;
    private String username;
    @Getter
    private String email;
    private String password;
    private boolean isEnabled;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialNoExpired;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String username, String email, String password, boolean isEnabled,
                         boolean accountNoExpired, boolean accountNoLocked, boolean credentialNoExpired,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.accountNoExpired = accountNoExpired;
        this.accountNoLocked = accountNoLocked;
        this.credentialNoExpired = credentialNoExpired;
        if (authorities == null) {
            this.authorities = null;
        } else {
            this.authorities = new ArrayList<>(authorities);
        }
    }


    public static UserPrincipal create(UserEntity userEntity) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new UserPrincipal(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities == null ? null : new ArrayList<>(authorities);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNoExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNoLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNoExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
