package ru.xpressed.springreactservercoursework.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import ru.xpressed.springreactservercoursework.entity.dto.AttendanceDTO;

import javax.validation.Valid;

public interface AttendanceService {
    ResponseEntity<?> addAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO, BindingResult bindingResult);
    ResponseEntity<?> getAllAttendances();
    ResponseEntity<?> getUserAttendances(String username);
    ResponseEntity<?> getAttendanceByID(Integer id);
    ResponseEntity<?> getAttendances(String date, String enterTime, String exitTime, String username);
    ResponseEntity<?> deleteAttendance(Integer id);
    ResponseEntity<?> updateAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO, BindingResult bindingResult, Integer id);
}
