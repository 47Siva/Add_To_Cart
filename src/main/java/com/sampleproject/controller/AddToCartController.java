package com.sampleproject.controller;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sampleproject.common.APIResponse;
import com.sampleproject.dto.OrderDTO;
import com.sampleproject.dto.ResponseOrderDTO;
import com.sampleproject.entity.Customer;
import com.sampleproject.entity.Order;
import com.sampleproject.entity.Product;
import com.sampleproject.service.AddToCartService;
import com.sampleproject.util.DateUtil;

@RestController
@RequestMapping("/api/addToCart")
public class AddToCartController {

	private AddToCartService addToCartService;

	public AddToCartController(AddToCartService addToCartService) {
		this.addToCartService = addToCartService;
	}

	private Logger logger = LoggerFactory.getLogger(AddToCartService.class);

	// place order and add to cart api
	@PostMapping("/placeOrder")
	public ResponseEntity<ResponseOrderDTO> placeOrder(@RequestBody OrderDTO orderDTO) {

		logger.info("Request Payload " + orderDTO.toString());

		ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();

		// set amount
		double amount = addToCartService.getCartAmount(orderDTO.getCartItems());

		Customer customer = new Customer( orderDTO.getCustomerName(), orderDTO.getCustomerEmail(), null);
		Integer customerIdFromDb = addToCartService.isCustomerPresent(customer);
		if (customerIdFromDb != null) {
			customer.setId(customerIdFromDb);
			logger.info("Customer already present in db with id : " + customerIdFromDb);
		} else {
			APIResponse apiResponse = new APIResponse();
			apiResponse.setData("User login failed");
			apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			apiResponse.setError("incorrect passward or emailId");
			
			logger.info("Customer not login ");
		}
		Order order = new Order(orderDTO.getOrderDescription(), customer, orderDTO.getCartItems());
		order = addToCartService.saveOrder(order);
		logger.info("Order processed successfully..");

		responseOrderDTO.setAmount(amount);
		responseOrderDTO.setDate(DateUtil.getCurrentDateTime());
		responseOrderDTO.setInvoiceNumber(new Random().nextInt(1000));
		responseOrderDTO.setOrderId(order.getId());
		responseOrderDTO.setOrderDescription(orderDTO.getOrderDescription());

		logger.info("test push..");

		return ResponseEntity.ok(responseOrderDTO);
	}

	// get all product api
	@GetMapping(value = "/getAllProducts")
	public ResponseEntity<List<Product>> getAllProducts() {

		List<Product> productList = addToCartService.getAllProducts();

		return ResponseEntity.ok(productList);
	}

	// get by id
	@GetMapping(value = "/getOrder/{orderId}")
	public ResponseEntity<Order> getOrderDetails(@PathVariable int orderId) {

		Order order = addToCartService.getOrderDetail(orderId);
		return ResponseEntity.ok(order);
	}

}
