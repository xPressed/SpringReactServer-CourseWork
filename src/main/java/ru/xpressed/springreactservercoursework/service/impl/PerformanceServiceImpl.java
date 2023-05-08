package ru.xpressed.springreactservercoursework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.xpressed.springreactservercoursework.entity.Performance;
import ru.xpressed.springreactservercoursework.entity.User;
import ru.xpressed.springreactservercoursework.entity.dto.PerformanceDTO;
import ru.xpressed.springreactservercoursework.repository.PerformanceRepository;
import ru.xpressed.springreactservercoursework.repository.UserRepository;
import ru.xpressed.springreactservercoursework.security.jwt.JwtUtil;
import ru.xpressed.springreactservercoursework.service.PerformanceService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private List<PerformanceDTO> rawToDTO(List<Performance> performanceList) {
        List<PerformanceDTO> performanceDTOList = new ArrayList<>();
        performanceList.forEach(performance -> performanceDTOList.add(new PerformanceDTO(performance.getId(), performance.getName(), performance.getMark(), performance.getYear(), performance.getUser().getUsername())));
        return performanceDTOList;
    }

    @Override
    public ResponseEntity<?> addPerformance(PerformanceDTO performanceDTO, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((e) -> response.put(((FieldError) e).getField(), e.getDefaultMessage()));
            return ResponseEntity.ok().body(response);
        }

        User user = userRepository.findById(performanceDTO.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.ok().body(Map.of("error", "User not found!"));
        }

        Performance performance = Performance.builder()
                .name(performanceDTO.getName())
                .mark(performanceDTO.getMark())
                .year(performanceDTO.getYear())
                .user(user).build();
        performanceRepository.save(performance);
        return ResponseEntity.ok().body(Map.of("isSaved", true));
    }

    @Override
    public ResponseEntity<?> getAllPerformances() {
        List<Performance> performanceList = performanceRepository.findAll();
        return ResponseEntity.ok().body(rawToDTO(performanceList));
    }

    @Override
    public ResponseEntity<?> getUserPerformances(String username, String token) {
        if (!Objects.equals(username, jwtUtil.extractUsername(token)) && !Objects.equals(jwtUtil.extractRole(token), "ROLE_ADMIN") && !Objects.equals(jwtUtil.extractRole(token), "ROLE_TEACHER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User user = userRepository.findById(username).orElse(null);
        if (user == null) {
            return ResponseEntity.ok().body(Map.of("error", "User not found!"));
        }

        return ResponseEntity.ok().body(rawToDTO(user.getPerformances()));
    }

    @Override
    public ResponseEntity<?> getPerformanceByID(Integer id) {
        Performance performance = performanceRepository.findById(id).orElse(null);

        if (performance == null) {
            return ResponseEntity.ok().body(Map.of("error", "Performance not found!"));
        }

        PerformanceDTO performanceDTO = new PerformanceDTO(performance.getId(), performance.getName(), performance.getMark(), performance.getYear(), performance.getUser().getUsername());
        return ResponseEntity.ok().body(performanceDTO);
    }

    @Override
    public ResponseEntity<?> getPerformances(String name, String mark, String year, String username) {
        List<Performance> performanceList = performanceRepository.findByParams(name, mark, year, username);
        return ResponseEntity.ok().body(rawToDTO(performanceList));
    }

    @Override
    public ResponseEntity<?> deletePerformance(Integer id) {
        if (id == null) {
            return ResponseEntity.ok().body(Map.of("id" , "ID must not be null!"));
        }

        if (!performanceRepository.existsById(id)) {
            return ResponseEntity.ok().body(Map.of("error", "Performance not found!"));
        }

        performanceRepository.removeById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> updatePerformance(PerformanceDTO performanceDTO, BindingResult bindingResult, Integer id) {
        Map<String, String> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((e) -> response.put(((FieldError) e).getField(), e.getDefaultMessage()));
            return ResponseEntity.ok().body(response);
        }

        User user = userRepository.findById(performanceDTO.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.ok().body(Map.of("error", "User not found!"));
        }

        Performance performance = performanceRepository.findById(id).orElse(null);
        if (performance == null) {
            return ResponseEntity.ok().body(Map.of("error", "Performance not found!"));
        }

        performance = Performance.builder()
                .id(id)
                .name(performanceDTO.getName())
                .mark(performanceDTO.getMark())
                .year(performanceDTO.getYear())
                .user(user).build();
        performanceRepository.save(performance);
        return ResponseEntity.ok().body(Map.of("isUpdated", true));
    }
}
