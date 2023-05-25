package com.example.demo.enumeration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserAuthority {
    ADMIN,
    USER;

    public GrantedAuthority asAuthority() {
        return new SimpleGrantedAuthority(this.toString());
    }
}
