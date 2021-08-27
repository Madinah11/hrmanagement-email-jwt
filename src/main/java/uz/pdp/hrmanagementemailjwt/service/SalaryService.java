package uz.pdp.hrmanagementemailjwt.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagementemailjwt.entity.Salary;
import uz.pdp.hrmanagementemailjwt.entity.User;
import uz.pdp.hrmanagementemailjwt.payload.ApiResponse;
import uz.pdp.hrmanagementemailjwt.payload.SalaryDto;
import uz.pdp.hrmanagementemailjwt.repository.SalaryRepository;
import uz.pdp.hrmanagementemailjwt.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SalaryService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SalaryRepository salaryRepository;

    public ApiResponse paySalary(SalaryDto salaryDto){
        Optional<User> optionalUser = userRepository.findById(salaryDto.getEmployee());
        if (!optionalUser.isPresent())
            return new ApiResponse("Xodim topilmadi",false);
        User employee = optionalUser.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user.getRole().getRoleName().name().equals("DIRECTOR") || user.getRole().getRoleName().name().equals("HR_MANAGER")){
            Salary salary=new Salary();
            salary.setEmployee(employee);
            salary.setAmountSalary(salaryDto.getAmountSalary());
            salary.setMonth(salaryDto.getMonth());
            salaryRepository.save(salary);
            return new ApiResponse("Oylik saqlandi "+employee.getLastName()+" "+ employee.getFirstName()+" uchun",true);
        }
        return new ApiResponse("Sizda oylik saqlashga huquq yoq",true);
    }

    public ApiResponse getSalarybyEmployee(UUID id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("Xodim topilmadi",false);
        User user = optionalUser.get();
        List<Salary> salaryList = salaryRepository.findAllByEmployee(user);
        return new ApiResponse("Oylik royhati bitta xodim boyicha: "+salaryList,true);
    }
}
