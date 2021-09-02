package uz.pdp.hrmanagementemailjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.hrmanagementemailjwt.entity.Months;

@RepositoryRestResource(path = "month")
public interface MonthRepository extends JpaRepository<Months, Integer> {
}
