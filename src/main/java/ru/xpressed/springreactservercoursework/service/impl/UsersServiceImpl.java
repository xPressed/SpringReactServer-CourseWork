package ru.xpressed.springreactservercoursework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.xpressed.springreactservercoursework.entity.Role;
import ru.xpressed.springreactservercoursework.entity.User;
import ru.xpressed.springreactservercoursework.entity.dto.UserDTO;
import ru.xpressed.springreactservercoursework.repository.UserRepository;
import ru.xpressed.springreactservercoursework.security.SecurityConfig;
import ru.xpressed.springreactservercoursework.security.jwt.JwtUtil;
import ru.xpressed.springreactservercoursework.service.UsersService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private List<UserDTO> rawToDTO(List<User> userList) {
        List<UserDTO> userDTOList = new ArrayList<>();
        userList.forEach((user -> userDTOList.add(new UserDTO(user.getUsername(), user.getSurname(), user.getName(), user.getPatronymic(), user.getGroupName(), user.getRoles().get(0).toString()))));
        return userDTOList;
    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok().body(rawToDTO(userList));
    }

    @Override
    public ResponseEntity<?> getUserByID(String username, String token) {
        if (!Objects.equals(username, jwtUtil.extractUsername(token)) && !Objects.equals(jwtUtil.extractRole(token), "ROLE_ADMIN") && !Objects.equals(jwtUtil.extractRole(token), "ROLE_TEACHER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User user = userRepository.findById(username).orElse(null);

        if (user == null) {
            return ResponseEntity.ok().body(Map.of("error", "User not found!"));
        }

        UserDTO userDTO = new UserDTO(user.getUsername(), user.getSurname(), user.getName(), user.getPatronymic(), user.getGroupName(), user.getRoles().get(0).toString());
        return ResponseEntity.ok().body(userDTO);
    }

    @Override
    public ResponseEntity<?> getUsers(String surname, String name, String patronymic, String groupName) {
        List<User> userList = userRepository.findByParams(surname, name, patronymic, groupName);
        return ResponseEntity.ok().body(rawToDTO(userList));
    }

    @Override
    public ResponseEntity<?> deleteUser(String username) {
        if (username == null) {
            return ResponseEntity.badRequest().body(Map.of("username", "Username must not be null!"));
        }

        if (!userRepository.existsById(username)) {
            return ResponseEntity.ok().body(Map.of("error", "User not found!"));
        }

        userRepository.deleteById(username);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> updateUser(UserDTO userDTO, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((e) -> response.put(((FieldError) e).getField(), e.getDefaultMessage()));
            return ResponseEntity.ok().body(response);
        }

        User user = userRepository.findById(userDTO.getUsername()).orElse(null);

        if (user == null) {
            return ResponseEntity.ok().body(Map.of("error", "User not found!"));
        }

        user = User.builder()
                .username(userDTO.getUsername())
                .password(user.getPassword())
                .surname(userDTO.getSurname())
                .name(userDTO.getName())
                .patronymic(userDTO.getPatronymic())
                .roles(List.of(Role.valueOf(userDTO.getRole())))
                .groupName(userDTO.getGroupName()).build();
        userRepository.save(user);
        return ResponseEntity.ok().body(Map.of("isUpdated", true));
    }
}
