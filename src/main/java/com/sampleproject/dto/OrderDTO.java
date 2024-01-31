package com.sampleproject.dto;

import java.util.List;

import com.sampleproject.entity.ShoppingCart;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderDTO {

	private String orderDescription;
    private List<ShoppingCart> cartItems;
    private String customerEmail;
    private String customerName;
	public OrderDTO(String orderDescription, List<ShoppingCart> cartItems, String customerEmail, String customerName) {
		this.orderDescription = orderDescription;
		this.cartItems = cartItems;
		this.customerEmail = customerEmail;
		this.customerName = customerName;
	}
    
    
}
