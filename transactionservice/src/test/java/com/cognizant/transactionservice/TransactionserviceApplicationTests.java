package com.cognizant.transactionservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class TransactionserviceApplicationTests {

	@Test
	void demo() {
		String check="demo";
		assertEquals("demo",check );
	}

}





