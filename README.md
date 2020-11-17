# Spring_JWT_Session_Example
- Spring MVC에서 JWT 또는 세션을 사용하여 로그인 및 권한인증 API 구현 예시 레포지토리 입니다.
- BCSDLab BackEnd Beginner 참고용입니다.

## JWT
### JWT 관련 라이브러리 dependency 설정
``` xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.10.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.10.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.10.5</version>
    <scope>runtime</scope>
</dependency>
```

### 생성 예시
io.jsonwebtoken 패키지의 Jwts 클래스 사용
1. builder() 호출
2. setHeader(), setClaims(), signWith() 호출
    - setHeader()
      - header를 설정하는 메소드
      - Map<String, Object> 타입의 객체를 인자로 넣어 헤더 설정 가능
    - setClaims()
      - payload를 설정하는 메소드
      - claim은 payload에 있는 하나의 key-value 데이터를 의미
      - Map<String, Object> 타입의 객체를 인자로 넣어 헤더 설정 가능
    - signWith()
      - secret key를 통해 서명하는 메소드
  
3. compact() 호출
  - token을 생성하여 리턴해주는 메소드
  
<예시 코드>
``` java
// 생략

@Component
public class Jwt {
    private Key key;
    
    // 생략

    public Jwt() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // 유효 시간 설정 및 반환
    private Date getExpirationTime(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    // JWT 생성
    public String generateToken(Long id) {
        // header 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        // payload 설정
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", id);
        claims.put("exp", getExpirationTime(1));

        // JWT 생성
        String token = Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .signWith(key) // HS256 알고리즘에 적합한 key를 사용하여 서명
                .compact();

        return token;
    }

    // 생략
}

```

### 검증 예시
io.jsonwebtoken 패키지의 Jwts 클래스 사용
1. parser() 호출
2. setSigningKey() 호출
    - 기존에 존재했었던 key를 인자로 넣어주어 호출
3. parseClaimsJws() 호출
    - 토큰을 인자로 넣어주어 호출
4. getBody() 호출
    - 클레임들(페이로드)을 가져옴
    
``` java
try {
    Claims claims = Jwts.parser().setSigningKey(this.key).parseClaimsJws(token).getBody();
    Long userId = Long.parseLong(claims.getSubject());

    // 생략
} catch (ExpiredJwtException e) {
    throw new UnauthorizeException("토큰이 만료되었습니다.");
} catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
    throw new UnauthorizeException("토큰이 유효하지 않습니다.");
}
```
