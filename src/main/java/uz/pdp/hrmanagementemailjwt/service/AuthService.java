package uz.pdp.hrmanagementemailjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagementemailjwt.entity.Role;
import uz.pdp.hrmanagementemailjwt.entity.User;
import uz.pdp.hrmanagementemailjwt.payload.ApiResponse;
import uz.pdp.hrmanagementemailjwt.payload.LoginDto;
import uz.pdp.hrmanagementemailjwt.payload.RegisterDto;
import uz.pdp.hrmanagementemailjwt.repository.RoleRepository;
import uz.pdp.hrmanagementemailjwt.repository.UserRepository;
import uz.pdp.hrmanagementemailjwt.security.JwtProvider;

import java.util.*;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    public ApiResponse registerUser(RegisterDto registerDto) {
        User newUser = new User();    // YANGI QO'SHILAYOTGAN XODIM
        boolean email = userRepository.existsByEmail(registerDto.getEmail());
        if (email)
            return new ApiResponse("Bunday emailli xodim mavjud", false);
        newUser.setFirstName(registerDto.getFirstName());
        newUser.setLastName(registerDto.getLastName());
        newUser.setEmail(registerDto.getEmail());
        newUser.setEmailCode(UUID.randomUUID().toString());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Optional<Role> optionalRole = roleRepository.findById(registerDto.getRoleId());
        if (!optionalRole.isPresent())
            return new ApiResponse("Bunday lavozim topilmadi", false);
        Role role = optionalRole.get();
        newUser.setRole(role);

        //SISTEMADA QAYSI USER EKANLIGINI ANIQLIMIZ
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User systemUser = (User) authentication.getPrincipal();
        // SISTEMADAGI USER LAVOZIMINI ANIQLIMIZ
        //AGAR DIRECTOR BOLSA MANAJERNI SAQLIDI
        Role systemUserRole = systemUser.getRole();
        if (systemUserRole.getRoleName().name().equals("DIRECTOR")) {
            if (newUser.getRole().getRoleName().name().equals("MANAGER")) {
                userRepository.save(newUser);
                // MANAGER EMAILIGA XABAR YUBORILADI
                sendEmail(newUser.getEmail(),newUser.getEmailCode());
                return new ApiResponse("Manager royhatga qo'shildi", true);
            }
            return new ApiResponse("Direktor faqat managerni qosha oladi", false);
        }
        //AGAR LAVOZIMI MANAGER BOLSA XODIMNI SAQLIDI
        if (systemUserRole.getRoleName().name().equals("HR_MANAGER")) {
            if (newUser.getRole().getRoleName().name().equals(" EMPLOYEE")) {
                userRepository.save(newUser);
                // XODIM EMAILIGA XABAR YUBORILADI
                sendEmail(newUser.getEmail(),newUser.getEmailCode());
                return new ApiResponse("Xodim qo'shildi", true);
            }
            return new ApiResponse("HR_Manager faqat xodimlarni qosha oladi", false);
        }
        return new ApiResponse("Siz xodimlarni qosha olmaysiz", false);
    }

    public boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("shahzadenishonova@gmail.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Accauntni tasdiqlash");
            mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>Tasdiqlang</a>");
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ApiResponse verifyEmail(String emailCode, String email) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse("Accaunt tasdiqlandi", true);
        }
        return new ApiResponse("Accaunt allaqachon tasdiqlangan", false);

    }

    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            User user = (User) authentication.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRole());
            return new ApiResponse("Token ", true, token);
        } catch (BadCredentialsException e) {
            return new ApiResponse("Parol yoki login xato", false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + "topilmadi"));
    }
}







