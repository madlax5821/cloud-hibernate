package com.xiaofei.li.service.jwtService;

import com.xiaofei.li.entity.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Author: xiaofei
 * Date: 2022-05-16, 0:15
 * Description:
 */
@Service
public class JwtTokenService {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";
    
    private static final String SECRET = "XIAOFEI";
    private static final String ISSUER = "ROCKVILLE";
    
    private static final Long EXPIRATION = 300L*1000;
    private static final Long EXPIRATION_REMEMBER_ME = 3600L*1000;
    
    public static String generateToken(JwtUser user, boolean ifRemember){
        long expiration = ifRemember?EXPIRATION_REMEMBER_ME:EXPIRATION;
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("username",user.getUsername());
        claims.put("authorities",user.getAuthorities());
        
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .setClaims(claims)
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .compact();
    }
    
    public static Claims decryptToken(String token){
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }
    
    public static boolean ifExpired(String token){
        return decryptToken(token).getExpiration().before(new Date());
    }
    
    public static JwtUser parseTokenToJwtUser(String token){
        Claims claims = decryptToken(token);
        User user = new User();
        user.setId((Integer) claims.get("id"));
        user.setEmail((String) claims.get("username"));
        List<LinkedHashMap<String, String>> roleMap = (List<LinkedHashMap<String, String>>) claims.get("authorities");
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (LinkedHashMap<String, String> role:roleMap){
            authorities.add(new SimpleGrantedAuthority(role.get("authority")));
        }
        return new JwtUser(user,null,authorities);
    }
}


