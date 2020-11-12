package util;

import domain.User;
import exception.UnauthorizeException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.UserMapper;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Jwt {
    private Key key;
    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    public void keySetter() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    private Date getExpirationTime(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    public String generateToken(Long id) {
        // header
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        // payload
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", id);
        claims.put("exp", getExpirationTime(1));

        // Generate token
        String token = Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .signWith(key)
                .compact();

        return token;
    }

    public User validateToken() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        if (token == null) {
            throw new UnauthorizeException("토큰이 없습니다.");
        }
        if (!token.startsWith("Bearer ")) {
            throw new UnauthorizeException("토큰 형식이 틀립니다. ('Bearer '뒤에 토큰이 붙습니다.)");
        }

        token = token.substring(7);
        try {
            Claims claims = Jwts.parser().setSigningKey(this.key).parseClaimsJws(token).getBody();
            Long userId = Long.parseLong(claims.getSubject());
            return userMapper.getUserById(userId);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizeException("토큰이 만료되었습니다.");
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new UnauthorizeException("토큰이 유효하지 않습니다.");
        }
    }
}
