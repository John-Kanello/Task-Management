package taskmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "comments")
public class TaskComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    private String author;
    private LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private TaskDetails task;

    public TaskComment(String text, String author, LocalDateTime timestamp, TaskDetails task) {
        this.text = text;
        this.author = author;
        this.timestamp = timestamp;
        this.task = task;
    }
}
