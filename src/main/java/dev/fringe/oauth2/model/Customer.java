package dev.fringe.oauth2.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {
	
	private String firstName,lastName,email;
	private Long id;
	
	public Customer(String firstName, String lastName, String email, Long id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.id = id;
	}
}
