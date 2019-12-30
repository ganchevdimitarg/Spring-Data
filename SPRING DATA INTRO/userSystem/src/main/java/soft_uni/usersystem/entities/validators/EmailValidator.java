package soft_uni.usersystem.entities.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Email(message="Please provide a valid email address")
@Pattern(regexp="^[_A-Za-z0-9]+(.[_A-Za-z0-9-]+)*[@][A-Za-z0-9-]+(.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Please provide a valid email address")
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface EmailValidator {
    String message() default "Please provide a valid email address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}