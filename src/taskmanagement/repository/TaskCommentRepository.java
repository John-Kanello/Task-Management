package taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import taskmanagement.model.entity.TaskComment;

import java.util.List;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    @Query(value = """
            SELECT c.id AS comment_id, c.text, c.author, c.timestamp, c.task_id
            FROM comments c
            JOIN tasks t ON c.task_id = t.id
            WHERE c.task_id = :taskId
            ORDER BY c.timestamp DESC
            """, nativeQuery = true)
    List<TaskComment> findByTaskIdOrderByTimestampDescending(@Param(value = "taskId") long taskId);
}
