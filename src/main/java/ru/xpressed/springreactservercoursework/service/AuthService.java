package ru.xpressed.springreactservercoursework.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import ru.xpressed.springreactservercoursework.entity.dto.CheckDTO;
import ru.xpressed.springreactservercoursework.entity.dto.LoginDTO;
import ru.xpressed.springreactservercoursework.entity.dto.RegistrationDTO;

import javax.mail.MessagingException;
import javax.validation.Valid;

public interface AuthService {
    ResponseEntity<?> registration(@Valid @RequestBody RegistrationDTO registrationDTO, BindingResult bindingResult) throws MessagingException;
    ResponseEntity<?> verification(String token);
    ResponseEntity<?> login(@RequestBody LoginDTO loginDTO);
    ResponseEntity<?> check(@RequestBody CheckDTO checkDTO);
}
