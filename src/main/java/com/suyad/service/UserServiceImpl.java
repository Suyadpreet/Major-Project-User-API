package com.suyad.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.suyad.Entity.UserInfoEntity;
import com.suyad.repo.UserInfoRepository;
import com.suyad.request.LoginRequest;
import com.suyad.request.PwdChangeRequest;
import com.suyad.request.SignUpRequest;
import com.suyad.response.DasboardResponse;
import com.suyad.response.LoginResponse;
import com.suyad.response.SignUpResponse;
import com.suyad.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService 
{
	@Autowired
	private UserInfoRepository userRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	@Override
	public SignUpResponse saveUser(SignUpRequest request) 
	{
		SignUpResponse response = new SignUpResponse();
		UserInfoEntity user = userRepo.findByEmail(request.getEmail());
		if(user!=null)
		{
			response.setErrorMsg("Duplicate Email");
			return response;
		}
		else
		{
			// generate Temporary password
			String tempPwd = generateTempPwd();
			request.setPwd(tempPwd);
			request.setPwdChanged("false");
			UserInfoEntity entity = new UserInfoEntity();
			BeanUtils.copyProperties(request, entity);
			userRepo.save(entity);
			String body = "Your password to Login "+tempPwd;
			boolean sendEmail = emailUtils.sendEmail("suyadgill@gmail.com", "IES Account created", body);
			if(sendEmail)
			{
				response.setSuccessMsg("Registration Successfull.....");
			}
			else
			{
				response.setErrorMsg("Registration Failed....");
			}
			return response;
		}
		
	}

	@Override
	public LoginResponse userLogin(LoginRequest request) 
	{
		LoginResponse response = new LoginResponse();
		UserInfoEntity entity = new UserInfoEntity();
		entity.setEmail(request.getEmail());
		entity.setPwd(request.getPwd());
		Example<UserInfoEntity> of = Example.of(entity);
		List<UserInfoEntity> entities = userRepo.findAll(of);
		if(!entities.isEmpty())
		{
			UserInfoEntity user = entities.get(0);
			
			response.setUserid(user.getUserid());
			response.setUserType(user.getUserType());
			
			if(user.getPwdChanged().equals("true"))
			{
				// second Login
				response.setPwdChanged("true");
				response.setValidLogin(true);
				
				
				//set Dasboard Data
				DasboardResponse dashboard = new DasboardResponse();
				dashboard.setPlanCount(61);
				dashboard.setBenefitAmtTotal(3400.00);
				dashboard.setCitizensApCnt(10001);
				dashboard.setCitizedDnCnt(5001);
				response.setDashboard(dashboard);
			}
			else
			{
				// forst Login
				response.setPwdChanged("false");
				response.setValidLogin(true);
			}
		}
		else
		{
			response.setValidLogin(false);
		}
		return null;
	}

	@Override
	public LoginResponse updatePwd(PwdChangeRequest request) 
	{
		LoginResponse response = new LoginResponse();
		Integer userid = request.getUserid();
		Optional<UserInfoEntity> findById = userRepo.findById(userid);
		if(findById.isPresent())
		{
			UserInfoEntity entity = findById.get();
			entity.setPwd(request.getPwd());
			entity.setPwdChanged("true");
			userRepo.save(entity);
			response.setUserid(entity.getUserid());
			response.setUserType(entity.getUserType());
			response.setValidLogin(true);
			response.setPwdChanged("true");
			DasboardResponse dashboard = new DasboardResponse();
			dashboard.setPlanCount(61);
			dashboard.setBenefitAmtTotal(3400.00);
			dashboard.setCitizensApCnt(10001);
			dashboard.setCitizedDnCnt(5001);
			response.setDashboard(dashboard);
		}
		return null;
	}

	@Override
	public boolean recoverPwd(String email) 
	{
		UserInfoEntity user = userRepo.findByEmail(email);
		if(user==null)
		{
			return false;
		}
		else
		{
			String subject = "IES Recovered Password";
			String body = "Your password is = "+user.getPwd();
			return emailUtils.sendEmail(email, subject, body);
		}
		
	}
	public String generateTempPwd()
	{
		// create a string of all characters
	    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

	    // create random string builder
	    StringBuilder sb = new StringBuilder();

	    // create an object of Random class
	    Random random = new Random();

	    // specify length of random string
	    int length = 5;

	    for(int i = 0; i < length; i++) 
	    {

	    	// generate random index number
	    	int index = random.nextInt(alphabet.length());

	    	// get character specified by index
	    	// from the string
	    	char randomChar = alphabet.charAt(index);

	    	// append the character to string builder
	    	sb.append(randomChar);
	    }

	    return  sb.toString();
	}
}
