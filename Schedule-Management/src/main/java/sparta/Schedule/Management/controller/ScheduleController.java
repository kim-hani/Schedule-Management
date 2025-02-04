package sparta.Schedule.Management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.Schedule.Management.dto.requestDto.ScheduleRequestDto;
import sparta.Schedule.Management.dto.responseDto.ScheduleResponseDto;
import sparta.Schedule.Management.service.schedule.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(requestDto), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules() {
        return new ResponseEntity<>(scheduleService.findAllSchedules(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable("id") Long id,
            @RequestBody ScheduleRequestDto requestDto
    ) {
        return new ResponseEntity<>(
                scheduleService.updateTodo(id, requestDto.getTodo(), requestDto.getUserName(), requestDto.getPassword()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable("id") Long id,
            @RequestParam("password") String password) {
        scheduleService.deleteSchedule(id,password);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ScheduleResponseDto>> findSchedulesByUserId(@PathVariable("userId") Long userId) {
        List<ScheduleResponseDto> schedules = scheduleService.findSchedulesByUserId(userId);
        return ResponseEntity.ok(schedules);
    }
}
