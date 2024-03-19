package com.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.model.User;

@Repository
public interface IUserDao extends JpaRepository<User,Integer> {
	
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
	User findByUsername(String username);
	

}
