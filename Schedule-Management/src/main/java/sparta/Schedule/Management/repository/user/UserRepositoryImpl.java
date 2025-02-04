package sparta.Schedule.Management.repository.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sparta.Schedule.Management.domain.User;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUser(User user) {
        jdbcTemplate.update(
                "INSERT INTO users (name, email, created_at, updated_at,registration_date) VALUES (?, ?, ?, ?, ?)",
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getRegistrationDate()
        );
    }

    @Override
    public List<User> findAllUsers() {
        return jdbcTemplate.query("SELECT * FROM users", userRowMapper());
    }

    @Override
    public User findUserById(Long id){
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                userRowMapper(),
                id
        );
    }

    @Override
    public void updateUser(User user) {
        jdbcTemplate.update(
                "UPDATE users SET name = ?, email = ?, updated_at = ?, registration_date = ? WHERE id = ?",
                user.getName(),
                user.getEmail(),
                user.getUpdatedAt(),
                user.getRegistrationDate(),
                user.getId()
        );
    }

    @Override
    public void deleteUser(Long id) {
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    private RowMapper<User> userRowMapper(){
        return (ResultSet rs, int rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            user.setRegistrationDate(rs.getString("registration_date"));
            return user;
        };
    }
}
