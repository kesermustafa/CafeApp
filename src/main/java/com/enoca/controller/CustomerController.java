package com.enoca.controller;

import com.enoca.domain.Customer;
import com.enoca.payload.request.CustomerRequest;
import com.enoca.payload.response.CustomerResponse;
import com.enoca.payload.response.ResponseMessage;
import com.enoca.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping("/save")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseMessage<CustomerResponse> save(@Valid @RequestBody CustomerRequest customerRequest){

        return customerService.save(customerRequest);
    }

    @GetMapping("/{id}")
    public ResponseMessage<CustomerResponse> getWithById (@PathVariable("id") Long id){

        return customerService.getWithById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById (@PathVariable("id") Long id){

        return customerService.deleteCustomerById(id);
    }


    @PutMapping("/{id}")
    public ResponseMessage<CustomerResponse> updateCustomer (@PathVariable("id") Long id,
                                                             @Valid @RequestBody CustomerRequest customerRequest){

        return customerService.updateCustomer(id, customerRequest);
    }


    @GetMapping("/getAll")
    public Page<CustomerResponse> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         @RequestParam(value = "sort", defaultValue = "id") String sort,
                                         @RequestParam(value = "type", defaultValue = "desc") String type){

        return customerService.getAll(page, size, sort, type);
    }


    @GetMapping("/like")
    public ResponseEntity<List<Customer>> getAllWithLike(@RequestParam("name")  String name){

        List<Customer> customers = customerService.getAllWithLike(name);
        return ResponseEntity.ok(customers);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomer(){
        List<Customer> customers = customerService.getAllCustomer();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/empty")
    public ResponseEntity<List<Customer>> getCustomerWithEmptyOrder () {
        return customerService.getCustomerWithEmptyOrder();
    }


}
