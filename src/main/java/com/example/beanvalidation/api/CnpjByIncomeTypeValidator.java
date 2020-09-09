package com.example.beanvalidation.api;

import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Validator;

@Component
class CnpjByIncomeTypeValidator implements ConstraintValidator<CnpjByIncomeType, CustomerRequest> {

    private final Validator validator;

    CnpjByIncomeTypeValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void initialize(CnpjByIncomeType constraintAnnotation) {

    }

    @Override
    public boolean isValid(CustomerRequest customerRequest, ConstraintValidatorContext context) {
        if (customerRequest.getIncomeType() == CustomerRequest.IncomeType.OWNER_PARTNER) {
            return isValidCNPJ(customerRequest.getCnpj());
        }
        return true;
    }
    private boolean isValidCNPJ(String cnpj) {

        return validator.validate(new CNPJValidator(cnpj)).isEmpty();
    }

    private static class CNPJValidator {
        CNPJValidator(String value) {
            this.value = value;
        }
        @CNPJ
        private final String value;

    }
}
