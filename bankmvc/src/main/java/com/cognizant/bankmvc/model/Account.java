package com.cognizant.bankmvc.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {
	
	@Max(value = 100000, message = "Please enter valid account number")
	@Min(value = 100, message = "Please enter valid account number")
	private long accountId;
	
	@NotBlank(message="Customer Id is required")
	private String customerId;

	@Min(value = 1000, message = "Please enter amount greater than 1000")
	private double currentBalance;
	
	@NotBlank(message="Please specify account type")
	private String accountType;
	
	@NotBlank(message="Owner name is required")
	private String ownerName;

	private transient List<Transaction> transactions = new ArrayList<Transaction>();


}