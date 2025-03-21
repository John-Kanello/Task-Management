package taskmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "tasks")
public class TaskDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "task_user_id", nullable = false)
    private TaskManagementUser author;
    private String assignee;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskComment> comments;

    public TaskDetails(String title,
                       String description,
                       String status,
                       LocalDateTime timestamp,
                       TaskManagementUser author,
                       String assignee) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.timestamp = timestamp;
        this.author = author;
        this.assignee = assignee;
    }
}
