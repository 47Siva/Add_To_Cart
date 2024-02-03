package com.sampleproject.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sampleproject.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query(value = "select * from customer as c where c.email =:customerEmail and c.name =:customerName",nativeQuery = true)
	public Customer findByNameAndEmail(String customerEmail, String customerName);

	@Query(value = "select *from customer as c where c.email =:username",nativeQuery = true)
	public Customer findByUserName(String username);
	

}
