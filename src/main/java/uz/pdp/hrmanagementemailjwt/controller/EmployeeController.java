package uz.pdp.hrmanagementemailjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.hrmanagementemailjwt.payload.ApiResponse;
import uz.pdp.hrmanagementemailjwt.service.AboutEmployeesService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    AboutEmployeesService aboutEmployeesService;

    @GetMapping
    public HttpEntity<?> findAll(){
        ApiResponse apiResponse = aboutEmployeesService.findAllEmployees();
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }
}
