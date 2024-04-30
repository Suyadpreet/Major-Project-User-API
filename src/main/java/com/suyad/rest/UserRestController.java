package com.suyad.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.suyad.request.LoginRequest;
import com.suyad.request.PwdChangeRequest;
import com.suyad.request.SignUpRequest;
import com.suyad.response.LoginResponse;
import com.suyad.response.SignUpResponse;
import com.suyad.service.UserService;

@RestController
public class UserRestController
{
	@Autowired
	private UserService service;
	
	@PostMapping("/user")
	public ResponseEntity<SignUpResponse> userRegistration(@RequestBody SignUpRequest request)
	{
		
		 SignUpResponse response = service.saveUser(request);
		if(response.getSuccessMsg()!=null)
		{
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else if(response.getErrorMsg().contains("Duplicate Email"))
		{
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> userLogin(LoginRequest request)
	{
		LoginResponse response = service.userLogin(request);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/pwd-change")
	public ResponseEntity<LoginResponse> updatePwd(@RequestBody PwdChangeRequest request)
	{
		LoginResponse login = service.updatePwd(request);
		return new ResponseEntity<>(login,HttpStatus.OK);
		
	}
	
	@GetMapping("/recover-pwd/{email}")
	public ResponseEntity<String> recoverPwd(@PathVariable String email)
	{
		boolean isValid = service.recoverPwd(email);
		if(isValid)
		{
			return new ResponseEntity<>("Password sent to Your Email",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("Invalid Email",HttpStatus.BAD_REQUEST);
		}
		
	}
}
