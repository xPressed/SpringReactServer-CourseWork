package ru.xpressed.springreactservercoursework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xpressed.springreactservercoursework.entity.dto.UserDTO;
import ru.xpressed.springreactservercoursework.service.UsersService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsersController {
    private final UsersService usersService;

    @Operation(summary = "GET all Users")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return usersService.getAllUsers();
    }

    @Operation(summary = "GET Users by ID")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check Username param)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/id")
    public ResponseEntity<?> getUserById(@RequestParam("username") String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return usersService.getUserByID(username, token);
    }

    @Operation(summary = "GET Users by Params")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check params)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/params")
    public ResponseEntity<?> getUsers(@RequestParam("surname") Optional<String> surname,
                                      @RequestParam("name") Optional<String> name,
                                      @RequestParam("patronymic") Optional<String> patronymic,
                                      @RequestParam("groupName") Optional<String> groupName) {
        return usersService.getUsers(surname.orElse(null), name.orElse(null), patronymic.orElse(null), groupName.orElse(null));
    }

    @Operation(summary = "DELETE User by ID")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check Username param)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam("username") String username) {
        return usersService.deleteUser(username);
    }

    @Operation(summary = "PATCH User")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check body)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        return usersService.updateUser(userDTO, bindingResult);
    }
}
