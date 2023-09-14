package com.User.H2.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.User.H2.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByName(String name);

}
