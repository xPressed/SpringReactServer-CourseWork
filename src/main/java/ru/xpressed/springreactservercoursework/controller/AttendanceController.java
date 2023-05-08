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
import ru.xpressed.springreactservercoursework.entity.dto.AttendanceDTO;
import ru.xpressed.springreactservercoursework.service.AttendanceService;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @Operation(summary = "POST Attendance")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check body)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @PostMapping("/add")
    public ResponseEntity<?> addAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO, BindingResult bindingResult) {
        return attendanceService.addAttendance(attendanceDTO, bindingResult);
    }

    @Operation(summary = "GET All Attendances")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDTO.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/all")
    public ResponseEntity<?> getAllAttendances() {
        return attendanceService.getAllAttendances();
    }

    @Operation(summary = "GET User's Attendances")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check Username param)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/user")
    public ResponseEntity<?> getUserAttendances(@RequestParam("username") String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return attendanceService.getUserAttendances(username, token);
    }

    @Operation(summary = "GET Attendance by ID")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check ID param)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/id")
    public ResponseEntity<?> getAttendanceByID(@RequestParam("id") Integer id) {
        return attendanceService.getAttendanceByID(id);
    }

    @Operation(summary = "GET Attendance by Params")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check params)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/params")
    public ResponseEntity<?> getAttendances(@RequestParam("date") String date,
                                            @RequestParam("enterTime") String enterTime,
                                            @RequestParam("exitTime") String exitTime,
                                            @RequestParam("username") String username) {
        return attendanceService.getAttendances(date, enterTime, exitTime, username);
    }

    @Operation(summary = "DELETE Attendance")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check ID param)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAttendances(@RequestParam("id") Integer id) {
        return attendanceService.deleteAttendance(id);
    }

    @Operation(summary = "PATCH Attendance")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check body)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @PatchMapping("/update")
    public ResponseEntity<?> updateAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO, BindingResult bindingResult, @RequestParam("id") Integer id) {
        return attendanceService.updateAttendance(attendanceDTO, bindingResult, id);
    }
}
