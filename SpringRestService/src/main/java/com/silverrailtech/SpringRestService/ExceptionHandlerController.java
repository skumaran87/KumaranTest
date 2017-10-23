package com.silverrailtech.SpringRestService;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Not found Custom Exception")
public class ExceptionHandlerController extends RuntimeException{

	public ExceptionHandlerController() {
		// TODO Auto-generated constructor stub
	}
/*	
	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class, NotFoundException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Not found Custom Exception";
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.CONFLICT, request);
    }*/
	
	@ExceptionHandler(value = { NotFoundException.class})
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response requestHandlingNoHandlerFound() {
		return Response.status(Response.Status.NOT_FOUND).entity("URL not found").build();
    }
	
}
