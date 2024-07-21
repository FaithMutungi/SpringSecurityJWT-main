package com.crackit.SpringSecurityJWT.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

public enum Role {
  ADMIN,
  MEMBER;

  public List<SimpleGrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
  }
}
