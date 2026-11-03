package com.hcl.hclassessment2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.hclassessment2.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
