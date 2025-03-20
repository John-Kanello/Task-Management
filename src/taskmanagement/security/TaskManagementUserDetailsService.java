package taskmanagement.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import taskmanagement.repository.TaskManagementUserRepository;

@Service
public class TaskManagementUserDetailsService implements UserDetailsService {
    private final TaskManagementUserRepository taskManagementUserRepository;

    public TaskManagementUserDetailsService(TaskManagementUserRepository taskManagementUserRepository) {
        this.taskManagementUserRepository = taskManagementUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return taskManagementUserRepository.findByEmailIgnoreCase(username)
                .map(TaskManagementUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Task management user with email :" + username + " not found..."));
    }
}
