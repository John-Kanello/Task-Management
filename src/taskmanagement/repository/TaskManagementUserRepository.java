package taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taskmanagement.model.entity.TaskManagementUser;

import java.util.Optional;

@Repository
public interface TaskManagementUserRepository extends JpaRepository<TaskManagementUser, Long> {
    Optional<TaskManagementUser> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
