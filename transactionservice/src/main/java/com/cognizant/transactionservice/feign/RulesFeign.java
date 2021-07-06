package com.cognizant.transactionservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cognizant.transactionservice.exception.MinimumBalanceException;
import com.cognizant.transactionservice.models.RulesInput;

@FeignClient(name = "RULES")
public interface RulesFeign {
	
	@PostMapping("/evaluateMinBal")
	public ResponseEntity<?> evaluate(@RequestBody RulesInput account)throws MinimumBalanceException ;
	
}
