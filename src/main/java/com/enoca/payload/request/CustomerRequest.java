package com.enoca.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder (toBuilder = true)
public class CustomerRequest {

    @NotNull(message = "Please enter your name")
    @Size(min = 2, max = 16, message = "Your name should be at least 2 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your name must cosist of the characters.")
    private String name;


    @DecimalMax(value = "100", message = "Age '${validatedValue}' must be max {value} ")
    @DecimalMin(value = "10", message = "Age '${validatedValue}' must be min {value}")
    @NotNull(message = "Please enter age")
    private Integer age;

}
