package com.hcl.hclassessment2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.hclassessment2.entity.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByCustomer_IdOrderByOrderDateDesc(Long customerId);
}
