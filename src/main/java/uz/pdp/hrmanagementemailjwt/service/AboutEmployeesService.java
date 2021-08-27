package uz.pdp.hrmanagementemailjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagementemailjwt.entity.Role;
import uz.pdp.hrmanagementemailjwt.entity.Task;
import uz.pdp.hrmanagementemailjwt.entity.Turniket;
import uz.pdp.hrmanagementemailjwt.entity.User;
import uz.pdp.hrmanagementemailjwt.entity.enums.RoleName;
import uz.pdp.hrmanagementemailjwt.entity.enums.TaskStatus;
import uz.pdp.hrmanagementemailjwt.payload.ApiResponse;
import uz.pdp.hrmanagementemailjwt.payload.EmplResponse;
import uz.pdp.hrmanagementemailjwt.repository.RoleRepository;
import uz.pdp.hrmanagementemailjwt.repository.TaskRepository;
import uz.pdp.hrmanagementemailjwt.repository.TurniketRepository;
import uz.pdp.hrmanagementemailjwt.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class AboutEmployeesService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    TurniketRepository turniketRepository;
    @Autowired
    TaskRepository taskRepository;

    public ApiResponse findAllEmployees() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user.getRole().getRoleName().name().equals("DIRECTOR") || user.getRole().getRoleName().name().equals("HR_MANAGER")) {
            Role roleEmployee = roleRepository.findByRoleName(RoleName.EMPLOYEE);
            List<User> employeeList = userRepository.findAllByRoleIn(Collections.singleton(roleEmployee));
            return new ApiResponse("Barcha xodimlar ro'yhati: " + employeeList, true);
        }
        return new ApiResponse("Sizda xodimlarni ko'rish uchun xuquq yo'q", false);
    }

    public EmplResponse findByData(UUID id, LocalDate start, LocalDate finish) {
        Optional<User> optionalEmployee = userRepository.findById(id);
        if (!optionalEmployee.isPresent())
            return new EmplResponse("Bunday xodim mavjud emas!", false);
        User user = optionalEmployee.get();
        if (user.getRole().getRoleName().name().equals("EMPLOYEE")) {
            List<Turniket> turniketList = turniketRepository.findAllByCreatedByAndEnterDateTimeAndExitDateTimeBefore(id, start, finish);
            if (turniketList.isEmpty())
                return new EmplResponse("Bunday vaqt oraligidagi natija topilmadi ", false);
            EmplResponse emplResponse = new EmplResponse();
            emplResponse.setTurniketList(turniketList);
            List<Task> taskList = taskRepository.findAllByTaskStatusAndResponsibleUser(TaskStatus.COMPLETED, user);
            emplResponse.setTaskList(taskList);
            emplResponse.setSuccess(true);
            emplResponse.setMessage("Xodimning bajargan vazifalari va turniket malumotlari");
            return emplResponse;
        } else {
            return new EmplResponse("Boshqa xodim haqida ma'lumot olish mumkin emas", false);
        }
    }

}
