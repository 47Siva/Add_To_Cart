package com.sampleproject.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sampleproject.entity.Customer;
import com.sampleproject.entity.Order;
import com.sampleproject.entity.Product;
import com.sampleproject.entity.ShoppingCart;
import com.sampleproject.repositroy.CustomerRepository;
import com.sampleproject.repositroy.OrderRepository;
import com.sampleproject.repositroy.ProductRepository;

@Service
public class AddToCartService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	// order section
	public double getCartAmount(List<ShoppingCart> shoppingCartList) {

		double totalCartAmount = 0d;
		double singleCartAmount = 0d;
		int availableQuantity = 0;

		for (ShoppingCart cart : shoppingCartList) {

			int productId = cart.getProductId();
			Optional<Product> product = productRepository.findById(productId);
			if (product.isPresent()) {
				Product product1 = product.get();
				if (product1.getAvailableQuantity() < cart.getQuantity()) {
					singleCartAmount = product1.getPrice() * product1.getAvailableQuantity();
					cart.setQuantity(product1.getAvailableQuantity());
				} else {
					singleCartAmount = cart.getQuantity() * product1.getPrice();
					availableQuantity = product1.getAvailableQuantity() - cart.getQuantity();
				}
				totalCartAmount = totalCartAmount + singleCartAmount;
				product1.setAvailableQuantity(availableQuantity);
				availableQuantity = 0;
				cart.setProductName(product1.getName());
				cart.setAmount(singleCartAmount);
				productRepository.save(product1);
			}
		}

		return totalCartAmount;
	}

	// order save section
	@Transactional
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}

	// get details
//	public Order getOrderDetail(int orderId) {
//		Optional<Order> order = this.orderRepository.findById(orderId);
//		return order.isPresent() ? order.get() : null;
//	}

	public Order getOrderDetail(int orderId) {
		Optional<Order> order = orderRepository.findById(orderId);
		Order order2 = order.get();
		return order2 ;
	}

	// Customer section
	// Customer is present or not
//	public Integer isCustomerPresent(Customer customer) {
//		Customer customer1 = customerRepository.getCustomerByEmailAndName(customer.getEmail(), customer.getName());
//		return customer1 != null ? customer1.getId() : null;
//	}

	// customer is present or not
	public Customer isCustomerPresent(String customerEmail, String customerName) {
		return customerRepository.findByNameAndEmail(customerEmail, customerName);
	}

	// Custommer save
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	// product setion
	// get all product
	public List<Product> getAllProducts() {
		return this.productRepository.findAll();
	}

}
