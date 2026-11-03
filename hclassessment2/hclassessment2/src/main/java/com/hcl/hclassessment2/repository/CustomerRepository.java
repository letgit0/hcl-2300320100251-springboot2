package com.hcl.hclassessment2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.hclassessment2.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
