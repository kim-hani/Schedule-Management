package sparta.Schedule.Management.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sparta.Schedule.Management.domain.Schedule;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    
    private Long id;
    private String todo;
    private String userName;
    private Long authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.todo = schedule.getTodo();
        this.userName = schedule.getUserName();
        this.authorId = schedule.getAuthorId();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
    
}
