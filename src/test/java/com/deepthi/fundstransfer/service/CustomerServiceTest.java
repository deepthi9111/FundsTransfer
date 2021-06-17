package com.deepthi.fundstransfer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import com.deepthi.fundstransfer.entity.Customer;
import com.deepthi.fundstransfer.repository.CustomerRepository;

@AutoConfigureTestDatabase(replace=Replace.NONE)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest 
{
	@Mock
	CustomerRepository customerRepository;
	
	@InjectMocks
	CustomerService customerService;
	
	@Test
	void testGetCustomerByEmail() 
	{
		Customer customer=new Customer();
		customer.setId(1L);
		customer.setName("Mounika");
		customer.setPhone("8985478597");
		customer.setEmail("mounika@gmail.com");
		customer.setPassword("mouni@1234");
		customer.setCity("Hyderabad");
		
		when(customerRepository.findByEmail(customer.getEmail())).thenReturn(customer);
		
		Customer customerByEmail = customerService.getCustomerByEmail(customer.getEmail());	
		
		assertTrue(customerByEmail.getId()==customer.getId());
	}

	@Test
	void testAddCustomer() 
	{
		Customer customer=new Customer();
		customer.setId(1L);
		customer.setName("Mounika");
		customer.setPhone("8985478597");
		customer.setEmail("mounika@gmail.com");
		customer.setPassword("mouni@1234");
		customer.setCity("Hyderabad");
		
		when(customerRepository.save(customer)).thenReturn(customer);
		
		Customer addCustomer = customerService.addCustomer(customer);
		
		assertTrue(addCustomer.getId()==customer.getId());
	}

	@Test
	void testGetCustomerById() 
	{
		Customer customer=new Customer();
		customer.setId(1L);
		customer.setName("Mounika");
		customer.setPhone("8985478597");
		customer.setEmail("mounika@gmail.com");
		customer.setPassword("mouni@1234");
		customer.setCity("Hyderabad");
		
		when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
		
		Optional<Customer> customerById = customerService.getCustomerById(customer.getId());
		
		assertTrue(customerById.isPresent());
	}

}
