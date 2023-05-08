package ru.xpressed.springreactservercoursework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xpressed.springreactservercoursework.entity.dto.AttendanceDTO;
import ru.xpressed.springreactservercoursework.entity.dto.PerformanceDTO;
import ru.xpressed.springreactservercoursework.service.PerformanceService;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/performance")
public class PerformanceController {
    private final PerformanceService performanceService;

    @Operation(summary = "POST Performance")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PerformanceDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check body)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @PostMapping("/add")
    public ResponseEntity<?> addPerformance(@Valid @RequestBody PerformanceDTO performanceDTO, BindingResult bindingResult) {
        return performanceService.addPerformance(performanceDTO, bindingResult);
    }

    @Operation(summary = "GET All Performances")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PerformanceDTO.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/all")
    public ResponseEntity<?> getAllPerformances() {
        return performanceService.getAllPerformances();
    }

    @Operation(summary = "GET User's Performances")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PerformanceDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check Username param)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/user")
    public ResponseEntity<?> getUserPerformances(@RequestParam("username") String username) {
        return performanceService.getUserPerformances(username);
    }

    @Operation(summary = "GET Performance by ID")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PerformanceDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check ID param)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/id")
    public ResponseEntity<?> getPerformanceByID(@RequestParam("id") Integer id) {
        return performanceService.getPerformanceByID(id);
    }

    @Operation(summary = "GET Performance by Params")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PerformanceDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check params)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @GetMapping("/params")
    public ResponseEntity<?> getPerformances(@RequestParam("name") String name,
                                             @RequestParam("mark") String mark,
                                             @RequestParam("year") String year,
                                             @RequestParam("username") String username) {
        return performanceService.getPerformances(name, mark, year, username);
    }

    @Operation(summary = "DELETE Performance by ID")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PerformanceDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check ID param)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePerformance(@RequestParam("id") Integer id) {
        return performanceService.deletePerformance(id);
    }

    @Operation(summary = "PATCH Performance")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PerformanceDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request (Check body)")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody PerformanceDTO performanceDTO, BindingResult bindingResult, @RequestParam("id") Integer id) {
        return performanceService.updatePerformance(performanceDTO, bindingResult, id);
    }
}
