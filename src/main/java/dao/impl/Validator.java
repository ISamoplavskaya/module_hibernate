package dao.impl;

import exeption.ValidationException;
import jakarta.persistence.Column;
import java.lang.reflect.Field;
import java.math.BigDecimal;

public class Validator {
    public static <T> boolean validateEntity(T entity) {
        boolean isValid = true;
        if (entity == null) {
            System.out.println("Entity is null.");
            return false;
        }

        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (!columnAnnotation.nullable()) {
                    try {
                        if (field.get(entity) == null) {
                            System.out.println(field.getName() + " is null, but shouldn`t be nullable.");
                            isValid = false;
                        }
                    } catch (IllegalAccessException e) {
                        throw new ValidationException("Error accessing field", e);
                    }
                }
            }

            if (field.getType().equals(BigDecimal.class)) {

                try {
                    BigDecimal value = (BigDecimal) field.get(entity);
                    if (value != null) {
                        if (value.compareTo(BigDecimal.ZERO) < 0) {
                            System.out.println(field.getName() + " should be a positive value.");
                            isValid = false;
                        }
                    } else isValid = false;

                } catch (IllegalAccessException e) {
                    throw new ValidationException("Error accessing field", e);
                }
            }
        }
        if (!isValid) {
            throw new ValidationException("Entity validation failed");
        }
        return isValid;
    }
}