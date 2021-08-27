package uz.pdp.hrmanagementemailjwt.payload;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Data
public class RegisterDto {
    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;

    @NotNull
    @Size(min = 3, max = 50)
    private String firstName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private Integer roleId;
}
