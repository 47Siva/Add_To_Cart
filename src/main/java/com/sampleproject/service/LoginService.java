package com.sampleproject.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sampleproject.common.APIResponse;
import com.sampleproject.dto.LoginRequestDto;
import com.sampleproject.dto.SignUpRequestDto;
import com.sampleproject.entity.Customer;
import com.sampleproject.entity.Login;
import com.sampleproject.entity.UserLogin;
import com.sampleproject.repositroy.CustomerRepository;
import com.sampleproject.repositroy.LoginRepository;
import com.sampleproject.repositroy.UserLoginRepository;
import com.sampleproject.util.JwtUtil;



@Service
public class LoginService {

	@Autowired
	UserLoginRepository userLoginRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	LoginRepository loginRepository;

	@Autowired
	JwtUtil jwtutil;

	// singup api service
	public APIResponse signUp(SignUpRequestDto signUpRequestDto) {
		APIResponse apiResponse = new APIResponse();

		// validation

		// Dto to entity
		UserLogin userLoginEntity = new UserLogin();
		userLoginEntity.setName(signUpRequestDto.getName());
		userLoginEntity.setEmailId(signUpRequestDto.getEmailId());
		userLoginEntity.setGender(signUpRequestDto.getGender());
		userLoginEntity.setId(signUpRequestDto.getId());
		userLoginEntity.setPnoneNumber(signUpRequestDto.getPhoneNumber());
		userLoginEntity.setPassward(signUpRequestDto.getPassward());
		
		//userlogin to customer save
		Customer customer = new Customer();
		customer.setEmail(signUpRequestDto.getEmailId());
		customer.setName(signUpRequestDto.getName());
		
		//userlogin to login save
		Login login = new Login();
		login.setUsername(signUpRequestDto.getEmailId());
		login.setPassword(signUpRequestDto.getPassward());

		// store entity
		userLoginEntity = userLoginRepository.save(userLoginEntity);
		customer = customerRepository.save(customer);
		login = loginRepository.save(login);
				
		
		// return
		if (userLoginEntity != null) {
			apiResponse.setStatus(HttpStatus.OK.value());
			apiResponse.setData(userLoginEntity);
		} else {
			apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			apiResponse.setError("bad request");
		}

		return apiResponse;
	}

	// login api service
	public APIResponse login(LoginRequestDto loginRequestDto) {

		APIResponse apiResponse = new APIResponse();

		// validation

		// verfiy user exist with given email and possward
		Login userLogin =loginRepository.findOneByUserNameAndPassword(loginRequestDto.getUsername(),
				loginRequestDto.getPassword());
		
		//verfiy coustomer exist with given username
		Customer customer = customerRepository.findByUserName(loginRequestDto.getUsername());
		

		// response
		if (userLogin != null) {
			apiResponse.setData("Loged in");
			apiResponse.setStatus(HttpStatus.OK.value());
			String token = jwtutil.generateJwt(userLogin);

			Map<Object, Object> data = new HashMap<Object, Object>();
			data.put("UserDetiles",customer);
			data.put("Token", token);

			apiResponse.setData(data);
			return apiResponse;
		} else {
			apiResponse.setData("User login failed");
			apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			apiResponse.setError("incorrect passward or emailId");
			return apiResponse;
		}

	}


}
