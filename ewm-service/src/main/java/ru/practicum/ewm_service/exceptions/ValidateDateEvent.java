package ru.practicum.ewm_service.exceptions;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidateDateEvent.DateEventValidator.class)
public @interface ValidateDateEvent {

    String message() default "{message.key}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class DateEventValidator implements ConstraintValidator<ValidateDateEvent, Timestamp> {
        private final Timestamp startTime = Timestamp.valueOf(LocalDateTime.now().plusHours(2));

        @Override
        public boolean isValid(Timestamp timestamp, ConstraintValidatorContext context) {
            return timestamp.after(startTime);
        }
    }


}
