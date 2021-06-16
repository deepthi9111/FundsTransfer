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

import com.deepthi.fundstransfer.entity.Account;
import com.deepthi.fundstransfer.repository.AccountRepository;

@AutoConfigureTestDatabase(replace=Replace.NONE)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class AccountServiceTest 
{
	@Mock
	AccountRepository accountRepository;
	
	@InjectMocks
	AccountService accountService;

	@Test
	void testGetAccountByAcno() 
	{
		Account account=new Account();
		account.setAcno(1673190501L);
		account.setIfsc("AX0000500");
		account.setBranch("Hyderabad");
		account.setBalance(1000.0);
		account.setBank("Axis");
		
		when(accountRepository.findById(account.getAcno())).thenReturn(Optional.of(account));
		
		Optional<Account> accountByAcno = accountService.getAccountByAcno(account.getAcno());
		
		assertTrue(accountByAcno.isPresent());
	}

	@Test
	void testUpdateAccount() 
	{
		Account account=new Account();
		account.setAcno(1673190501L);
		account.setIfsc("AX0000500");
		account.setBranch("Hyderabad");
		account.setBalance(1000.0);
		account.setBank("Axis");
		
		when(accountRepository.save(account)).thenReturn(account);
		
		Account updateAccount = accountService.updateAccount(account);
		
		assertTrue(updateAccount.getAcno()==account.getAcno());
	}

}
