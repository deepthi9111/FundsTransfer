package com.deepthi.fundstransfer.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.deepthi.fundstransfer.entity.Account;
import com.deepthi.fundstransfer.entity.Beneficiary;
import com.deepthi.fundstransfer.entity.Customer;
import com.deepthi.fundstransfer.exception.BeneficiaryExistsException;
import com.deepthi.fundstransfer.exception.BeneficiaryNotFoundException;
import com.deepthi.fundstransfer.exception.CustomerNotFoundException;
import com.deepthi.fundstransfer.service.AccountService;
import com.deepthi.fundstransfer.service.BeneficiaryService;
import com.deepthi.fundstransfer.service.CustomerService;

@AutoConfigureTestDatabase(replace=Replace.NONE)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BeneficiaryControllerTest 
{
	@Mock
	CustomerService customerService;
	
	@InjectMocks
	BeneficiaryController beneficiaryController;
	
	@Mock
	BeneficiaryService beneficiaryService;
	
	@Mock
	AccountService accountService;
	
	@Test
	@Order(1)
	@DisplayName("Add Beneficiary : Positive Scenario")
	void testAddBeneficiary() throws BeneficiaryExistsException, CustomerNotFoundException 
	{
		List<Beneficiary> beneficiaries=new ArrayList<Beneficiary>();
		
		Customer customer=new Customer();
		customer.setId(1L);
		customer.setName("Mouni");
		customer.setPhone("7075725533");
		customer.setEmail("amouni1998@gmail.com");
		customer.setPassword("mouni@1234");
		customer.setCity("Kavali");
		
		Account account=new Account();
		account.setAcno(1673190502L);
		account.setIfsc("AX0000500");
		account.setBranch("Hyderabad");
		account.setBalance(1000.0);
		account.setBank("Axis");
		
		customer.setAccount(account);
		
		Beneficiary beneficiary=new Beneficiary();
		beneficiary.setBid(1L);
		beneficiary.setAcno(1673190502L);
		beneficiary.setBank("Axis");
		beneficiary.setBranch("Hyderabad");
		beneficiary.setIfsc("AX0000500");
		beneficiary.setName("Triveni");
		beneficiary.setAccount(account);
		
		when(customerService.getCustomerById(customer.getId())).thenReturn(Optional.of(customer));
		when(beneficiaryService.getAllBeneficiariesByAcno(account.getAcno())).thenReturn(beneficiaries);
		when(accountService.getAccountByAcno(beneficiary.getAcno())).thenReturn(Optional.of(account));
		when(beneficiaryService.addBeneficiary(beneficiary)).thenReturn(beneficiary);
		
		ResponseEntity<String> addBeneficiary = beneficiaryController.addBeneficiary(beneficiary, customer.getId());
	
		assertEquals("Beneficiary has been added successfully", addBeneficiary.getBody());
	}

	@Test
	@Order(2)
	@DisplayName("Add Beneficiary : Negative Scenario")
	void testAddBeneficiary2()
	{
		List<Beneficiary> beneficiaries=new ArrayList<Beneficiary>();
		
		Customer customer=new Customer();
		customer.setId(1L);
		customer.setName("Mouni");
		customer.setPhone("7075725533");
		customer.setEmail("amouni1998@gmail.com");
		customer.setPassword("mouni@1234");
		customer.setCity("Kavali");
		
		Account account=new Account();
		account.setAcno(1673190502L);
		account.setIfsc("AX0000500");
		account.setBranch("Hyderabad");
		account.setBalance(1000.0);
		account.setBank("Axis");
		
		customer.setAccount(account);
		
		Beneficiary beneficiary=new Beneficiary();
		beneficiary.setBid(1L);
		beneficiary.setAcno(1673190502L);
		beneficiary.setBank("Axis");
		beneficiary.setBranch("Hyderabad");
		beneficiary.setIfsc("AX0000500");
		beneficiary.setName("Triveni");
		beneficiary.setAccount(account);
		
		beneficiaries.add(beneficiary);
		
		when(customerService.getCustomerById(customer.getId())).thenReturn(Optional.of(customer));
		when(beneficiaryService.getAllBeneficiariesByAcno(account.getAcno())).thenReturn(beneficiaries);
	
		BeneficiaryExistsException e = assertThrows(BeneficiaryExistsException.class, ()->beneficiaryController.addBeneficiary(beneficiary, customer.getId()));
		
		assertEquals("Beneficiary already added", e.getMessage());
	}

	@Test
	@Order(3)
	@DisplayName("Delete Beneficiary : Positive Scenario")
	void testDeleteBeneficiary() throws BeneficiaryNotFoundException 
	{
		List<Beneficiary> beneficiaries=new ArrayList<Beneficiary>();
		
		Customer customer=new Customer();
		customer.setId(1L);
		customer.setName("Mouni");
		customer.setPhone("7075725533");
		customer.setEmail("amouni1998@gmail.com");
		customer.setPassword("mouni@1234");
		customer.setCity("Kavali");
		
		Account account=new Account();
		account.setAcno(1673190501L);
		account.setIfsc("AX0000500");
		account.setBranch("Hyderabad");
		account.setBalance(1000.0);
		account.setBank("Axis");
		
		customer.setAccount(account);
		
		Account account2=new Account();
		account2.setAcno(1673190502L);
		account2.setIfsc("AX0000500");
		account2.setBranch("Hyderabad");
		account2.setBalance(1000.0);
		account2.setBank("Axis");
		
		Beneficiary beneficiary=new Beneficiary();
		beneficiary.setBid(1L);
		beneficiary.setAcno(1673190502L);
		beneficiary.setBank("Axis");
		beneficiary.setBranch("Hyderabad");
		beneficiary.setIfsc("AX0000500");
		beneficiary.setName("Triveni");
		beneficiary.setAccount(account2);
		
		beneficiaries.add(beneficiary);
	
		when(customerService.getCustomerById(customer.getId())).thenReturn(Optional.of(customer));
		when(beneficiaryService.getAllBeneficiariesByAcno(account.getAcno())).thenReturn(beneficiaries);
		when(beneficiaryService.getBeneficiaryByAcno(account2.getAcno())).thenReturn(beneficiary);
		Mockito.doNothing().when(beneficiaryService).deleteBeneficiary(beneficiary.getBid());
		
		ResponseEntity<String> deleteBeneficiary = beneficiaryController.deleteBeneficiary(beneficiary, customer.getId());
	
		assertEquals("Beneficiary has been deleted successfully", deleteBeneficiary.getBody());
	}

	@Test
	@Order(4)
	@DisplayName("Delete Beneficiary : Positive Scenario")
	void testDeleteBeneficiary2()
	{
		List<Beneficiary> beneficiaries=new ArrayList<Beneficiary>();
		
		Customer customer=new Customer();
		customer.setId(1L);
		customer.setName("Mouni");
		customer.setPhone("7075725533");
		customer.setEmail("amouni1998@gmail.com");
		customer.setPassword("mouni@1234");
		customer.setCity("Kavali");
		
		Account account=new Account();
		account.setAcno(1673190501L);
		account.setIfsc("AX0000500");
		account.setBranch("Hyderabad");
		account.setBalance(1000.0);
		account.setBank("Axis");
		
		customer.setAccount(account);
		
		Account account2=new Account();
		account2.setAcno(1673190502L);
		account2.setIfsc("AX0000500");
		account2.setBranch("Hyderabad");
		account2.setBalance(1000.0);
		account2.setBank("Axis");
		
		Beneficiary beneficiary=new Beneficiary();
		beneficiary.setBid(1L);
		beneficiary.setAcno(1673190502L);
		beneficiary.setBank("Axis");
		beneficiary.setBranch("Hyderabad");
		beneficiary.setIfsc("AX0000500");
		beneficiary.setName("Triveni");
		beneficiary.setAccount(account2);
	
		when(customerService.getCustomerById(customer.getId())).thenReturn(Optional.of(customer));
		when(beneficiaryService.getAllBeneficiariesByAcno(account.getAcno())).thenReturn(beneficiaries);
		
		BeneficiaryNotFoundException e = assertThrows(BeneficiaryNotFoundException.class, ()->beneficiaryController.deleteBeneficiary(beneficiary, customer.getId()));
	
		assertEquals("The beneficiary you want to delete does not exist", e.getMessage());
	}
}
