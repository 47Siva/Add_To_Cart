package com.sampleproject.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sampleproject.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public Customer getCustomerByEmailAndName(String email, String name);

	@Query(value = "select * from customer as u where u.email=:username and u.password=:password",nativeQuery = true)
	public Customer findOneByUserNameAndPassword(String username, String password);

}
