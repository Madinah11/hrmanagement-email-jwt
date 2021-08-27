package uz.pdp.hrmanagementemailjwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.hrmanagementemailjwt.entity.enums.TaskStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String body;

    @NotNull
    private Date deadLine;         //vazifani yakunlanadigan vaqti

    @ManyToOne
    private User responsibleUser;  // vazifa uchun ma'sul xodim


    private TaskStatus taskStatus; // qaysi holatdaligi(yengi,jarayonda,tugatilgan)


    @CreatedBy
    private UUID createdBy;        //vazifa qo'shuvchi

}
