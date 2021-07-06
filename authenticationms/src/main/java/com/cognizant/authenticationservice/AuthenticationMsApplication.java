package com.cognizant.authenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * The AuthenticationMsApplication class to start the application
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class AuthenticationMsApplication {
	

	
	/**
	 * The main method for app
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationMsApplication.class, args);
	}
	

}
 