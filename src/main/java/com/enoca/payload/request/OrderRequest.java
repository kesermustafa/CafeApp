package com.enoca.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder (toBuilder = true)
public class OrderRequest {

    @NotNull(message = "Please enter total price")
    @DecimalMin(value = "1", message = "Total price '${validatedValue}' must be min {value}")
    @Positive(message = "Please enter positive number")
    private Double totalprice;

    @NotNull(message = "Please enter customer id")
    @DecimalMin(value = "1", message = "Total price '${validatedValue}' must be min {value}")
    @Positive(message = "Please enter positive number")
    private Long customerId;


}
