package ru.xpressed.springreactservercoursework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.xpressed.springreactservercoursework.entity.Attendance;
import ru.xpressed.springreactservercoursework.entity.User;
import ru.xpressed.springreactservercoursework.entity.dto.AttendanceDTO;
import ru.xpressed.springreactservercoursework.repository.AttendanceRepository;
import ru.xpressed.springreactservercoursework.repository.UserRepository;
import ru.xpressed.springreactservercoursework.service.AttendanceService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    private List<AttendanceDTO> rawToDTO(List<Attendance> attendanceList) {
        List<AttendanceDTO> attendanceDTOList = new ArrayList<>();
        attendanceList.forEach(attendance -> attendanceDTOList.add(new AttendanceDTO(attendance.getId(), attendance.getDate(), attendance.getEnterTime(), attendance.getExitTime(), attendance.getUser().getUsername())));
        return attendanceDTOList;
    }

    @Override
    public ResponseEntity<?> addAttendance(AttendanceDTO attendanceDTO, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((e) -> response.put(((FieldError) e).getField(), e.getDefaultMessage()));
            return ResponseEntity.ok().body(response);
        }

        User user = userRepository.findById(attendanceDTO.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.ok().body(Map.of("error", "User not found!"));
        }

        Attendance attendance = Attendance.builder()
                .date(attendanceDTO.getDate())
                .enterTime(attendanceDTO.getEnterTime())
                .exitTime(attendanceDTO.getExitTime())
                .user(user).build();
        attendanceRepository.save(attendance);
        return ResponseEntity.ok().body(Map.of("isSaved", true));
    }

    @Override
    public ResponseEntity<?> getAllAttendances() {
        List<Attendance> attendanceList = attendanceRepository.findAll();
        return ResponseEntity.ok().body(rawToDTO(attendanceList));
    }

    @Override
    public ResponseEntity<?> getUserAttendances(String username) {
        User user = userRepository.findById(username).orElse(null);
        if (user == null) {
            return ResponseEntity.ok().body(Map.of("error", "User not found!"));
        }

        return ResponseEntity.ok().body(rawToDTO(user.getAttendances()));
    }

    @Override
    public ResponseEntity<?> getAttendanceByID(Integer id) {
        Attendance attendance = attendanceRepository.findById(id).orElse(null);

        if (attendance == null) {
            return ResponseEntity.ok().body(Map.of("error", "Attendance not found!"));
        }

        AttendanceDTO attendanceDTO = new AttendanceDTO(attendance.getId(), attendance.getDate(), attendance.getEnterTime(), attendance.getExitTime(), attendance.getUser().getUsername());
        return ResponseEntity.ok().body(attendanceDTO);
    }

    @Override
    public ResponseEntity<?> getAttendances(String date, String enterTime, String exitTime, String username) {
        List<Attendance> attendanceList = attendanceRepository.findByParams(date, enterTime, exitTime, username);
        return ResponseEntity.ok().body(rawToDTO(attendanceList));
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteAttendance(Integer id) {
        if (id == null) {
            return ResponseEntity.ok().body(Map.of("id", "ID must not be null!"));
        }

        if (!attendanceRepository.existsById(id)) {
            return ResponseEntity.ok().body(Map.of("error", "Attendance not found!"));
        }

        attendanceRepository.removeById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> updateAttendance(AttendanceDTO attendanceDTO, BindingResult bindingResult, Integer id) {
        Map<String, String> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((e) -> response.put(((FieldError) e).getField(), e.getDefaultMessage()));
            return ResponseEntity.ok().body(response);
        }

        User user = userRepository.findById(attendanceDTO.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.ok().body(Map.of("error", "User not found!"));
        }

        Attendance attendance = attendanceRepository.findById(id).orElse(null);
        if (attendance == null) {
            return ResponseEntity.ok().body(Map.of("error", "Attendance not found!"));
        }

        attendance = Attendance.builder()
                .id(id)
                .date(attendanceDTO.getDate())
                .enterTime(attendanceDTO.getEnterTime())
                .exitTime(attendanceDTO.getExitTime())
                .user(user).build();
        attendanceRepository.save(attendance);
        return ResponseEntity.ok().body(Map.of("isUpdated", true));
    }
}
