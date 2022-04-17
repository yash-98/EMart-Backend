package Authentication;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import bean.AuthBean;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Provider
@secureAuth
public class SecurityFilter implements ContainerRequestFilter{

	private static final String BEARER = "Bearer";
	private static final byte[] SECRET = Base64.getDecoder().decode("fQQL4ucrPErtTqAxSvOV7XVadkKfIHGArw3Od5kYLYQ=");
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		String authString = requestContext.getHeaderString(AUTHORIZATION);
		
		if(authString == null || authString.isEmpty() || !authString.startsWith(BEARER)) {
			
			System.out.println("Invalid Authentication Header.");
			return;
		}
		
		String token = authString.substring(BEARER.length()).trim();
		Jws<Claims> result;
		try {
			result =	Jwts.parser()
							.setSigningKey(Keys.hmacShaKeyFor(SECRET))
							.requireSubject("Identification")
							.parseClaimsJws(token);
		}catch(Exception e) {
			System.out.println("Authentication Failed.");
			return;
		}
		
		System.out.println("Authentication Successful");
	}
	
	public static AuthBean tokenGenerator() {
		
		String token = "";
		Instant now = Instant.now();
		Date expiry = Date.from(now.plus(1, ChronoUnit.DAYS));
		
		token = Jwts.builder()
				.setSubject("Identification")
				.setIssuedAt(Date.from(now))
				.setExpiration(expiry)
				.compact();
		
		return new AuthBean(token, String.format("%d-%d-%d", expiry.getYear(), expiry.getMonth(), expiry.getDate()));
	}

}