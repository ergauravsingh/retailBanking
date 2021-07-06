package com.cognizant.bankmvc.model;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
	
	@NotBlank(message="Customer Id is required")
	@Pattern(regexp="[0-9]*", message="Customer id should be in digits")
	private String userid;
	
	@NotBlank(message="Name is required")
	@Pattern(regexp="[a-zA-z]*", message="Name should contain only alphabets")
	private String username;
	
	@NotBlank(message="Password is required")
	@Size(min = 4, max = 10, message = "Password should be 4 to 6 characters long")
	private String password;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	
	@Pattern(regexp="[0-9]{10}", message="PAN must be 10 digit number")
	private String pan;
	
	@NotBlank(message="Address is required")
	private String address;
	
	private List<Account> accounts = new ArrayList<Account>();
	
}


