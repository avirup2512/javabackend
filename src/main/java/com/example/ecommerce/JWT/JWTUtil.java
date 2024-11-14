package com.example.ecommerce.JWT;

import java.net.Authenticator;
import java.nio.file.attribute.UserPrincipal;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTUtil {

    private String SECRET_KEY = "ajshdjwbkwbcuweuebbcqwdhiqwhdqdkadiqdh2384238y827hedd287328r9heqdn9238ydakdadbm";

    // Generate a JWT Token
    public String generateToken(String email)
    {
        HashMap<String, Object> claims = new HashMap<String,Object>();
        return createToken(claims, email);
    }
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private String createToken(HashMap<String,Object> claims, String subject)
    {
        claims.put("email",subject);
        return Jwts.builder()
            .claims(claims)
            .issuedAt(new Date(0))
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
            .signWith(getSignInKey(), Jwts.SIG.HS256)
            .compact();
    }
    public String resolveToken(HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }
        return null;
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
        .verifyWith(getSignInKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(0));
    }

   public String extractEmail(String token)
   {
    final Claims claims = extractAllClaims(token);
    Object email = claims.get("email");
    return (String)email;
   }
   public Long extractId(String token)
   {
    final Claims claims = extractAllClaims(token);
    Object id = claims.get("id");
    return (Long)id;
   }
   public Boolean validateToken(String token, String email) {
    final String tokenUseremail = extractEmail(token);
    return (tokenUseremail.equals(email) && !isTokenExpired(token));
}
}
