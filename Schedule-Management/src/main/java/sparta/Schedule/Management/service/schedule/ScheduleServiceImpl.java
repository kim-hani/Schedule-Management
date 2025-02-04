package sparta.Schedule.Management.service.schedule;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sparta.Schedule.Management.domain.Schedule;
import sparta.Schedule.Management.dto.requestDto.ScheduleRequestDto;
import sparta.Schedule.Management.dto.responseDto.ScheduleResponseDto;
import sparta.Schedule.Management.repository.schedule.ScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto){

        if (requestDto.getUserName() == null || requestDto.getUserName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User name is required");
        }

        Schedule schedule = new Schedule(
                requestDto.getTodo(),
                requestDto.getUserName(),
                requestDto.getAuthorId(),
                requestDto.getPassword(),
                java.time.LocalDateTime.now(),
                java.time.LocalDateTime.now()
        );
        return scheduleRepository.saveSchedule(schedule);
    }


    @Override
    public List<ScheduleResponseDto> findAllSchedules(){
        return scheduleRepository.findAllSchedules();
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id){
        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);

        if(optionalSchedule.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Schedule does not exist : " + id);
        }

        return new ScheduleResponseDto(optionalSchedule.get());
    }


   @Override
   public ScheduleResponseDto updateTodo(Long id, String todo, String userName, String password){

        if(todo==null || password == null || password.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Todo and password are required " +id);
        }

        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);

        if(optionalSchedule.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Schedule does not exist : " + id);
        }

        Schedule schedule = optionalSchedule.get();

        if(!schedule.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid password " + id);
        }

       scheduleRepository.updateSchedule(id, todo, userName, java.time.LocalDateTime.now());

       return new ScheduleResponseDto(scheduleRepository.findScheduleById(id).get());
   }

    @Override
    public void deleteSchedule(Long id, String password) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);

        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule does not exist: " + id);
        }

        Schedule schedule = optionalSchedule.get();

        if(!schedule.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid password " + id);
        }

        scheduleRepository.deleteSchedule(id);
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByUserId(Long userId) {
        List<Schedule> schedules = scheduleRepository.findSchedulesByAuthorId(userId);
        if (schedules.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No schedules found for user ID: " + userId);
        }
        return schedules.stream().map(ScheduleResponseDto::new).toList();
    }

}
