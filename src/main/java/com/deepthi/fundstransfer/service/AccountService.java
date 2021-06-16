package com.deepthi.fundstransfer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepthi.fundstransfer.entity.Account;
import com.deepthi.fundstransfer.repository.AccountRepository;

@Service
@Transactional
public class AccountService 
{

	@Autowired
	AccountRepository accountRepository;
	
	public Account createAccount() 
	{
		Account account=new Account();
		account.setBranch("Hyderabad");
		account.setIfsc("AX0000101");
		account.setBank("Axis");
		account.setBalance(1000.0);
		
		return accountRepository.save(account);
	}

	public Optional<Account> getAccountByAcno(Long acno) 
	{
		System.out.println("In service "+acno);
		return accountRepository.findById(acno);
	}

	public Account updateAccount(Account account) 
	{
		return accountRepository.save(account);
	}

}
