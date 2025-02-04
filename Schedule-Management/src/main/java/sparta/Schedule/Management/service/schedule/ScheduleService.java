package sparta.Schedule.Management.service.schedule;

import sparta.Schedule.Management.dto.requestDto.ScheduleRequestDto;
import sparta.Schedule.Management.dto.responseDto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);
    List<ScheduleResponseDto> findAllSchedules();
    ScheduleResponseDto findScheduleById(Long id);
    ScheduleResponseDto updateTodo(Long id, String todo, String userName, String password);

    void deleteSchedule(Long id, String password);

    List<ScheduleResponseDto> findSchedulesByUserId(Long userId);
}
