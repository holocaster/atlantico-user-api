package br.gov.inst.atlan.userapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateToken(String username, boolean admin) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", username);
		claims.put("exp", new Date(System.currentTimeMillis() + expiration * 1000).getTime() / 1000L);
		claims.put("admin", admin);
		return  Jwts.builder().setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, this.secret.getBytes()).compact();
	}

	public boolean isValidToken(final String token) {
		Claims claims = this.getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	private Claims getClaims(final String token) {
		try {
			return Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public String getUserName(final String token) {
		Claims claims = this.getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}
}
