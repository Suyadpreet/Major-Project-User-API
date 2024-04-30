package com.suyad.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SignUpRequest 
{
	private Integer userid;
	
	private String name;
	
	private String email;
	
	private String pwd;
	
	private String gender;
	
	private LocalDate dob;
	
	private Long phno;
	
	private Long ssn;
	
	private String userType;
	
	private String pwdChanged;
}
