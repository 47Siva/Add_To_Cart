package com.sampleproject.controller;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

	@Autowired
	private AddToCartService addToCartService;

	private Logger logger = LoggerFactory.getLogger(AddToCartService.class);

	// place order and add to cart api
	@PostMapping("/placeOrder")
	public ResponseEntity<APIResponse> placeOrder(@RequestBody OrderDTO orderDTO) {

		logger.info("Request Payload " + orderDTO.toString());

		APIResponse apiResponse = new APIResponse();
		ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();

		// set amount
		double amount = addToCartService.getCartAmount(orderDTO.getCartItems());

		Customer customer = addToCartService.isCustomerPresent(orderDTO.getCustomerEmail(), orderDTO.getCustomerName());

		if (customer != null) {
			logger.info("Customer is present " + customer.getId());
			Order order = new Order(orderDTO.getOrderDescription(), customer, orderDTO.getCartItems());
			order = addToCartService.saveOrder(order);
			logger.info("Order processed successfully..");

			responseOrderDTO.setAmount(amount);
			responseOrderDTO.setDate(DateUtil.getCurrentDateTime());
			responseOrderDTO.setInvoiceNumber(new Random().nextInt(1000));
			responseOrderDTO.setOrderId(order.getId());
			responseOrderDTO.setOrderDescription(orderDTO.getOrderDescription());

			logger.info("test push..");
			apiResponse.setData(responseOrderDTO);
			apiResponse.setStatus(HttpStatus.OK.value());

		} else {
			logger.info("Customer not login ");
			apiResponse.setError("Customer not loged in");
			apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		}

		return ResponseEntity.ok(apiResponse);
	}

    // get all product api
	@GetMapping(value = "/getAllProducts")
	public ResponseEntity<APIResponse> getAllProducts() {

		List<Product> productList = addToCartService.getAllProducts();
		APIResponse apiResponse = new APIResponse();
		if(productList!=null) {
		apiResponse.setData(productList);
		apiResponse.setStatus(HttpStatus.OK.value());
		}else {
			apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
			apiResponse.setError("products not found");
		}
		return ResponseEntity.ok(apiResponse);
	}
	
	//get oreder detiles in orde id 
	@GetMapping(value = "getById")
	public ResponseEntity<APIResponse> getOrderDetiles(int orderId){
		Order order = addToCartService.getOrderDetail(orderId);
		APIResponse apiResponse = new APIResponse();
		
		if(order!= null) {
			apiResponse.setStatus(HttpStatus.OK.value());
			apiResponse.setData(order);
		}else {
			apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			apiResponse.setError("Oreder Id not Found");
		}
		
        return ResponseEntity.ok(apiResponse);
	}
}