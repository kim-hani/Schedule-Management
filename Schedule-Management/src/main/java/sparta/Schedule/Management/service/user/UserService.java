package sparta.Schedule.Management.service.user;

import sparta.Schedule.Management.dto.requestDto.UserRequestDto;
import sparta.Schedule.Management.dto.responseDto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto saveUser(UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto updateUser(Long id, String name, String email);

    void deleteUser(Long id);

    UserResponseDto findUserById(Long id);
}
