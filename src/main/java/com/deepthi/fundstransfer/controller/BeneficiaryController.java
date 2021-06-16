package com.deepthi.fundstransfer.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepthi.fundstransfer.entity.Account;
import com.deepthi.fundstransfer.entity.Beneficiary;
import com.deepthi.fundstransfer.entity.Customer;
import com.deepthi.fundstransfer.exception.BeneficiaryExistsException;
import com.deepthi.fundstransfer.exception.BeneficiaryNotFoundException;
import com.deepthi.fundstransfer.exception.CustomerNotFoundException;
import com.deepthi.fundstransfer.service.AccountService;
import com.deepthi.fundstransfer.service.BeneficiaryService;
import com.deepthi.fundstransfer.service.CustomerService;

@RestController
public class BeneficiaryController 
{
	static Logger log = LoggerFactory.getLogger(BeneficiaryController.class.getName());
	
	@Autowired
	BeneficiaryService beneficiaryService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	AccountService accountService;
	
	@PostMapping("/customers/{id}/beneficiaries")
	public ResponseEntity<String> addBeneficiary(@RequestBody Beneficiary beneficiary,
												 @PathVariable Long id) throws BeneficiaryExistsException, CustomerNotFoundException
	{
		Optional<Customer> customerById = customerService.getCustomerById(id);
		
		Customer customer=new Customer();
		
		if(customerById.isPresent())
		{
			customer = customerById.get();
		}
		
		Account custAccount=customer.getAccount();
		
		List<Beneficiary> beneficiariesList = beneficiaryService.getAllBeneficiariesByAcno(custAccount.getAcno());
		
		log.info("checking if the beneficiary is already added");
		for(Beneficiary beneficiaries:beneficiariesList)
		{
			if(beneficiaries.getAcno().equals(beneficiary.getAcno()))
			{
				throw new BeneficiaryExistsException("Beneficiary already added");
			}
				
		}
		
		Optional<Account> accountByAcno = getAccountByAcno(beneficiary.getAcno());
		
		Account acntBeneficiary=new Account();
		
		if(accountByAcno.isPresent())
		{
			acntBeneficiary=accountByAcno.get();
		}
		
		log.error("Bank details are incorrect");
		if(acntBeneficiary == null || !acntBeneficiary.getIfsc().equals(beneficiary.getIfsc()) || 
				   !acntBeneficiary.getBank().equals(beneficiary.getBank()) || 
				   !acntBeneficiary.getBranch().equals(beneficiary.getBranch()))
		{
			throw new CustomerNotFoundException("The bank details you entered are incorrect");
		}
			
		else
		{
			beneficiary.setAccount(custAccount);
			beneficiaryService.addBeneficiary(beneficiary);
			
			log.info("Beneficiary added");
			
			String message="Beneficiary has been added successfully";
			
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
	}

	@DeleteMapping("/customers/{id}/beneficiaries")
	public ResponseEntity<String> deleteBeneficiary(@RequestBody Beneficiary beneficiary, @PathVariable Long id) throws BeneficiaryNotFoundException
	{
		Optional<Customer> customerById = customerService.getCustomerById(id);
		
		Customer customer=new Customer();
		
		if(customerById.isPresent())
		{
			customer = customerById.get();
		}
		
		Account account = customer.getAccount();

		List<Beneficiary> beneficiariesList = beneficiaryService.getAllBeneficiariesByAcno(account.getAcno());
		
		int notEqual=0;
		
		for(Beneficiary beneficiaries: beneficiariesList)
		{
			if(!beneficiaries.getAcno().equals(beneficiary.getAcno()))
			{
				notEqual++;
			}
		}
		
		log.warn("can't delete non-beneficiary account");
		if(notEqual==beneficiariesList.size())
		{
			throw new BeneficiaryNotFoundException("The beneficiary you want to delete does not exist");
		}
			
		
		beneficiaryService.deleteBeneficiary(getBeneficiaryByAcno(beneficiary.getAcno()).getBid());
		
		log.info("beneficiary deleted");
		String message="Beneficiary has been deleted successfully";
		
		return new ResponseEntity<>(message, HttpStatus.OK);
		
	}
	
	private Beneficiary getBeneficiaryByAcno(Long acno) 
	{
		return beneficiaryService.getBeneficiaryByAcno(acno);
	}
	
	public Optional<Account> getAccountByAcno(Long acno) 
	{
		return accountService.getAccountByAcno(acno);
		
	}

}
