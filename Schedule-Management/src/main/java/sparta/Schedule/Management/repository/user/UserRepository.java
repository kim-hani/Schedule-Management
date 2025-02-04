package sparta.Schedule.Management.repository.user;

import sparta.Schedule.Management.domain.User;

import java.util.List;

public interface UserRepository {
    void addUser(User user);

    List<User> findAllUsers();

    User findUserById(Long id);

    void updateUser(User user);

    void deleteUser(Long id);
}
