package uz.pdp.hrmanagementemailjwt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagementemailjwt.payload.ApiResponse;
import uz.pdp.hrmanagementemailjwt.payload.TaskDto;
import uz.pdp.hrmanagementemailjwt.service.TaskServica;

@RestController
@RequestMapping("/api/auth")
public class TaskController {
    @Autowired
    TaskServica taskServica;

    @PostMapping("/createTask")
    public HttpEntity<?> createNewTask(@RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskServica.createTask(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:401).body(apiResponse);
    }

    @GetMapping("/getTask")
    public HttpEntity<?>getNewTask(@RequestParam String email,@RequestParam Integer taskId){
        ApiResponse apiResponse = taskServica.getTask(taskId, email);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/completeTask/{id}")
    public HttpEntity<?> completeTask(@PathVariable Integer id){
        ApiResponse apiResponse = taskServica.completeTask(id);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.ACCEPTED : HttpStatus.UNAUTHORIZED).body(apiResponse);
    }
}
