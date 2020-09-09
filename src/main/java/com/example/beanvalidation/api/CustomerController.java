package com.example.beanvalidation.api;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("customers")
class CustomerController {

    private final Validator validator;

    public CustomerController(Validator validator) {
        this.validator = validator;
    }

    @PostMapping("sample1")
    ResponseEntity<?> sample1(@RequestBody @Valid CustomerRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            var errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(toList());
            return ResponseEntity.badRequest().body(errors);
        }

        return status(HttpStatus.CREATED).build();
    }

    @PostMapping("sample2")
    ResponseEntity<?> sample2(@RequestBody CustomerRequest request) {

        var constraints = validator.validate(request);

        if (!constraints.isEmpty()) {
            var errors = constraints.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(toList());

            return ResponseEntity.badRequest().body(errors);
        }

        return status(HttpStatus.CREATED).build();
    }

    @PostMapping("sample3")
    ResponseEntity<?> sample3(@RequestBody CustomerRequest2 request) {

        List<Class> rules = new ArrayList<>();
        if (request.getIncomeType() == CustomerRequest2.IncomeType.OWNER_PARTNER) {
            rules.add(CnpjByIncomeValidator.class);
        }
        var constraints = validator.validate(request, rules.toArray(new Class[0]));

        if (!constraints.isEmpty()) {
            var errors = constraints.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(toList());

            return ResponseEntity.badRequest().body(errors);
        }

        return status(HttpStatus.CREATED).build();
    }

}
