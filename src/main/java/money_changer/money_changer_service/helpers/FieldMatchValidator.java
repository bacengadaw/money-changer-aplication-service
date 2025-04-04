package money_changer.money_changer_service.helpers;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch constraintAnnotation)
    {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            // Using reflection to access the fields dynamically
            final Field firstField = value.getClass().getDeclaredField(firstFieldName);
            final Field secondField = value.getClass().getDeclaredField(secondFieldName);

            firstField.setAccessible(true);
            secondField.setAccessible(true);

            // Retrieve the values of the fields
            final Object firstObj = firstField.get(value);
            final Object secondObj = secondField.get(value);

            // Check if the fields are equal or both null
            return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } catch (final NoSuchFieldException | IllegalAccessException e) {
            // Log the error or handle accordingly
            e.printStackTrace();
        }
        return false;
    }
}
