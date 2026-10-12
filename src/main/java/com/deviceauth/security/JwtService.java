package com.deviceauth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final Key key; private final long expirationMs;
    public JwtService(@Value("${deviceauth.jwt.secret}") String secret,@Value("${deviceauth.jwt.expiration-ms}") long expirationMs){this.key=Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));this.expirationMs=expirationMs;}
    public String generate(String email,String role){Date now=new Date();return Jwts.builder().subject(email).claim("role",role).issuedAt(now).expiration(new Date(now.getTime()+expirationMs)).signWith(key).compact();}
    public String extractEmail(String token){return Jwts.parser().verifyWith((javax.crypto.SecretKey)key).build().parseSignedClaims(token).getPayload().getSubject();}
    public boolean isValid(String token,String email){try{return email.equals(extractEmail(token));}catch(Exception ex){return false;}}
}
