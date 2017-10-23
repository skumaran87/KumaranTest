package com.silverrailtech.SpringRestService.client;


import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.silverrailtech.SpringRestService.SpringRestController;


@EnableAutoConfiguration
@Controller
public class SecureController {

	private static final Logger logger = Logger.getLogger(SecureController.class.getName());

   	@Autowired
    UserRepository userRepository;
    
	public SecureController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public  String sayHello() {
		
		
		return "confirm";
		
	}	

	
	@RequestMapping(value="/confirm", params="welcomePage",  method=RequestMethod.POST)
	public  String sayConfirm(Users user, Model model, HttpSession session) {
		logger.info(user.getPassword());
		logger.info(user.getFirstName());
		model.addAttribute("name", user.getFirstName());
		model.addAttribute("pwd", user.getPassword());
		session.setAttribute("user", user.getFirstName());
		session.setAttribute("pwd", user.getPassword());
		String jwToken = TestClient.getJWTvalue();
		logger.info(jwToken);
		session.setAttribute("state", "");
		//logger.info("inside  default success"+ user.getFirstName());
		return "confirm";
		
	}
	
 
	
	@RequestMapping( params="addAction",method=RequestMethod.POST)
    public void action4(String character, String amount, HttpSession session, Model model)
    {
        
        String state = session.getAttribute("state").toString();
        logger.info("..."+state+"addAction block called"+character+".."+amount);
        String response = TestClient.addCharStateValue(character, amount, state);
       // logger.info(response);
        if(response.contains("Error:"))
        	model.addAttribute("response", response);
      //  session.setAttribute("state",TestClient.addCharStateValue(character, amount, state));
        else {
        model.addAttribute("stateValue", response);
        session.setAttribute("state",response);
        logger.info("User "+session.getAttribute("user")+" : added "+character + ", "+amount + " times");
        }
    }
	
	@RequestMapping(params="delAction",method=RequestMethod.POST)
    public void action5(String character, HttpSession session, Model model)
    {
        logger.info("delAction block called");
        String state = session.getAttribute("state").toString();
        String response = TestClient.deleteCharState(character, state);
       // logger.info(response);
        if(response.contains("Error:"))
        	model.addAttribute("response", response);
      
      //  session.setAttribute("state",TestClient.addCharStateValue(character, amount, state));
        else {
        model.addAttribute("stateValue", response);
        session.setAttribute("state",response);
        logger.info("User "+session.getAttribute("user")+" : deleted "+character + " from previous state "+state);
        }
    }
	
	
	@RequestMapping( params="disAction",method=RequestMethod.GET )
    public void action1(HttpSession session, Model model)
    {
        logger.info("disAction block called");
        String state = session.getAttribute("state").toString();
        String response = TestClient.getStateValue(state);
        logger.info("sumValue>>"+response);
        
        if(response.contains("Error:"))
        	model.addAttribute("response", response);
        else {
        	model.addAttribute("display", response);
        	model.addAttribute("stateValue",state);	
        }
        	
        
  
       
    }
	
	
	@RequestMapping(params="saveAction",method=RequestMethod.POST)
    public void SaveState ( HttpSession session)
    {
		
		String value = "kumar123kutti";
		String token = "ad4grgrgs";
		
		List<Users> usrList = userRepository.findByTokenAndFirstName(token, session.getAttribute("user").toString());
		
		for (Users usr : usrList) {
            logger.info("JPA>>"+usr.getFirstName()+usr.getToken());
        }
		Users usrObj = new Users();
		usrObj.setFirstName(session.getAttribute("user").toString());
		usrObj.setPassword(session.getAttribute("pwd").toString());
		usrObj.setActive("active");
		usrObj.setState(session.getAttribute("state").toString());
		usrObj.setToken(TestClient.tokenJWT);
		Date date = new Date(Calendar.getInstance().getTimeInMillis());
		usrObj.setDate(date);
		logger.info(session.getAttribute("user").toString()+session.getAttribute("pwd").toString());
		if(usrList.isEmpty())
			userRepository.save(usrObj);
		else
		userRepository.updateState( value , token);
				//TestClient.tokenJWT);
		
		for (Users ur : userRepository.findAll()) {
            logger.info("JPA>>"+ur.getFirstName()+ur.getToken());
        }
        
		
    }
	
	@RequestMapping(params="sumAction",method=RequestMethod.GET)
    public void action2(HttpSession session, Model model)
    {
        logger.info("sumAction block called");
        String state = session.getAttribute("state").toString();
        String response = TestClient.getSumStateValue(state);
        logger.info("sumValue>>"+response);
        
        if(response.contains("Error:"))
        	model.addAttribute("response", response);
        else {
        	model.addAttribute("sum", response);
        	model.addAttribute("stateValue",state);	
        }
    }
	
	@RequestMapping(params="charAction",method=RequestMethod.GET)
    public void action3(HttpSession session, Model model)
    {
        logger.info("charAction block called");
        String state = session.getAttribute("state").toString();
        String response = TestClient.getCharStateValue(state);
        logger.info("chaeValue>>"+response);
        
        
        if(response.contains("Error:"))
        	model.addAttribute("response", response);
        else {
        	model.addAttribute("char", response);
        	model.addAttribute("stateValue",state);	
        }
    }
	
	
	@RequestMapping(params="/logout",  method=RequestMethod.GET)
	public  String sayOut(Users user, Model model, HttpSession session) {
	
		session.removeAttribute("state");
		return "logOut";
		
	}
	
	@RequestMapping(value="/welcome" , method=RequestMethod.GET)
	public  String saySecureHello(HttpSession session ) {
		
		return "login";
		
	}
	
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public  String sayError() {
		
		return "display";
		
	}
	
	@RequestMapping(value="/error", method=RequestMethod.GET)
	public  String sayEError() {
		
		return "display";
		
	}
}
