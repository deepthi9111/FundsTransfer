package com.deepthi.fundstransfer.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepthi.fundstransfer.entity.Account;
import com.deepthi.fundstransfer.entity.Beneficiary;
import com.deepthi.fundstransfer.entity.Customer;
import com.deepthi.fundstransfer.exception.BeneficiaryNotFoundException;
import com.deepthi.fundstransfer.exception.InSufficientBalanceException;
import com.deepthi.fundstransfer.service.AccountService;
import com.deepthi.fundstransfer.service.BeneficiaryService;
import com.deepthi.fundstransfer.service.CustomerService;
import com.deepthi.fundstransfer.service.TransactionsService;

@RestController
public class FundsController 
{
	static Logger log = LoggerFactory.getLogger(FundsController.class.getName());
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	BeneficiaryService beneficiaryService;
	
	@Autowired
	TransactionsService transactionsService;
	
	@Autowired
	CustomerService customerService;
	
	@PutMapping("/customers/funds/{facno}/{tacno}/{amount}")
	public ResponseEntity<String> transferFunds(@PathVariable Long facno, @PathVariable Long tacno, @PathVariable Double amount) 
			throws InSufficientBalanceException, 
				   BeneficiaryNotFoundException
	{
		StringBuilder message=new StringBuilder();
		
		Optional<Account> accountByAcno = accountService.getAccountByAcno(facno);
		Account senderAccount=new Account();
		
		if(accountByAcno.isPresent())
		{
			senderAccount=accountByAcno.get();
		}

		if(senderAccount.getBalance()<amount)
		{
			log.error("Transaction failed");
			throw new InSufficientBalanceException("Transaction Failed...Insufficient balance");
		}
			
		else
		{
			Optional<Account> accountByAcno2 = accountService.getAccountByAcno(tacno);
			
			Account receiverAccount=new Account();

			if(accountByAcno2.isPresent())
			{
				receiverAccount=accountByAcno2.get();
			}
			
			List<Beneficiary> beneficiariesList = beneficiaryService.getAllBeneficiariesByAcno(senderAccount.getAcno());
		
			int notEqual=0;
			
			for(Beneficiary beneficiary: beneficiariesList)
			{
				if(!beneficiary.getAcno().equals(receiverAccount.getAcno()))
				{
					notEqual++;
				}
			}
			
			log.warn("Can't make transactions to non-beneficiary accounts");
			if(notEqual==beneficiariesList.size())
				throw new BeneficiaryNotFoundException("Please make a transaction to your beneficiary accounts only...");
			
			
			senderAccount.setBalance(senderAccount.getBalance()-amount);
	
			receiverAccount.setBalance(receiverAccount.getBalance()+amount);
		
			
			accountService.updateAccount(senderAccount);
			accountService.updateAccount(receiverAccount);
			
			log.info("funds transferred");
			
			message.append("Funds has been transferred succesfully"
					+"\n\nUpdated Balance : "+senderAccount.getBalance());
			
			
			transactionsService.addTransaction(senderAccount,receiverAccount,amount);
			
			return new ResponseEntity<>(message.toString(),HttpStatus.OK);
		}
	}
	
	@PutMapping("/customers/{id}/balance")
	public ResponseEntity<String> updateBalance(@RequestParam Double balance, @PathVariable Long id)
	{
		StringBuilder message=new StringBuilder();
		
		Optional<Customer> customerById = customerService.getCustomerById(id);
		
		Customer sender=new Customer();
		
		if(customerById.isPresent())
		{
			sender = customerById.get();
		}
		
		Account account=sender.getAccount();
		
		account.setBalance(account.getBalance()+balance);
		
		accountService.updateAccount(account);
		
		message.append("Balance has been updated successfully\nUpdatedBalance : "+account.getBalance());
		
		return new ResponseEntity<>(message.toString(), HttpStatus.OK);
		
	}
	
	@GetMapping("/customers/{acno}")
	public ResponseEntity<String> checkAccount(@PathVariable Long acno)
	{
		StringBuilder message=new StringBuilder();
		
		Optional<Account> accountByAcno = accountService.getAccountByAcno(acno);
		
		if(accountByAcno.isPresent())
		{
			System.out.println("In funds controller "+acno);
			message.append("Present");
			return new ResponseEntity<>(message.toString(),HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/funds/api")
	public ResponseEntity<String> sample()
	{
		String message="OK";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
}
