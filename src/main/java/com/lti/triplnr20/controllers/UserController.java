package com.lti.triplnr20.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.lti.triplnr20.models.User;
import com.lti.triplnr20.services.S3Service;
import com.lti.triplnr20.services.UserService;

@RestController
@RequestMapping("/users")

@CrossOrigin(origins = "*")

public class UserController {

	UserService us;
	S3Service s3;
	Gson gson = new Gson();

	@Autowired
	public UserController(UserService us, S3Service s3) {
		super();
		this.us = us;
		this.s3 = s3;
	}

	// Test Route
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<>(gson.toJson("test works"), HttpStatus.OK);
	}

	
	/* 
	* Update will receive user in request body and try to update changes to the database will throw 
	* InvalidAddressException and UserAlreadyExistsException which is handled by the Exception Handler if thrown
	*/ 
	@PutMapping(value = "/update", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> update(@RequestPart("user") User user, @RequestPart(value = "file", required = false) MultipartFile file, @RequestHeader("Authorization") String token ){
		try {
			if (file != null) {
				user.setProfilePic(s3.upload(file));
			}
			us.updateUser(user);
			return new ResponseEntity<>(gson.toJson("Update Successful"), HttpStatus.OK);
		} catch (IOException e) {
				return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT); 
		}
	}
	
	//Receives a user id as path parameter and gets the current user with matched id in the database 
	@GetMapping(value="/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") int id){
		return new ResponseEntity<>(us.getUserById(id), HttpStatus.OK);

	}
	
	@PostMapping(value="/create", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Void> createUser(@RequestPart("user") User user, @RequestPart(name = "file", required = false) MultipartFile file) {
		try {
			if (file != null) {
				user.setProfilePic(s3.upload(file));
			}
			us.createUser(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
		}
	}

	// Recieves the sub in header to find logged in user
	@GetMapping(value="/sub")
	public ResponseEntity<User> getBySub(@RequestHeader("Authorization") String sub) {
		return new ResponseEntity<>(us.getUserBySub(sub), HttpStatus.OK);
	}
	
	//Requests for the authorization token in the request header and will send corresponding user information back 
	@GetMapping(value="/user")
	public ResponseEntity<User> getByUser(@RequestHeader("Authorization") String token ){
		return new ResponseEntity<>(us.getUserBySub(token), HttpStatus.OK);

	}
	
	///Requests for the authorization token in the request header and will send corresponding user friend list back 
	@GetMapping("/myfriends")
	public ResponseEntity<List<User>> getFriends(@RequestHeader("Authorization") String token){
		return new ResponseEntity<>(us.getFriends(token), HttpStatus.OK);
	}
	
	@GetMapping("/profiles")
	public ResponseEntity<List<User>> getProfiles(@RequestHeader("Authorization") String token){
		return new ResponseEntity<>(us.getProfiles(token), HttpStatus.OK);
		
	}
	
}
