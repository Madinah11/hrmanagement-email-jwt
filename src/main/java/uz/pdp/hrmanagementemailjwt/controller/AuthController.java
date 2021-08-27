package uz.pdp.hrmanagementemailjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagementemailjwt.payload.ApiResponse;
import uz.pdp.hrmanagementemailjwt.payload.LoginDto;
import uz.pdp.hrmanagementemailjwt.payload.RegisterDto;
import uz.pdp.hrmanagementemailjwt.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/saveDirector")
    public HttpEntity<?> registerDirector(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.saveDirector(registerDto);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerUser(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse = authService.login(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:401).body(apiResponse);
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?>verifyEmail(@RequestParam String emailCode,@RequestParam String email){
        ApiResponse apiResponse = authService.verifyEmail(emailCode, email);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
