package com.xiaofei.li.service.jwtService;

import com.xiaofei.li.entity.FullName;
import com.xiaofei.li.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    public static final String TOKEN_HEADER="Authorization";
    public static final String TOKEN_PREFIX="Bearer ";

    private static final String SECRET="xiaofeifei";
    private static final String ISSUER="rockville";

    private static final Long EXPIRATION=3600L;
    private static final Long EXPIRATION_REMEMBER_ME=604800L;

    public static String createToken(JwtUser jwtUser, boolean ifRememberMe){
        long expiration = ifRememberMe?EXPIRATION_REMEMBER_ME:EXPIRATION;

        Map<String, Object> jtwClaims =new HashMap<>();
        jtwClaims.put("id",jwtUser.getId());
        jtwClaims.put("username",jwtUser.getUsername());
        jtwClaims.put("authorities",jwtUser.getAuthorities());

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .setClaims(jtwClaims)
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiration*1000))
                .compact();
        return token;
    }

    public static boolean ifExpired(String token){
        return decryptToken(token).getExpiration().before(new Date());
    }

    public static Claims decryptToken(String token){
        return Jwts
                .parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

    }

    public static JwtUser parseTokenToJwtUser(String token){
        Claims claims = decryptToken(token);
        User user = new User();
        user.setId((Integer) claims.get("id"));
        user.setFullName(new FullName((String) claims.get("username"),null));
        List<LinkedHashMap<String, String>> roles = (List<LinkedHashMap<String, String>>) claims.get("authorities");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (LinkedHashMap<String, String> role:roles){
            authorities.add(new SimpleGrantedAuthority(role.get("authority")));
        }
        return new JwtUser(user,null,authorities);
    }
}
