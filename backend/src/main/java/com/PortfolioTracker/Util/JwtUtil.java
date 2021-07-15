package com.PortfolioTracker.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/*

*/
@Service
public class JwtUtil {

    private String SECRET_KEY = "secret";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Takes in userDetails object and creates a JWT token. Claims found in the payload. Claims are statements about the user and additonal data.
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    //The subject is the username of the person being authenticated
    private String createToken(Map<String, Object> claims, String subject) {
        // Uses a builder pattern
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))  //H
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  //Setting the expiration date, here it's 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();  // .compact() finishes the builder pattern
    }

    public Boolean validateToken(String token, UserDetails userDetails) {  // Here when the front end uses a API, they pass along the JWt and we test it against the 
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}