package uz.pdp.hrmanagementemailjwt.entity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Turniket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean status;               // true-ishxonaga kirdi; false-ishdan chiqdi

    @CreatedBy
    private UUID createdBy;               // ishga kiruvchi user

    @NotNull
    private LocalDateTime enterDateTime;   // ishga kirgan vaqti

    @NotNull
    private LocalDateTime exitDateTime;    // ishdan chiqqan vaqti
}
