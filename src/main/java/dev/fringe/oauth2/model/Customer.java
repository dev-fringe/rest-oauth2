package dev.fringe.oauth2.model;

import lombok.Data;

@Data
public class Customer {
	
	private String firstName;
	private String lastName;
	private String email;
	private Long id;
	
	public Customer(){
	}
	
	public Customer(String firstName, String lastName, String email, Long id) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.id = id;
	}
}
