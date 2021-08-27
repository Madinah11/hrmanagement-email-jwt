package uz.pdp.hrmanagementemailjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.hrmanagementemailjwt.entity.Role;
import uz.pdp.hrmanagementemailjwt.entity.User;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(path = "user")
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(@Email String email);

    Optional<User> findByEmail(@Email String email);

    Optional<User> findByEmailAndEmailCode(@Email String email, String emailCode);

    List<User> findAllByRoleIn(Collection<Role> role);

}