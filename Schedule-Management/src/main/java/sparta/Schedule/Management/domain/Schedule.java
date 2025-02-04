package sparta.Schedule.Management.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Schedule {

    @Id
    private Long id;
    private String todo;
    private Long authorId;
    private String userName;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Schedule(String todo,String userName,Long authorId, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.todo = todo;
        this.userName = userName;
        this.authorId = authorId;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
