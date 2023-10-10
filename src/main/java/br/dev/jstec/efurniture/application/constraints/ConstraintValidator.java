package br.dev.jstec.efurniture.application.constraints;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ConstraintValidator {

    public static <T> void validarConstraints(T objectToValidate) {

        throwException(buildDefaultValidatorFactory().getValidator().validate(objectToValidate));
    }

    private static <T> void throwException(Set<ConstraintViolation<T>> errors) {

        if (!errors.isEmpty()) {
            throw new ConstraintException(new ConstraintViolationException(errors).getMessage());
        }
    }
}