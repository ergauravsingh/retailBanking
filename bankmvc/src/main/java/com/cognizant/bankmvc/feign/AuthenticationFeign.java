package com.cognizant.bankmvc.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cognizant.bankmvc.model.AppUser;

@FeignClient(name = "AUTH")
public interface AuthenticationFeign {

	@PostMapping(value = "/login")
	public AppUser login(@RequestBody AppUser appUserloginCredentials);
	
	@GetMapping("role/{id}")
	public String getRole(@PathVariable("id") String id);
}
