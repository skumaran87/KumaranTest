package com.silverrailtech.SpringRestService;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {

	
	
	  static final long EXPIRATIONTIME = 4_000_000; // 10 days
	  static final String SECRET = "ThisIsASecret";
	  static final String TOKEN_PREFIX = "Bearer";
	  static final String HEADER_STRING = "Authorization";
	  
	  public TokenAuthenticationService() {
			// TODO Auto-generated constructor stub
		}

	  static void addAuthentication(HttpServletResponse res, String username) {
	    String JWT = Jwts.builder()
	        .setSubject(username)
	        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
	        .signWith(SignatureAlgorithm.HS512, SECRET)
	        .compact();
	    System.out.println("JWT token"+TOKEN_PREFIX+ " " +JWT+" >>>User Name >>"+username);
	    res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	  }

	  static Authentication getAuthentication(HttpServletRequest request) {
	    String token = request.getHeader(HEADER_STRING);
	    if (token != null) {
	      // parse the token.
	      String user = Jwts.parser()
	          .setSigningKey(SECRET)
	          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
	          .getBody()
	          .getSubject();
	      System.out.println("user suthentiacation>>"+user);

	      return user != null ?
	          new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) :
	          null;
	    }
	    return null;
	  }


}
