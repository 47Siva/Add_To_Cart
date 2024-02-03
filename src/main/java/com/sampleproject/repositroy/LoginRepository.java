package com.sampleproject.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sampleproject.entity.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {

	@Query(value = "select * from login as u where u.username=:username and u.password=:password", nativeQuery = true)
	Login findOneByUserNameAndPassword(String username, String password);

}
