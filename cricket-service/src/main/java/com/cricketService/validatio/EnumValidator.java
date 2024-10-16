package com.cricketService.validatio;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Handle null values if needed
        }

        // Check if the provided value is part of the enum constants
        Enum<?>[] enumValues = enumClass.getEnumConstants();
        for (Enum<?> enumValue : enumValues) {
            if (enumValue.name().equals(value.name())) {
                return true;
            }
        }

        return false;
    }
}

