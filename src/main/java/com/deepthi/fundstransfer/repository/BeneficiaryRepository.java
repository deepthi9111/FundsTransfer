package com.deepthi.fundstransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepthi.fundstransfer.entity.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

	Beneficiary findByAcno(Long acno);

}
