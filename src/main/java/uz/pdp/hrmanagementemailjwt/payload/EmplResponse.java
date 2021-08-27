package uz.pdp.hrmanagementemailjwt.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.hrmanagementemailjwt.entity.Task;
import uz.pdp.hrmanagementemailjwt.entity.Turniket;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmplResponse {
    private String message;
    private boolean success;
    List<Turniket> turniketList;
    List<Task> taskList;

    public EmplResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
