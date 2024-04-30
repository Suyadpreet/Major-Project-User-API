package com.suyad.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suyad.Entity.UserInfoEntity;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> 
{
	public UserInfoEntity findByEmail(String email);
}
