package com.rulesservice.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rulesservice.exception.MinimumBalanceException;
import com.rulesservice.model.RulesInput;
import com.rulesservice.service.RulesServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RulesController.class)
class RulesControllerTest {

		@Autowired
		MockMvc mockMvc;

		@MockBean
		RulesServiceImpl rulesService;
		
		
		// Testing MVC '/evaluateMinBal' endpoint
		
		@Test
		void evaluateTest() throws Exception {
			
			RulesInput inp=new RulesInput(101,1200,100);
			when(rulesService.evaluate(inp)).thenReturn(true);
			mockMvc.perform(
					MockMvcRequestBuilders.post("/evaluateMinBal").content(asJsonString(inp))
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		   
			
		}	
		
		
		
		//Test minimum balance exception

		@Test
		public void minimumBal() throws MinimumBalanceException,Exception {
			
			RulesController con=new RulesController();
			RulesInput account=new RulesInput(0,0,0);
			 Throwable exception = assertThrows(MinimumBalanceException.class, () -> con.evaluate(account));
			    assertEquals("Send Valid Details.", exception.getMessage());
			
		}
		

		
		
		//reading and writing json content
		public static String asJsonString(final Object obj) throws JsonProcessingException {
			
				final ObjectMapper mapper = new ObjectMapper();
				final String jsonContent = mapper.writeValueAsString(obj);
				return jsonContent;
			
		}
		
}