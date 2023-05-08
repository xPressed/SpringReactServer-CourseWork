package ru.xpressed.springreactservercoursework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.xpressed.springreactservercoursework.entity.Role;
import ru.xpressed.springreactservercoursework.entity.User;
import ru.xpressed.springreactservercoursework.entity.dto.CheckDTO;
import ru.xpressed.springreactservercoursework.entity.dto.LoginDTO;
import ru.xpressed.springreactservercoursework.entity.dto.RegistrationDTO;
import ru.xpressed.springreactservercoursework.repository.UserRepository;
import ru.xpressed.springreactservercoursework.security.SecurityConfig;
import ru.xpressed.springreactservercoursework.security.jwt.JwtUtil;
import ru.xpressed.springreactservercoursework.service.AuthService;
import ru.xpressed.springreactservercoursework.service.EmailService;
import ru.xpressed.springreactservercoursework.service.TokenService;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final EmailService emailService;

    @Override
    public ResponseEntity<?> registration(RegistrationDTO registrationDTO, BindingResult bindingResult) throws MessagingException {
        Map<String, String> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((e) -> response.put(((FieldError) e).getField(), e.getDefaultMessage()));
            return ResponseEntity.ok().body(response);
        }

        if (userRepository.findById(registrationDTO.getUsername()).isPresent()) {
            return ResponseEntity.ok().body(Map.of("username", "This E-Mail is already taken!"));
        }

        User user = User.builder()
                .username(registrationDTO.getUsername())
                .password(securityConfig.passwordEncoder().encode(registrationDTO.getPassword()))
                .surname(registrationDTO.getSurname())
                .name(registrationDTO.getName())
                .patronymic(registrationDTO.getPatronymic())
                .roles(List.of(Role.ROLE_DEFAULT))
                .token(tokenService.generateNewToken()).build();
        userRepository.save(user);

        emailService.sendMessage(user.getUsername(), user.getToken());

        return ResponseEntity.ok().body(Map.of("isRegistered", true));
    }

    @Override
    public ResponseEntity<?> verification(String token) {
        User user = userRepository.findByToken(token).orElse(null);
        if (user == null) {
            return ResponseEntity.ok().body(Map.of("isVerified", false));
        }

        user.setRoles(List.of(Role.ROLE_STUDENT));
        user.setToken(null);
        userRepository.save(user);
        return ResponseEntity.ok().body(Map.of("isVerified", true));
    }

    @Override
    public ResponseEntity<?> login(LoginDTO loginDTO) {
        if (!userRepository.existsById(loginDTO.getUsername())) {
            return ResponseEntity.ok().body(Map.of("error", "Wrong name or password!"));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());

        if (!userDetails.isEnabled()) {
            return ResponseEntity.ok(Map.of("error", "Please, verify your E-Mail!"));
        }

        if (securityConfig.passwordEncoder().matches(loginDTO.getPassword(), userDetails.getPassword())) {
            return ResponseEntity.ok().body(Map.of("jwt", jwtUtil.generateToken(userDetails)));
        }

        return ResponseEntity.ok().body(Map.of("error", "Wrong name or password!"));
    }

    @Override
    public ResponseEntity<?> check(CheckDTO checkDTO) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(checkDTO.getUsername());
            return ResponseEntity.ok().body(Map.of("valid", jwtUtil.validateToken(checkDTO.getJwt(), userDetails)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(Map.of("valid", false));
        }
    }
}
