package taskmanagement.model.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailOrNoneValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailOrNone {
    String message() default "Must be a valid email or 'none'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
