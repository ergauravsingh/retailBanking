package com.rulesservice.service;

import org.springframework.stereotype.Service;

import com.rulesservice.model.RulesInput;

@Service
public class RulesServiceImpl implements RulesService {
	
	
	@Override
	public boolean evaluate(RulesInput account) {
		int min=1000;
	  //if balance should higher than minimum balance
		double check = account.getCurrentBalance() - account.getAmount();
	    	if(check >= min)
	    		return true;
	    	else
	    		return false;
	}
	
	
}
