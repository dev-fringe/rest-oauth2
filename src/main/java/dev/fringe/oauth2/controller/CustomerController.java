package dev.fringe.oauth2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.fringe.oauth2.model.Customer;
import dev.fringe.oauth2.service.CustomerService;

@RestController
public class CustomerController {
	
	private static Long ID_VAR = 1L;
	
	private static Long getID() {
		return ++ID_VAR;
	}
	
	@Autowired
	private CustomerService customerDAO;
	
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		return customerDAO.getCustomers();
	}
	
	@GetMapping("/customers/{id}")
	public ResponseEntity<?> getCustomer(@PathVariable("id") Long id){
		Customer customer = customerDAO.getCustomer(id);
		if(customer == null) {
			return new ResponseEntity<String>("Customer does not exist with id : " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@PostMapping("/customers")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
		customer.setId(getID());
		Customer addedCustomer = customerDAO.addCustomer(customer);
		return new ResponseEntity<Customer>(addedCustomer, HttpStatus.CREATED);
	}
	
}
