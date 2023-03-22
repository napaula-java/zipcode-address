package com.example.postof.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.postof.infra.exception.NoContentException;
import com.example.postof.infra.exception.NotReadyException;
import com.example.postof.model.Address;
import com.example.postof.service.PostService;

@RestController
@RequestMapping("post")
public class PostController {
	
	
	@Autowired
	private PostService service;
	
	@GetMapping("/status")
	public String getStatus() {
		return "Service Status: " + service.getStatus();
	}

	@GetMapping("/zipcode/{zipcode}")
	public Address getAddressByZipCode(@PathVariable String zipcode) throws NoContentException, NotReadyException {
		return service.getAddressByZipcode(zipcode);
	}
}
