package dev.fringe.oauth2.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dev.fringe.oauth2.model.Customer;

@Repository
public class CustomerService {

	private Map<Long, Customer> customers = new HashMap<Long, Customer>();
	
	public CustomerService() {
		customers.put(1L, new Customer("Kiran", "Bhat", "kd@mt.com", 1L));
	}

	public List<Customer> getCustomers(){
		return Arrays.asList(customers.values().toArray(new Customer[customers.values().size()]));
	}
	
	public Customer getCustomer(Long id) {
		return customers.get(id);
	}
	
	public Customer addCustomer(Customer customer) {
		customers.put(customer.getId(), customer);
		return customer;
	}
}
