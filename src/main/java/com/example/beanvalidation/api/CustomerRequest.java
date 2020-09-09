package com.example.beanvalidation.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@CnpjByIncomeType
public class CustomerRequest {

    @Email(message = "The email '${validatedValue}' is invalid. Must be a well-formed email address")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "The name '${validatedValue}' is invalid. Must not be blank")
    @JsonProperty("name")
    private String name;

    @CPF(message = "invalid Brazilian individual taxpayer registry number (CPF): '${validatedValue}'")
    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("cnpj")
    private String cnpj;

    @NotNull(message = "income type must not be null")
    @JsonProperty("income_type")
    private IncomeType incomeType;

    public String getCnpj() {
        return cnpj;
    }

    public IncomeType getIncomeType() {
        return incomeType;
    }

    public enum IncomeType {
        EMPLOYEE,
        OWNER_PARTNER
    }

}
