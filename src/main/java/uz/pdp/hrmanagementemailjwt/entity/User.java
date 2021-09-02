package uz.pdp.hrmanagementemailjwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String firstName;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt; //qacon ro'yhatdan o'tganligi

    @UpdateTimestamp
    private Timestamp updateAt;


    @ManyToOne // KO'PLAB XODIMLARGA BITTA LAVOZIM TOGRI KELADI  (WUNDA BITTA XODIM BITTA LAVOZIMDA IWLIDI)
    private Role role;

    private String emailCode;
    private boolean accountNonExpired = true;     //muddati o'tmaganligi
    private boolean credentialsNonExpired = true; //ishonclilik muddati o'tmaganligi
    private boolean accountNonLocked = true;      // acc bloklanmaganligi
    private boolean enabled;                    //yoniqligi


    //------------UserDetails metodlari-----------------------


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(this.role);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


//    //DataLoader uchun
    public User(String lastName, String firstName, String email, String password, Role role) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
