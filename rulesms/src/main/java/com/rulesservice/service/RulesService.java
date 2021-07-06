package com.rulesservice.service;

import com.rulesservice.model.RulesInput;

public interface RulesService {
	
	public boolean evaluate(RulesInput account);
	
}
