package uz.pdp.hrmanagementemailjwt.payload;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
