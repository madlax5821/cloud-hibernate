package com.xiaofei.li.service.jwtService;

import com.xiaofei.li.entity.FullName;
import com.xiaofei.li.entity.User;
import io.jsonwebtoken.*;
import org.springframework.boot.actuate.endpoint.web.Link;
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
    
    private static final String SECRET="xiaofeifei";
    private static final String ISSUER="rockville";
    
    private static final Long EXPIRATION=300L*1000;
    private static final Long EXPIRATION_REMEMBER_ME=3600L*1000;
    
    public static String generateToken(JwtUser user, boolean ifRemember){
        long expiration = ifRemember?EXPIRATION_REMEMBER_ME:EXPIRATION;
        
        Map<String, Object> map = new HashMap<>();
        map.put("id",user.getId());
        map.put("username",user.getUsername());
        map.put("authorities",user.getAuthorities());

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuer(ISSUER)
                .setClaims(map)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
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
        user.setFullName(new FullName((String) claims.get("username"),null));
        
        List<LinkedHashMap<String, String>> roleMap = (List<LinkedHashMap<String, String>>) claims.get("authorities");
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        for (LinkedHashMap<String, String> role:roleMap){
            authorities.add(new SimpleGrantedAuthority(role.get("authority")));
        }
        return new JwtUser(user,null,authorities);
    }
}