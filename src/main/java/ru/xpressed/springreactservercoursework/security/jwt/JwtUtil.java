package ru.xpressed.springreactservercoursework.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.xpressed.springreactservercoursework.repository.UserRepository;

import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtil {
    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    @Value("${jwt.expires_hours}")
    private Long EXPIRES_HOURS;

    private final UserRepository userRepository;

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token.substring(7));
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractAllClaims(token.substring(7)).get("role").toString();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String createToken(String role, String subject) {
        return Jwts.builder().claim("role", role).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * EXPIRES_HOURS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public String generateToken(UserDetails userDetails) {
        return "Bearer " + createToken(userDetails.getAuthorities().stream().toList().get(0).toString(), userDetails.getUsername());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            String role = Objects.requireNonNull(userRepository.findById(username).orElse(null)).getRoles().get(0).toString();
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && role.equals(extractRole(token)));
        } catch (UsernameNotFoundException | SignatureException e) {
            return false;
        }
    }
}
