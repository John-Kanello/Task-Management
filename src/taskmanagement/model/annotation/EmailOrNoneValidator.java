package taskmanagement.model.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailOrNoneValidator implements ConstraintValidator<EmailOrNone, String> {
    private static final String EMAIL_REGEX = ".+@.+[.].+";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) {
            return false;
        }
        return value.equals("none") || Pattern.matches(EMAIL_REGEX, value);
    }
}
