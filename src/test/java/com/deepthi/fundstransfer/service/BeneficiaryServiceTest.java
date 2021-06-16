package com.deepthi.fundstransfer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import com.deepthi.fundstransfer.entity.Beneficiary;
import com.deepthi.fundstransfer.repository.BeneficiaryRepository;

@AutoConfigureTestDatabase(replace=Replace.NONE)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BeneficiaryServiceTest 
{

	@Mock
	BeneficiaryRepository beneficiaryRepository;
	
	@InjectMocks
	BeneficiaryService beneficiaryService;
	
	@Test
	void testAddBeneficiary() 
	{
		Beneficiary beneficiary=new Beneficiary();
		beneficiary.setBid(1L);
		beneficiary.setAcno(1673190502L);
		beneficiary.setBank("Axis");
		beneficiary.setBranch("Hyderabad");
		beneficiary.setIfsc("AX0000500");
		beneficiary.setName("Triveni");
	
		when(beneficiaryRepository.save(beneficiary)).thenReturn(beneficiary);
		
		Beneficiary addBeneficiary = beneficiaryService.addBeneficiary(beneficiary);
		
		assertTrue(addBeneficiary.getBid()==beneficiary.getBid());
	}

	@Test
	void testGetBeneficiaryByAcno() 
	{
		Beneficiary beneficiary=new Beneficiary();
		beneficiary.setBid(1L);
		beneficiary.setAcno(1673190502L);
		beneficiary.setBank("Axis");
		beneficiary.setBranch("Hyderabad");
		beneficiary.setIfsc("AX0000500");
		beneficiary.setName("Triveni");
		
		when(beneficiaryRepository.findByAcno(beneficiary.getAcno())).thenReturn(beneficiary);
		
		Beneficiary beneficiaryByAcno = beneficiaryService.getBeneficiaryByAcno(beneficiary.getAcno());
		
		assertTrue(beneficiaryByAcno.getBid()==beneficiary.getBid());
	}

	@Test
	void testDeleteBeneficiary() 
	{
		Beneficiary beneficiary=new Beneficiary();
		beneficiary.setBid(1L);
		beneficiary.setAcno(1673190502L);
		beneficiary.setBank("Axis");
		beneficiary.setBranch("Hyderabad");
		beneficiary.setIfsc("AX0000500");
		beneficiary.setName("Triveni");
		
		doNothing().when(beneficiaryRepository).deleteById(beneficiary.getBid());
		
		beneficiaryService.deleteBeneficiary(beneficiary.getBid());
		
		verify(beneficiaryRepository).deleteById(beneficiary.getBid());
	}

	@Test
	void testGetAllBeneficiariesByAcno() 
	{
		List<Beneficiary> beneficiaries=new ArrayList<Beneficiary>();
		
		Account account=new Account();
		account.setAcno(1673190501L);
		account.setIfsc("AX0000500");
		account.setBranch("Hyderabad");
		account.setBalance(1000.0);
		account.setBank("Axis");
		
		Beneficiary beneficiary=new Beneficiary();
		beneficiary.setBid(1L);
		beneficiary.setAcno(1673190502L);
		beneficiary.setBank("Axis");
		beneficiary.setBranch("Hyderabad");
		beneficiary.setIfsc("AX0000500");
		beneficiary.setName("Triveni");
		beneficiary.setAccount(account);
		
		beneficiaries.add(beneficiary);
		
		when(beneficiaryRepository.findAll()).thenReturn(beneficiaries);
		
		List<Beneficiary> allBeneficiariesByAcno = beneficiaryService.getAllBeneficiariesByAcno(account.getAcno());
		
		assertTrue(allBeneficiariesByAcno.size()==1);
	}

}
