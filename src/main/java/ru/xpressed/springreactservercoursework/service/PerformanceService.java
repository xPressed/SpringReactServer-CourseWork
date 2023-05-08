package ru.xpressed.springreactservercoursework.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import ru.xpressed.springreactservercoursework.entity.dto.PerformanceDTO;

import javax.validation.Valid;

public interface PerformanceService {
    ResponseEntity<?> addPerformance(@Valid @RequestBody PerformanceDTO performanceDTO, BindingResult bindingResult);
    ResponseEntity<?> getAllPerformances();
    ResponseEntity<?> getUserPerformances(String username, String token);
    ResponseEntity<?> getPerformanceByID(Integer id);
    ResponseEntity<?> getPerformances(String name, String mark, String year, String username);
    ResponseEntity<?> deletePerformance(Integer id);
    ResponseEntity<?> updatePerformance(@Valid @RequestBody PerformanceDTO performanceDTO, BindingResult bindingResult, Integer id);
}
