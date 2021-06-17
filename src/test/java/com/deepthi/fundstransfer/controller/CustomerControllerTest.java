package com.deepthi.fundstransfer.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.deepthi.fundstransfer.entity.Account;
import com.deepthi.fundstransfer.entity.Beneficiary;
import com.deepthi.fundstransfer.entity.Customer;
import com.deepthi.fundstransfer.entity.CustomerLogin;
import com.deepthi.fundstransfer.exception.CustomerNotFoundException;
import com.deepthi.fundstransfer.exception.DuplicateEntryException;
import com.deepthi.fundstransfer.service.AccountService;
import com.deepthi.fundstransfer.service.BeneficiaryService;
import com.deepthi.fundstransfer.service.CustomerService;

@AutoConfigureTestDatabase(replace=Replace.NONE)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest 
{
	
	@Mock
	CustomerService customerService;
	
	@InjectMocks
	CustomerController customerController;
	
	@Mock
	BeneficiaryService beneficiaryService;
	
	@Mock
	AccountService accountService;
	
	@Test
	@Order(1)
	@DisplayName("Registration : Positive scenario")
	void testAddCustomer() throws DuplicateEntryException 
	{
		Customer customer=new Customer(1L,"Mounika","8985478597","Hyderabad","mounika@gmail.com","mouni@1234");
		
		Account account=new Account();
		account.setAcno(1673190501L);
		account.setIfsc("AX00000101");
		account.setBranch("Kavali");
		account.setBalance(1000.0);
		account.setBank("Axis");
		
		customer.setAccount(account);
		
		when(customerService.getCustomerByEmail(customer.getEmail())).thenReturn(null);
		when(customerService.addCustomer(customer)).thenReturn(customer);
		when(accountService.createAccount()).thenReturn(account);
		
		ResponseEntity<String> addUser = customerController.addCustomer(customer);
		
		assertEquals("Registered Successfully", addUser.getBody());
	}

	@Test
	@Order(2)
	@DisplayName("Registration : Negative Scenario")
	void testAddCustomer2() 
	{
		Customer customer=new Customer();
		customer.setId(1L);
		customer.setName("Mounika");
		customer.setPhone("8985478597");
		customer.setEmail("mounika@gmail.com");
		customer.setPassword("mouni@1234");
		customer.setCity("Hyderabad");
		
		when(customerService.getCustomerByEmail(customer.getEmail())).thenReturn(customer);
		
		DuplicateEntryException e = assertThrows(DuplicateEntryException.class, ()->customerController.addCustomer(customer));
		
		assertEquals("Customer already exists", e.getMessage());
	}
	
	@Test
	@Order(3)
	@DisplayName("Login : Positive Scenario")
	void testLogCustomer() throws CustomerNotFoundException
	{
		List<Beneficiary> beneficiaries=new ArrayList<Beneficiary>();
		CustomerLogin login=new CustomerLogin("amouni1998@gmail.com","mouni@1234");
		
		Customer customer=new Customer();
		customer.setId(1L);
		customer.setName("Mounika");
		customer.setPhone("8985478597");
		customer.setEmail("mounika@gmail.com");
		customer.setPassword("mouni@1234");
		customer.setCity("Hyderabad");
		
		Account account=new Account();
		account.setAcno(1673190501L);
		account.setIfsc("AX00000101");
		account.setBranch("Kavali");
		account.setBalance(1000.0);
		account.setBank("Axis");
	
		customer.setAccount(account);
		
		Beneficiary beneficiary=new Beneficiary(1L,account,1673190502L,"AX0000500","Hyderabad","Axis","Triveni");
		
		beneficiaries.add(beneficiary);
		
		when(customerService.getCustomerByEmail(login.getEmail())).thenReturn(customer);
		when(beneficiaryService.getAllBeneficiariesByAcno(account.getAcno())).thenReturn(beneficiaries);
		
		ResponseEntity<String> logCustomer = customerController.logCustomer(login);
	
		assertTrue(logCustomer.getStatusCodeValue()==200);
		
	}
	
	@Test
	@Order(4)
	@DisplayName("Login : Negative Scenario")
	void testLogCustomer2()
	{
		
		CustomerLogin login=new CustomerLogin();
		login.setEmail("mounika@gmail.com");
		login.setPassword("mouni@1234");
		
		Customer customer=null;

		when(customerService.getCustomerByEmail(login.getEmail())).thenReturn(customer);
		
		Assertions.assertThrows(CustomerNotFoundException.class, ()->customerController.logCustomer(login));
	
		//assertEquals("Customer doesn't exist...Enter correct Email or Password", e.getMessage());
	}
}
