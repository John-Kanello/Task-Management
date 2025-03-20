package taskmanagement.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import taskmanagement.model.entity.TaskManagementUser;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TaskDetailsRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private TaskManagementUser author;
}
