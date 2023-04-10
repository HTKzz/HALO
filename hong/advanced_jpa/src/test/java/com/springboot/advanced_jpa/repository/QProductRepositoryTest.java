package com.springboot.advanced_jpa.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.core.types.Predicate;
import com.springboot.advanced_jpa.data.entity.Product;
import com.springboot.advanced_jpa.data.entity.QProduct;
import com.springboot.advanced_jpa.data.repository.QProductRepository;

@SpringBootTest
public class QProductRepositoryTest {

	@Autowired
	QProductRepository qProductRepository;

	@Test
	public void queryDSLTest1() {
		Predicate predicate = QProduct.product.name.containsIgnoreCase("펜")
				.and(QProduct.product.price.between(1000, 2500));
		Optional<Product> foundProduct = qProductRepository.findOne(predicate);
		if (foundProduct.isPresent()) {
			Product product = foundProduct.get();
			System.out.println(product.getNumber());
			System.out.println(product.getName());
			System.out.println(product.getPrice());
			System.out.println(product.getStock());
		}
	}
}
