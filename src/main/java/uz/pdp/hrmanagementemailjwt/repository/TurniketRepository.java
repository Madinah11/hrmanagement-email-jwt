package uz.pdp.hrmanagementemailjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.hrmanagementemailjwt.entity.Turniket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(path = "turniket")
public interface TurniketRepository extends JpaRepository<Turniket, Integer> {
    Optional<Turniket> findByCreatedByAndStatus(UUID createdBy, boolean status);

    @Query("select tur from Turniket tur " +
            "where tur.createdBy = :employeeId and (tur.enterDateTime >= :start or tur.enterDateTime <= :finish)")
    List<Turniket> findAllByCreatedByAndEnterDateTimeAndExitDateTimeBefore(UUID employeeId, LocalDate start, LocalDate finish);
}
