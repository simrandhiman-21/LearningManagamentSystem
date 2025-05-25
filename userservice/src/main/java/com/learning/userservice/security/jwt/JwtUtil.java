package com.learning.userservice.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final SecretKey key;

    public JwtUtil() {
        // Use a properly sized secret key (HS256 requires 256 bits)
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // Or, if you want to use a base64 secret string, ensure it's at least 32 bytes when decoded:
        // this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("your-long-base64-secret-string"));
    }


    public String extractUsername(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key) // use your key field here
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(key) // use your key field here
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}
