package ru.xpressed.springreactservercoursework.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import ru.xpressed.springreactservercoursework.entity.dto.UserDTO;

import javax.validation.Valid;

public interface UsersService {
    ResponseEntity<?> getAllUsers();
    ResponseEntity<?> getUserByID(String username, String token);
    ResponseEntity<?> getUsers(String surname, String name, String patronymic, String groupName);

    ResponseEntity<?> deleteUser(String username);
    ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult);
}
