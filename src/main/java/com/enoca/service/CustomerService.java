package com.enoca.service;

import com.enoca.domain.Customer;
import com.enoca.exception.ResourceNotFoundException;
import com.enoca.payload.request.CustomerRequest;
import com.enoca.payload.response.CustomerResponse;
import com.enoca.payload.response.ResponseMessage;
import com.enoca.repository.CustomerRepository;
import com.enoca.utils.message.ErrorMessage;
import com.enoca.utils.message.RMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;


    public ResponseMessage<CustomerResponse> save(CustomerRequest customerRequest) {

        Customer customer = crateCustomer(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);

        return ResponseMessage.<CustomerResponse>builder()
                .message(String.format(RMessage.CREATED_OBJECT,savedCustomer.getId()))
                .object(createCutomerResponse(savedCustomer))
                .build();
    }

    public Customer getById(Long id){
        return customerRepository.findById(id).orElseThrow(()->
            new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));
    }

    public ResponseMessage<CustomerResponse> getWithById(Long id) {

        Customer customer = getById(id);

        return ResponseMessage.<CustomerResponse>builder()
                .message(String.format(RMessage.REQUEST_DATA_OBJECT,customer.getId()))
                .object(createCutomerResponse(customer))
                .build();
    }

    public ResponseEntity<String> deleteCustomerById(Long id) {

        customerRepository.delete(getById(id));
        String str = String.format(RMessage.DELETED_OBJECT, id);
        return ResponseEntity.status(HttpStatus.OK).body(str);
    }


    public ResponseMessage<CustomerResponse> updateCustomer(Long id, CustomerRequest customerRequest) {

        Customer customer = getById(id);
        customer.setName(customerRequest.getName());
        customer.setAge(customerRequest.getAge());

        Customer savedCustomer = customerRepository.save(customer);

        return ResponseMessage.<CustomerResponse>builder()
                .message(String.format(RMessage.UPDATED_OBJECT,savedCustomer.getId()))
                .object(createCutomerResponse(savedCustomer))
                .build();
    }

    public Page<CustomerResponse> getAll(int page, int size, String sort, String type ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if(Objects.equals(type, "desc")){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return customerRepository.findAll(pageable).map(this::createCutomerResponse);
    }

    public List<Customer> getAllWithLike(String name) {

        String lowerName = name.toLowerCase();
        return customerRepository.findAllByNameLike(lowerName);

    }



    private Customer crateCustomer (CustomerRequest customerRequest){
        return Customer.builder()
                .name(customerRequest.getName())
                .age(customerRequest.getAge()).build();
    }


    private CustomerResponse createCutomerResponse(Customer customer){

        return CustomerResponse.builder()
                .name(customer.getName())
                .age(customer.getAge())
                .build();
    }


    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }


    public ResponseEntity<List<Customer>> getCustomerWithEmptyOrder() {

        List<Customer> customers =customerRepository.findCustomerByOrders_Empty();
        return ResponseEntity.ok(customers);
    }


}
