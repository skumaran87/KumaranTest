package com.silverrailtech.SpringRestService;

import java.util.Collections;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.json.JsonParser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class SpringRestController {
	
	private static final Logger logger = Logger.getLogger(SpringRestController.class.getName());
	//public static String state = ""; 

	public SpringRestController() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	@RequestMapping(value = "/test/state")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getState(@RequestHeader(value="state") String state) {
		
		logger.info("State retrivel : "+ state);
		return state;
	}
	
	@RequestMapping(value = "test/sum")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getStateSum(@RequestHeader(value="state") String state) {
		logger.info("Get sum of State value: "+ state);
		int stateCount=0;
		Pattern p = Pattern.compile("-?\\d+");
		Matcher m = p.matcher(state);
		while (m.find()) {
		  logger.info(m.group());
		  stateCount+=Integer.parseInt(m.group());
		}
		return stateCount+"";
	}
	
	@RequestMapping(value = "test/char")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getStateChars(@RequestHeader(value="state") String state) {
		logger.info("Get char value of State : "+ state);
		
		String removedigits = state.replaceAll("[0-9]", "");
		return removedigits;
	}
	
	
	@RequestMapping(value = "/test/chars")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSaveChars(@RequestBody String jsonObj, @RequestHeader(value="state") String state) {
			//String character, String amount) {
	
		logger.info("Request Body >>"+jsonObj+" ;Existing State :"+ state);
		JSONObject jsob;
		try {
			jsob = new JSONObject(jsonObj);
			logger.info(jsob.get("character").toString()+jsob.get("amount").toString());
			
			
			if(validateChar(jsob.get("character").toString()) && validateNumber(jsob.get("amount").toString())) {
				
				String character = jsob.get("character").toString();
				int amount = Integer.parseInt(jsob.get("amount").toString())+1;
				
				String newState = state + String.join(character, Collections.nCopies(amount, ""));
				
				if(newState.length()>200)
					return Response.status(Response.Status.BAD_REQUEST).entity("Error: State will become more than 200 characters").build();
					
			state = newState;
	   	   // logger.info("User added: "+character+" , "+ (amount-1) +" times");
			}
			else {
				//throw new ExceptionHandlerController();
				return Response.status(Response.Status.BAD_REQUEST).entity("Error: Character is not a only alphanumeric or amount is not a number from 1-9 ").build();
			}
			    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			logger.warning("JSON Psrse exception:: "+e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity("Error: because of Input JSON format").build();
		}
		
		
		
		return Response.status(Response.Status.ACCEPTED).header("state", state).entity(state).build();
				
	}
	
	public boolean validateChar(String character) {
		
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9]");
		Matcher match = pattern.matcher(character);
		
		return match.matches();
	}
	
    public boolean validateNumber(String amount) {
		
		Pattern pattern = Pattern.compile("^[1-9]");
		Matcher match = pattern.matcher(amount);
		
		return match.matches();
	}
	
	
	@RequestMapping(value = "/test/chars/{character}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeleteChars(@PathVariable("character") String character, @RequestHeader(value="state") String state) {
		logger.info("Delete a char :"+character+ " from State :"+ state);
		
		if(validateChar(character)) {
		int index = state.lastIndexOf(character);
		if(index != -1) {
			state = state.substring(0,index)+state.substring(index+1);
		}
		
		return Response.status(Response.Status.ACCEPTED).header("state", state).entity(state).build();
		}
		else
			return Response.status(Response.Status.BAD_REQUEST).entity("Error from Input JSON format").build();
	}
	

}
