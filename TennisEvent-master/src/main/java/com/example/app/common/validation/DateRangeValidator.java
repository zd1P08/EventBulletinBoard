package com.example.app.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Doc https://terasolunaorg.github.io/guideline/current/ja/ArchitectureInDetail/WebApplicationDetail/Validation.html
 */
public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

  private String startFieldName;
  private String endFieldName;
  private String message;

  @Override
  public void initialize(DateRange constraintAnnotation) {
    startFieldName = constraintAnnotation.start();
    endFieldName = constraintAnnotation.end();
    message = constraintAnnotation.message();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    boolean isValid;
    try {
      Date start = (Date) getProperty(value, startFieldName);
      Date end = (Date) getProperty(value, endFieldName);

      if (start == null || end == null) {
        // フィールドがnullの場合は検証をスキップ
        return true;
      }

      isValid = start.compareTo(end) <= 0;
    } catch (Exception e) {
      isValid = false;
    }
    if (isValid) {
      return true;
    } else {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message)
          .addPropertyNode(endFieldName).addConstraintViolation();
      return false;
    }
  }

  private Object getProperty(Object obj, String fieldName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    Method method = obj.getClass().getMethod(methodName);
    return method.invoke(obj);
  }
}
