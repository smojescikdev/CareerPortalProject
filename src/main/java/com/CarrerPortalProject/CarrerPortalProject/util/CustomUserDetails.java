package com.CarrerPortalProject.CarrerPortalProject.util;

import com.CarrerPortalProject.CarrerPortalProject.model.Users;
import com.CarrerPortalProject.CarrerPortalProject.model.UsersType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {


    private final Users user;

    public CustomUserDetails(Users user) {
        this.user = user;
    }

    //sprawdzenie roli/typu uzytkownika
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UsersType userTypeId = user.getUserTypeId();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userTypeId.getUserTypeName()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return true;
    }
}
