package io.github.kiriinteo.visuvarejo.infra.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    /*private static final String SECRET =
            "bXlTdXBlclNlY3JldEtleUZvclZpc3VWYXJlam9KU1Rlc3QxMjM0NTY3ODkwMTIz"; */

    private final Key key =
        Keys.hmacShaKeyFor("minhachavesecretaextremamentesegura123456".getBytes());

    private final long EXPIRATION = 86400000;

    public String generateToken(String email, String role, String companyId) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("companyId", companyId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace(); 
            return false;
        }
    }

    public String getCompanyId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("companyId", String.class);
    }

    public String getRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
}