//package com.catalisa.squad5.security;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//import java.util.Date;
//
//public class JwtUtil {
//
//    private static final String SECRET_KEY = "your-secret-key";
//    private static final long EXPIRATION_TIME = 86400000; // 24 hours
//
//    public static String generateToken(String username) {
//        Date now = new Date();
//        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
//
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(now)
//                .setExpiration(expiration)
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
//}