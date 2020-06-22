package dev.fringe.oauth2.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class User extends org.springframework.security.core.userdetails.User {
	
	private static final long serialVersionUID = 8777447718328694727L;
	private String lastname;

	public User(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			String lastname) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.lastname = lastname;
	}

	public User(String username, String password, Collection<? extends GrantedAuthority> authorities, String lastname) {
		this(username, password, true, true, true, true, authorities, lastname);
	}

	public String getLastname() {
		return lastname;
	}
}