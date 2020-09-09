package com.example.beanvalidation.api;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CnpjByIncomeTypeValidator.class)
public @interface CnpjByIncomeType {

    String message() default "invalid Brazilian corporate taxpayer registry number (CNPJ: '${validatedValue.cnpj}')";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
