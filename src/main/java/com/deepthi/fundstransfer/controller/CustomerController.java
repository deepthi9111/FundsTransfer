package com.deepthi.fundstransfer.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepthi.fundstransfer.entity.Account;
import com.deepthi.fundstransfer.entity.Beneficiary;
import com.deepthi.fundstransfer.entity.Customer;
import com.deepthi.fundstransfer.entity.CustomerLogin;
import com.deepthi.fundstransfer.exception.CustomerNotFoundException;
import com.deepthi.fundstransfer.exception.DuplicateEntryException;
import com.deepthi.fundstransfer.service.AccountService;
import com.deepthi.fundstransfer.service.BeneficiaryService;
import com.deepthi.fundstransfer.service.CustomerService;

@RestController
public class CustomerController 
{
	static Logger log = LoggerFactory.getLogger(CustomerController.class.getName());
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	BeneficiaryService beneficiaryService;
	
	@Autowired
	AccountService accountService;
	
	@PostMapping("/customers")
	public ResponseEntity<String> addCustomer(@Valid @RequestBody Customer customer) throws DuplicateEntryException
	{
		String message="";
		
		if(getCustomerByEmail(customer.getEmail()) != null)
		{
			log.error("Customer already exists");
			throw new DuplicateEntryException("Customer already exists");
		}
		else
		{
			
			Account account = accountService.createAccount();
			
			customer.setAccount(account);
			
			message="Registered Successfully";
			log.info("Customer registered successfully");
			customerService.addCustomer(customer);
			
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
	}

	@GetMapping("/customers/auth")
	public ResponseEntity<String> logCustomer(@RequestBody CustomerLogin customerLogin) throws CustomerNotFoundException
	{	
		StringBuilder message = new StringBuilder();
		
		Customer customer = getCustomerByEmail(customerLogin.getEmail());
		
		if(customer==null || !(customer.getPassword().equals(customerLogin.getPassword())))
		{
			log.error("incorrect Email or password");
			throw new CustomerNotFoundException("Customer doesn't exist...Enter correct Email or Password");
		}
		else
		{
			Account account = customer.getAccount();
			
			List<Beneficiary> beneficiaries = beneficiaryService.getAllBeneficiariesByAcno(account.getAcno());
			
			log.info("Logged in successfully");
			message.append("Logged in successfully\n\n");
			message.append("User Id : "+customer.getId()+
							"\nAccount Number : "+account.getAcno()+
							"\nIFSC Code : "+account.getIfsc()+
							"\nBranch : "+account.getBranch()+
							"\nBalance : "+account.getBalance()
							+"\n\nFollowing are your beneficiary accounts(Name-AcNo-IFSC Code-Bank-Branch)\n");
			
			if(beneficiaries.isEmpty())
			{
				message.append("Empty");
			}
			else
			{
				int sno=1;
				for(Beneficiary beneficiary:beneficiaries)
				{
					message.append(sno+". "+beneficiary.getName()+" - "+beneficiary.getAcno()+" - "+
											 beneficiary.getIfsc()+" - "+beneficiary.getBank()+" - "+
											 beneficiary.getBranch()+"\n");
					sno++;
				}
			}
			
			return new ResponseEntity<>(message.toString(), HttpStatus.OK);
			
		}
	}
	
	private Customer getCustomerByEmail(String email) 
	{
		return customerService.getCustomerByEmail(email);
	}
	
}
