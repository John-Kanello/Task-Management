package taskmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import taskmanagement.model.Role;

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

    public TaskManagementUser(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
