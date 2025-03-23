package taskmanagement.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taskmanagement.model.dto.request.RegistrationRequest;
import taskmanagement.model.entity.TaskManagementUser;
import taskmanagement.repository.TaskManagementUserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TaskManagementUserService {
    private final TaskManagementUserRepository taskManagementUserRepository;
    private final PasswordEncoder passwordEncoder;

    public TaskManagementUserService(TaskManagementUserRepository taskManagementUserRepository, PasswordEncoder passwordEncoder) {
        this.taskManagementUserRepository = taskManagementUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsByEmail(String email) {
        return taskManagementUserRepository.existsByEmailIgnoreCase(email);
    }

    public Optional<TaskManagementUser> findByEmail(String email) {
        return taskManagementUserRepository.findByEmailIgnoreCase(email);
    }

    public TaskManagementUser save(RegistrationRequest registrationRequest) {
        TaskManagementUser taskManagementUser = new TaskManagementUser(
                registrationRequest.email(),
                passwordEncoder.encode(registrationRequest.password()),
                registrationRequest.role(),
                new ArrayList<>()
        );
        return taskManagementUserRepository.save(taskManagementUser);
    }
}
