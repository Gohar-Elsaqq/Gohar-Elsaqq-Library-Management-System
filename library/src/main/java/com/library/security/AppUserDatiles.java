package com.library.security;

import com.library.entity.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class AppUserDatiles implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    List<GrantedAuthority> grantedAuthorities;
    public AppUserDatiles() {
        super();
    }
    public AppUserDatiles(AppUser appUser) {
        super();
        this.id = appUser.getId();
        this.username = appUser.getUserName();
        this.password =  appUser.getPassword();
        this.fullName = appUser.getFullName();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(!appUser.getRoles().isEmpty()){
            appUser.getRoles().forEach(role -> {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            });
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
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
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
//        return UserDetails.super.isAccountNonLocked();
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
