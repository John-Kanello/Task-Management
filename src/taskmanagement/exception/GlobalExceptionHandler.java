package taskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorMessage> handleInvalidInput(
            MethodArgumentNotValidException e, WebRequest request
    ) {
        return ResponseEntity.badRequest()
                .body(new CustomErrorMessage(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getLocalizedMessage(),
                        request.getDescription(false),
                        LocalDateTime.now()
                ));
    }
}
