package taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import taskmanagement.model.entity.TaskDetails;

import java.util.List;

@Repository
public interface TaskDetailsRepository extends JpaRepository<TaskDetails, Long> {
    @Query(value = "SELECT * FROM tasks t ORDER BY t.timestamp DESC", nativeQuery = true)
    List<TaskDetails> findAllOrderByTimestampDescending();
    @Query(value = """
        SELECT t.id AS task_id, t.title, t.description, t.status, t.timestamp, t.task_user_id, t.assignee
        FROM tasks t
        JOIN users u ON t.task_user_id = u.id
        WHERE u.email = :author
        ORDER BY t.timestamp DESC
        """, nativeQuery = true)
    List<TaskDetails> findByAuthorOrderByTimestampDescending(@Param("author") String author);
    @Query(value = """
            SELECT * FROM tasks t WHERE t.assignee = :assignee ORDER BY t.timestamp DESC
            """, nativeQuery = true)
    List<TaskDetails> findByAssigneeOrderByTimestampDescending(@Param("assignee") String assignee);
    @Query(value = """
            SELECT t.id AS task_id, t.title, t.description, t.status, t.timestamp, t.task_user_id, t.assignee
            FROM tasks t
            JOIN users u ON t.task_user_id = u.id
            WHERE u.email = :author AND t.assignee = :assignee
            ORDER BY t.timestamp DESC
            """, nativeQuery = true)
    List<TaskDetails> findByAuthorAndAssigneeOrderByTimestampDescending(@Param("author") String author,
                                                                        @Param("assignee") String assignee);
}
