package sparta.Schedule.Management.service.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sparta.Schedule.Management.domain.User;
import sparta.Schedule.Management.dto.requestDto.UserRequestDto;
import sparta.Schedule.Management.dto.responseDto.UserResponseDto;
import sparta.Schedule.Management.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRegistrationDate(LocalDateTime.now().toString());

        userRepository.addUser(user);
        return new UserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAllUsers();
        return users.stream().map(UserResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto updateUser(Long id, String name, String email) {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findUserById(id));
        if (existingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        User user = existingUser.get();
        user.setName(name);
        user.setEmail(email);
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.updateUser(user);
        return new UserResponseDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findUserById(id));
        if (existingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteUser(id);
    }

    @Override
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return new UserResponseDto(user);
    }
}

