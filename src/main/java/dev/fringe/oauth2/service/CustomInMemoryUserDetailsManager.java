package dev.fringe.oauth2.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import dev.fringe.oauth2.model.User;

@Component
public class CustomInMemoryUserDetailsManager implements UserDetailsService {
	private Map<String, User> users = new HashMap<String, User>();

	public CustomInMemoryUserDetailsManager(Collection<User> users) {
		for (User user : users) {
			this.users.put(user.getUsername().toLowerCase(), user);
		}
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = users.get(username.toLowerCase());
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		User userNew = new User(user.getUsername(), user.getPassword(), user.getAuthorities(), user.getLastname());

		return userNew;
	}
}
