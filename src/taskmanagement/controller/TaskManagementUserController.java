package taskmanagement.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import taskmanagement.model.dto.request.RegistrationRequest;
import taskmanagement.model.dto.response.JwtTokenDto;
import taskmanagement.model.entity.TaskManagementUser;
import taskmanagement.service.TaskManagementUserService;
import taskmanagement.util.AuthenticationUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskManagementUserController {
    private final TaskManagementUserService taskManagementUserService;
    private final JwtEncoder jwtEncoder;

    public TaskManagementUserController(TaskManagementUserService taskManagementUserService, JwtEncoder jwtEncoder) {
        this.taskManagementUserService = taskManagementUserService;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/auth/token")
    public ResponseEntity<JwtTokenDto> token(Authentication authentication) {
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(8, ChronoUnit.HOURS))
                .claim("scope", authorities)
                .build();
        return ResponseEntity.ok()
                .body(new JwtTokenDto(
                        jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue())
                );
    }

    @PostMapping("/accounts")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        if(taskManagementUserService.existsByEmail(registrationRequest.email())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        taskManagementUserService.save(registrationRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteById(@PathVariable("userId") long userId,
                                             Authentication authentication) {
        if(!taskManagementUserService.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        String userEmail = authentication.getName();
        TaskManagementUser currentUser = taskManagementUserService.findByEmail(userEmail).orElseThrow();
        boolean hasAdminRole = AuthenticationUtils.hasAdminRole(authentication);
        if(!hasAdminRole && currentUser.getId() != userId) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        taskManagementUserService.deleteById(userId);
        return ResponseEntity.ok().build();
    }
}
