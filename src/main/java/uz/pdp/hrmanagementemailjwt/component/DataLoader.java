package uz.pdp.hrmanagementemailjwt.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.hrmanagementemailjwt.entity.User;
import uz.pdp.hrmanagementemailjwt.entity.enums.RoleName;
import uz.pdp.hrmanagementemailjwt.repository.RoleRepository;
import uz.pdp.hrmanagementemailjwt.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Value(value = "${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")){
            User director=new User("Akbar","Shavkatov",
                    "akbar@mail.ru",passwordEncoder.encode("direktor"),
                    roleRepository.findByRoleName(RoleName.DIRECTOR));
            userRepository.save(director);
        }
    }
}
