package com.deepthi.fundstransfer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepthi.fundstransfer.entity.Customer;
import com.deepthi.fundstransfer.repository.CustomerRepository;

@Service
public class CustomerService 
{
	@Autowired
	CustomerRepository customerRepository;
	
	public Customer getCustomerByEmail(String email) 
	{
		return customerRepository.findByEmail(email);
	}

	public Customer addCustomer(Customer customer) 
	{
		return customerRepository.save(customer);
	}

	public Optional<Customer> getCustomerById(Long id) 
	{
		return customerRepository.findById(id);
	}

}
