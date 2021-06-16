package com.deepthi.fundstransfer.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.deepthi.fundstransfer.entity.Transactions;
import com.deepthi.fundstransfer.exception.BeneficiaryNotFoundException;
import com.deepthi.fundstransfer.exception.InSufficientBalanceException;
import com.deepthi.fundstransfer.service.AccountService;
import com.deepthi.fundstransfer.service.BeneficiaryService;
import com.deepthi.fundstransfer.service.CustomerService;
import com.deepthi.fundstransfer.service.TransactionsService;

@AutoConfigureTestDatabase(replace=Replace.NONE)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class FundsControllerTest 
{
	@Mock
	CustomerService customerService;
	
	@InjectMocks
	FundsController fundsController;
	
	@Mock
	BeneficiaryService beneficiaryService;
	
	@Mock
	AccountService accountService;
	
	@Mock
	TransactionsService transactionService;
	
	@Test
	@Order(1)
	@DisplayName("Funds Transfer : Negative Scenario1")
	void testTransferFunds() 
	{		
		Account account=new Account(1673190501L,"AX0000500","Hyderabad",100.0,"Axis");

		when(accountService.getAccountByAcno(account.getAcno())).thenReturn(Optional.of(account));
		
		InSufficientBalanceException e = assertThrows(InSufficientBalanceException.class, ()->fundsController.transferFunds(account.getAcno(),1673190500L,800.0));
		
		assertEquals("Transaction Failed...Insufficient balance", e.getMessage());
	}

	@Test
	@Order(2)
	@DisplayName("Funds Transfer : Negative Scenario2")
	void testTransferFunds2() 
	{
		List<Beneficiary> beneficiaries=new ArrayList<>();
		
		Account account=new Account();
		account.setAcno(1673190501L);
		account.setIfsc("AX0000500");
		account.setBranch("Hyderabad");
		account.setBalance(1000.0);
		account.setBank("Axis");
			
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
		beneficiary.setAccount(account);
		
		when(accountService.getAccountByAcno(account.getAcno())).thenReturn(Optional.of(account));
		when(accountService.getAccountByAcno(account2.getAcno())).thenReturn(Optional.of(account2));
		when(beneficiaryService.getAllBeneficiariesByAcno(account.getAcno())).thenReturn(beneficiaries);
		
		BeneficiaryNotFoundException e = assertThrows(BeneficiaryNotFoundException.class, ()->fundsController.transferFunds(account.getAcno(), account2.getAcno(),600.0));
		
		assertEquals("Please make a transaction to your beneficiary accounts only...", e.getMessage());
	}
	
	@Test
	@Order(3)
	@DisplayName("Funds Transfer : Positive Scenario")
	void testTransferFunds3() throws InSufficientBalanceException,BeneficiaryNotFoundException
	{
		List<Beneficiary> beneficiaries=new ArrayList<>();
		
		Account sender=new Account();
		sender.setAcno(1673190501L);
		sender.setIfsc("AX0000500");
		sender.setBranch("Hyderabad");
		sender.setBalance(1000.0);
		sender.setBank("Axis");
		
		Account receiver=new Account();
		receiver.setAcno(1673190502L);
		receiver.setIfsc("AX0000500");
		receiver.setBranch("Hyderabad");
		receiver.setBalance(1000.0);
		receiver.setBank("Axis");
		
		Beneficiary beneficiary=new Beneficiary();
		beneficiary.setBid(1L);
		beneficiary.setAcno(1673190502L);
		beneficiary.setBank("Axis");
		beneficiary.setBranch("Hyderabad");
		beneficiary.setIfsc("AX0000500");
		beneficiary.setName("Triveni");
		beneficiary.setAccount(sender);
		
		beneficiaries.add(beneficiary);
		
		Double amount=500.0;
		LocalDateTime time= LocalDateTime.now();
		Transactions transaction=new Transactions(1L,sender.getAcno(),receiver.getAcno(),amount,time);
		transaction.setAmount(amount);
		transaction.setReceiver(receiver.getAcno());
		transaction.setSender(sender.getAcno());
		transaction.setTid(1L);
		transaction.setTime(time);
		
		when(accountService.getAccountByAcno(sender.getAcno())).thenReturn(Optional.of(sender));
		when(accountService.getAccountByAcno(receiver.getAcno())).thenReturn(Optional.of(receiver));
		when(beneficiaryService.getAllBeneficiariesByAcno(sender.getAcno())).thenReturn(beneficiaries);
		when(accountService.updateAccount(sender)).thenReturn(sender);
		when(accountService.updateAccount(receiver)).thenReturn(receiver);
		when(transactionService.addTransaction(sender,receiver,amount)).thenReturn(transaction);
		
		ResponseEntity<String> transferFunds = fundsController.transferFunds(sender.getAcno(),receiver.getAcno(),amount);
		
		assertTrue(transferFunds.getStatusCodeValue()==200);
	}
	
	
	@Test
	@Order(4)
	@DisplayName("Update Balance")
	void testUpdateBalance() 
	{
		Double amount=2000.0;
		
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
		
		when(customerService.getCustomerById(customer.getId())).thenReturn(Optional.of(customer));
		when(accountService.updateAccount(account)).thenReturn(account);
		
		ResponseEntity<String> updateBalance = fundsController.updateBalance(amount, customer.getId());
		assertTrue(updateBalance.getStatusCodeValue()==200);
	}
	
	@Test
	@DisplayName("Check Account : Positive Scenario")
	void testCheckAccount()
	{
		Account account=new Account();
		account.setAcno(1673190501L);
		account.setIfsc("AX0000500");
		account.setBranch("Hyderabad");
		account.setBalance(1000.0);
		account.setBank("Axis");
		
		when(accountService.getAccountByAcno(account.getAcno())).thenReturn(Optional.of(account));
		
		ResponseEntity<String> checkAccount = fundsController.checkAccount(account.getAcno());
		assertTrue(checkAccount.getStatusCodeValue()==200);
	}
	
	@Test
	@DisplayName("Check Account : Negative Scenario")
	void testCheckAccount2()
	{
		Account account=new Account();
		account.setAcno(1673190501L);
		account.setIfsc("AX0000500");
		account.setBranch("Hyderabad");
		account.setBalance(1000.0);
		account.setBank("Axis");
		
		when(accountService.getAccountByAcno(account.getAcno())).thenReturn(Optional.empty());
		
		ResponseEntity<String> checkAccount = fundsController.checkAccount(account.getAcno());
		assertTrue(checkAccount.getStatusCodeValue()==404);
	}

}
