package dev.fringe.oauth2.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements UserDetails {

    private static final long serialVersionUID = -5592668947749607490L;

    public static UserDetails create(String username, String password, String... authorities) {
        return new User(username, password, authorities);
    }

    private final String username;
    private final String password;
    private final Collection<GrantedAuthority> authorities;

    @SuppressWarnings("unchecked")
    private User(String username, String password) {
        this(username, password, Collections.EMPTY_LIST);
    }

    private User(String username, String password, String... authorities) {
        this(username, password, AuthorityUtils.createAuthorityList(authorities));
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
