package Authentication;

import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import bean.AuthBean;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Provider
@SecureAuth
public class SecurityFilter implements ContainerRequestFilter{

	private static final String BEARER = "Bearer";
	private static final byte[] SECRET = Base64.getDecoder().decode("fQQL4ucrPErtTqAxSvOV7XVadkKfIHGArw3Od5kYLYQ=");
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		String authString = requestContext.getHeaderString(AUTHORIZATION);
		
		if(authString == null || authString.isEmpty() || !authString.startsWith(BEARER)) {
			
			System.out.println("Invalid Authentication Header.");
			throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED));
		}
		
		String token = authString.substring(BEARER.length()).trim();
		System.out.println(token);

		try {
				Jwts.parser()
					.setSigningKey(Keys.hmacShaKeyFor(SECRET))
					.requireSubject("CUSTOMER")
					.parseClaimsJwt(token);
		}catch(Exception e) {
			
			try {
				Jwts.parser()
					.setSigningKey(Keys.hmacShaKeyFor(SECRET))
					.requireSubject("ADMIN")
					.parseClaimsJwt(token);
			}catch(Exception ex) {
				ex.printStackTrace();
				System.out.println("Authentication Failed.");
				throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED));
			}
		}
		
		System.out.println("Authentication Successful");
	}
	
	public static AuthBean tokenGenerator(String user) {
		
		String token = "";
		Instant now = Instant.now();
		Date expiry = Date.from(now.plus(1, ChronoUnit.DAYS));
		Calendar temp = Calendar.getInstance();
		temp.setTime(expiry);
		
		token = Jwts.builder()
				.setSubject("CUSTOMER")
				.claim("email", user)
				.setIssuedAt(Date.from(now))
				.setExpiration(expiry)
				.compact();
		
		token = SecurityFilter.BEARER +" " +token;
		
		return new AuthBean(token, String.format("%d-%d-%d", temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DAY_OF_MONTH)));
	}

}
