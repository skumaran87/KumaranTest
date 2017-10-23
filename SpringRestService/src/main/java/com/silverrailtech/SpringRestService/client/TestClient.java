package com.silverrailtech.SpringRestService.client;

import java.net.URI;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;


public class TestClient {

	public TestClient() {
		// TODO Auto-generated constructor stub main(String[] args)
	}
static Client client = ClientBuilder.newClient();
static String tokenJWT = "";

	public static String getJWTvalue() {
		
		WebTarget target = client.target("http://localhost:8080/test");
		Entity json = Entity.entity("{\"username\":\"kutti\",\"password\":\"kumar\"}", MediaType.APPLICATION_JSON_TYPE);
	    Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE);
	    Response res = builder.post(json);
	    tokenJWT=res.getHeaderString("authorization");
	    System.out.println(tokenJWT);
	    return tokenJWT;
	}
	
	public static String getResponseString(WebTarget webTarget, String state) {
		
		MultivaluedMap<String, Object> headerValues = new MultivaluedHashMap<>();
		headerValues.add("authorization", tokenJWT);
		headerValues.add("state", state);
		
  Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON_TYPE).headers(headerValues); 
  //res.getHeaderString("authorization"));
  Response response = builder.get();
  String sumValue = response.readEntity(String.class);
  System.out.println("SAIBABA>>"+sumValue);
  return sumValue;
  
	}
	
	
	public static String getStateValue(String state) {
		
		
		WebTarget webResource =client.target("http://localhost:8080/test/state");
		return getResponseString(webResource, state);
	  
	}
	
	public static String getSumStateValue(String state) {
				
		WebTarget webResource =client.target("http://localhost:8080/test/sum");
		return getResponseString(webResource, state);
	}
	
	public static String getCharStateValue(String state) {
		
		WebTarget webResource =client.target("http://localhost:8080/test/char");
		return getResponseString(webResource, state);
	}
	
public static String deleteCharState(String character, String state) {
		
		WebTarget webTarget =client.target("http://localhost:8080/test/chars/"+character);
		//Entity json = Entity.entity("{\"character\":"+character+",\"amount\":"+amount+"}", MediaType.APPLICATION_JSON_TYPE);
		
		MultivaluedMap<String, Object> headerValues = new MultivaluedHashMap<>();
		headerValues.add("authorization", tokenJWT);
		headerValues.add("state", state);
		
	    Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON_TYPE).headers(headerValues);
	    Response response = builder.delete();
	    String sumValue = "";
	    try {
			JSONObject jsonObject = new JSONObject(response.readEntity(String.class));
			System.out.println("Delete jsonObject>>"+jsonObject.toString());
			sumValue=jsonObject.get("entity").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return state;
		}
	   
	     System.out.println(sumValue);
	    return sumValue;

	}
public static String addCharStateValue(String character, String amount, String state) {
	
	WebTarget webTarget =client.target("http://localhost:8080/test/chars");
	System.out.println(state+">>>"+"{\"character\":\""+character+"\",\"amount\":\""+amount+"\"}");
	Entity json = Entity.entity("{\"character\":\""+character+"\",\"amount\":\""+amount+"\"}", MediaType.APPLICATION_JSON_TYPE);
	
	MultivaluedMap<String, Object> headerValues = new MultivaluedHashMap<>();
	headerValues.add("authorization", tokenJWT);
	headerValues.add("state", state);
	
    Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON_TYPE).headers(headerValues);
    Response response = builder.post(json);
   /* if(response.getStatus()>= 400) {
    	System.out.println(response.getStatus()+""+response.getStatusInfo());
    	return response.getStatusInfo().toString();
    }*/
    String sumValue = "";
    try {
    	String value = response.readEntity(String.class);
    	//System.out.println("get>>response>>>"+value);
		JSONObject jsonObject = new JSONObject(value);
	//	System.out.println("Add jsonObject>>"+jsonObject.toString());
		sumValue=jsonObject.get("entity").toString();
		System.out.println("Entity>>"+sumValue);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
		return state;
	}
    

    return sumValue;
	
}
	   
	   
	    
	


		
	

}
