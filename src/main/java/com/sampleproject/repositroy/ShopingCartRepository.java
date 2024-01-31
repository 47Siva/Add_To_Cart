package com.sampleproject.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sampleproject.entity.ShoppingCart;

@Repository
public interface ShopingCartRepository extends JpaRepository<ShoppingCart,Integer>{

}
