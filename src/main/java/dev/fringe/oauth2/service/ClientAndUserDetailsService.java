package dev.fringe.oauth2.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;

/**
 * A class that combines a UserDetailsService and ClientDetailsService into a
 * single object.
 * 
 * @author jules
 * 
 * This code was provided by Jules White.
 */
public class ClientAndUserDetailsService implements UserDetailsService, ClientDetailsService {

    private final ClientDetailsService clients;
    private final UserDetailsService users;
    private final ClientDetailsUserDetailsService clientDetailsWrapper;

    public ClientAndUserDetailsService(ClientDetailsService clients, UserDetailsService users) {
        this.clients = clients;
        this.users = users;
        this.clientDetailsWrapper = new ClientDetailsUserDetailsService(clients);
    }

    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    	System.out.println(clients.loadClientByClientId(clientId).getAuthorities());
    	System.out.println(clients.loadClientByClientId(clientId).getAuthorizedGrantTypes());
        return clients.loadClientByClientId(clientId);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = null;
        try {
            user = users.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            user = clientDetailsWrapper.loadUserByUsername(username);
        }
        return user;
    }

}
