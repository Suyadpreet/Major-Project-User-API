package com.suyad.service;

import com.suyad.request.LoginRequest;
import com.suyad.request.PwdChangeRequest;
import com.suyad.request.SignUpRequest;
import com.suyad.response.LoginResponse;
import com.suyad.response.SignUpResponse;

public interface UserService 
{
	public SignUpResponse saveUser(SignUpRequest request);
	
	public LoginResponse userLogin(LoginRequest request);
	
	public LoginResponse updatePwd(PwdChangeRequest request);
	
	public boolean recoverPwd(String email);
}
