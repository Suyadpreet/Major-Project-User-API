package com.suyad.response;

import lombok.Data;

@Data
public class LoginResponse 
{
	private Integer userid;
	
	private String name;
	
	private String userType;
	
	private DasboardResponse dashboard;
	
	private boolean isValidLogin;
	
	private String pwdChanged;
}
