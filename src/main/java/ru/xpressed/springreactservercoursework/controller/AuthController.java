package ru.xpressed.springreactservercoursework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xpressed.springreactservercoursework.entity.dto.CheckDTO;
import ru.xpressed.springreactservercoursework.entity.dto.LoginDTO;
import ru.xpressed.springreactservercoursework.entity.dto.RegistrationDTO;
import ru.xpressed.springreactservercoursework.entity.dto.UserDTO;
import ru.xpressed.springreactservercoursework.service.AuthService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "POST Registration User")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistrationDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check body)")
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationDTO registrationDTO, BindingResult bindingResult) throws MessagingException {
        return authService.registration(registrationDTO, bindingResult);
    }

    @Operation(summary = "GET Result of Token verification (E-Mail)")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check token)")
    @GetMapping("/verify")
    public ResponseEntity<?> verification(@RequestParam("token") String token) {
        return authService.verification(token);
    }

    @Operation(summary = "POST Logging in User")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check body)")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @Operation(summary = "POST Validating JWT")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CheckDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check body)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody CheckDTO checkDTO) {
        return authService.check(checkDTO);
    }
}
