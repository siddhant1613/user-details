package com.User.H2.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.User.H2.demo.entity.User;
import com.User.H2.demo.service.UserService;

@RestController
public class userController {
	
	

	@Autowired
	private UserService userService;
	
	@PostMapping("/addUser")
	public User addUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	@PostMapping("/addUsers")
	public List<User> addUsers(@RequestBody List<User> users){
		return userService.createUsers(users);
	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable int id) {
		return userService.getUserById(id);
		
	}
	
	@GetMapping("/user1/{name}")
	public User getUserById(@PathVariable String name) {
		return userService.getUserByName(name);
		
	}
	
	@GetMapping("/users")
	public List<User> getAllUser(){
		return userService.getUsers();
	}

	@PutMapping("/updateuser")
	public User updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}
	
	@DeleteMapping("/user/{id}")
	public String deleteUser(@PathVariable int id) {
          return userService.delUserById(id);		
	}
	
	@GetMapping("/userbatchadd")
	public List<User> batchAddUser()
	{
		return userService.batchAdd();
	}
}
