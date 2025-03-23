package taskmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import taskmanagement.model.Role;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "users")
public class TaskManagementUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String email;
    private String password;
    private Role role;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskDetails> tasks;

    public TaskManagementUser(String email, String password, Role role, List<TaskDetails> tasks) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.tasks = tasks;
    }
}
