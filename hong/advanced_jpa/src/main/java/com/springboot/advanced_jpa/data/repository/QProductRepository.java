package com.springboot.advanced_jpa.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.springboot.advanced_jpa.data.entity.Product;

public interface QProductRepository extends JpaRepository<Product, Long>, 
	QuerydslPredicateExecutor<Product> {
}
