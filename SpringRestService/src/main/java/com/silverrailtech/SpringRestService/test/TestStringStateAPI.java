/*package com.silverrailtech.SpringRestService.test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.silverrailtech.SpringRestService.SpringRestServiceApplication;
import com.silverrailtech.SpringRestService.client.SecureController;

@ContextConfiguration(classes = SpringRestServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestStringStateAPI {

	public TestStringStateAPI() {
		// TODO Auto-generated constructor stub
	}
	


	private MockMvc mock;
	
	
	private ApplicationContext context;
	
		
	 @InjectMocks
	    private SecureController secureController;
	 
	@Before
	public void setup() {
		context= new ClassPathXmlApplicationContext("spring.xml");
       this.mock = MockMvcBuilders.standaloneSetup(context).build();
    		   //.webAppContextSetup(context).build();

	}
	
	@Test
    public void launhTestWebApp() throws Exception{
       
       //this.mock = MockMvcBuilders.webAppContextSetup(this.context).build();
        
    }

	
	@Test
	public void verifyInvalidPO() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/login/purchaseOrders")//.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{\"purchase_order_ids\" : \"[2344]\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.product_type_id").exists())
		.andExpect(jsonPath("$.total").exists())
		.andDo(print());
		
	
	}
	



}
*/