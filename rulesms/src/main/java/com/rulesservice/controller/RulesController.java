package com.rulesservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rulesservice.exception.MinimumBalanceException;
import com.rulesservice.model.RulesInput;
import com.rulesservice.service.RulesService;

@RestController
public class RulesController {

	private static final String INVALID = "Send Valid Details.";
	
	@Autowired
	public RulesService rulesService;
	

	
	/**
	 * Evaluate Mininmum Balance Criteria for Transaction
	 * @param RulesInput
	 * @return Boolean
	 * @throws MinimumBalanceException
	 */
	@PostMapping("/evaluateMinBal")
	public ResponseEntity<?> evaluate(@RequestBody RulesInput account)
			throws MinimumBalanceException {
		
		if (account.getCurrentBalance()== 0) {
			throw new MinimumBalanceException(INVALID);
		} else {
			boolean status = rulesService.evaluate(account);

			return new ResponseEntity<Boolean>(status,HttpStatus.OK);
		}
	}



}
