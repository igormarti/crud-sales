package io.github.igormarti.service.security.jwt;

import io.github.igormarti.VendasApplication;
import io.github.igormarti.domain.entity.Role;
import io.github.igormarti.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;
    @Value("${security.jwt.key}")
    private String keySign;

    public String generateToken(Usuario usuario){
        LocalDateTime dateTimeExp = LocalDateTime.now().plusMinutes(Long.valueOf(expiration));
        Date date = Date.from(dateTimeExp.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setClaims(generateClaims(usuario))
                .setSubject(usuario.getLogin())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, keySign)
                .compact();
    }

    public Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(keySign)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenIsValid(String token){
        try {
            Claims claims = getClaims(token);
            Date dateExpiration = claims.getExpiration();
            LocalDateTime dateTimeExp = dateExpiration.toInstant()
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDateTime();
            return ! LocalDateTime.now().isAfter(dateTimeExp);
        }catch (Exception e){
            return false;
        }
    }

    public String getLoginByToken(String token) throws ExpiredJwtException {
        return (String) getClaims(token).getSubject();
    }

    private Map<String, Object> generateClaims(Usuario usuario){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("username", usuario.getLogin());
        claims.put("authorities", usuario.getRoles());

        return claims;
    }

    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
        JwtService service = context.getBean(JwtService.class);
        Usuario usuario = new Usuario(1, "fulano", "123456", Arrays.asList(new Role("TESTE")));
        String token = service.generateToken(usuario);
        System.out.println(token);
        System.out.println("Token é válido:"+ service.tokenIsValid(token));
        System.out.println("Login:"+ service.getLoginByToken(token));
    }

}
