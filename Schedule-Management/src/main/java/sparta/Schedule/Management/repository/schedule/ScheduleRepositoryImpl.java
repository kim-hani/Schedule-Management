package sparta.Schedule.Management.repository.schedule;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import sparta.Schedule.Management.domain.Schedule;
import sparta.Schedule.Management.dto.responseDto.ScheduleResponseDto;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", schedule.getTodo());
        parameters.put("user_name", schedule.getUserName());
        parameters.put("author_id", schedule.getAuthorId());
        parameters.put("password", schedule.getPassword());
        parameters.put("created_at", schedule.getCreatedAt());
        parameters.put("updated_at", schedule.getUpdatedAt());

        Number key = insert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(
                key.longValue(),
                schedule.getTodo(),
                schedule.getUserName(),
                schedule.getAuthorId(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );

    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return jdbcTemplate.query(
                "SELECT s.*, u.name AS author_name FROM schedule s " +
                        "JOIN users u ON s.author_id = u.id ORDER BY s.updated_at DESC",
                scheduleRowMapper()
        );
    }

    @Override
    public List<Schedule> findSchedulesByAuthorId(Long authorId) {
        return jdbcTemplate.query(
                "SELECT s.*, u.name AS author_name FROM schedule s " +
                        "JOIN users u ON s.author_id = u.id WHERE s.author_id = ?",
                scheduleRowMapperV2(),
                authorId
        );
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return (ResultSet rs, int rowNum) -> new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("todo"),
                rs.getString("user_name"),
                rs.getLong("author_id"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }

    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query(
                "SELECT * FROM schedule WHERE id = ?",
                scheduleRowMapperV2(),
                id
        );
        return result.stream().findAny();
    }



    private RowMapper<Schedule> scheduleRowMapperV2() {
        return (ResultSet rs, int rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("todo"),
                rs.getLong("author_id"),
                rs.getString("user_name"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }

    @Override
    public int updateSchedule(Long id, String todo, String userName, LocalDateTime updatedAt) {
        return jdbcTemplate.update(
                "UPDATE schedule SET todo = ?, user_name = ?, updated_at = ? WHERE id = ?",
                todo,
                userName,
                updatedAt,
                id
        );
    }

    @Override
    public void deleteSchedule(Long id) {
        jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }
}
