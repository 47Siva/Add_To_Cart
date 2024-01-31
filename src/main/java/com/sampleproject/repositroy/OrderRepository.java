package com.sampleproject.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sampleproject.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
