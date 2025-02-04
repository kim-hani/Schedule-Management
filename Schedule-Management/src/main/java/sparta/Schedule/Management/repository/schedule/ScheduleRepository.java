package sparta.Schedule.Management.repository.schedule;

import sparta.Schedule.Management.domain.Schedule;
import sparta.Schedule.Management.dto.responseDto.ScheduleResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    List<Schedule> findSchedulesByAuthorId(Long authorId);

    Optional<Schedule> findScheduleById(Long id);

    int updateSchedule(Long id, String todo, String userName, LocalDateTime updatedAt);

    void deleteSchedule(Long id);
}
