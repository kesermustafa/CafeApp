package com.enoca.payload.response;

import com.enoca.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.OptionalLong;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomersWithLastOrder {


    private String name;

    private Integer age;
    
    private Long lastOrderId;


    public CustomersWithLastOrder(Customer customer, OptionalLong max) {
    }
}
