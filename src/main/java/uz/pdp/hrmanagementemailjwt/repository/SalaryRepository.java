package uz.pdp.hrmanagementemailjwt.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.hrmanagementemailjwt.entity.Salary;
import uz.pdp.hrmanagementemailjwt.entity.User;


import java.util.List;
@RepositoryRestResource(path = "salary")
public interface SalaryRepository extends JpaRepository<Salary, Integer> {
    List<Salary> findAllByEmployee(User employee);

}
