package com.enoca.service;

import com.enoca.domain.Customer;
import com.enoca.domain.Order;
import com.enoca.exception.ResourceNotFoundException;
import com.enoca.payload.request.OrderRequest;
import com.enoca.payload.response.OrderResponse;
import com.enoca.payload.response.ResponseMessage;
import com.enoca.repository.OrderRepository;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;


    public ResponseMessage<OrderResponse> saveOrder(OrderRequest orderRequest) {

           Order order = crateOrder(orderRequest);
           Order savedOrder = orderRepository.save(order);

        return ResponseMessage.<OrderResponse>builder()
                .message(String.format(RMessage.CREATED_OBJECT,savedOrder.getId()))
                .object(createOrderResponse(savedOrder))
                .build();
    }


    public Order getById(Long id){
       Order order = orderRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));;
       return order;
    }



    public ResponseMessage<OrderResponse> getOrderById(Long id) {

        Order order = getById(id);

        return ResponseMessage.<OrderResponse>builder()
                .message(String.format(RMessage.REQUEST_DATA_OBJECT,order.getId()))
                .object(createOrderResponse(order))
                .build();

    }

    public ResponseEntity<String> deleteOrderById(Long id) {

        orderRepository.delete(getById(id));
        String str = String.format(RMessage.DELETED_OBJECT, id);
        return ResponseEntity.status(HttpStatus.OK).body(str);
    }

    public ResponseMessage<OrderResponse> updateOrder(Long id, OrderRequest orderRequest) {

        Order order = getById(id);
        Customer customer = customerService.getById(orderRequest.getCustomerId());

        order.setCustomer(customer);
        order.setTotalprice(orderRequest.getTotalprice());
        order.setDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        return ResponseMessage.<OrderResponse>builder()
                .message(String.format(RMessage.UPDATED_OBJECT,savedOrder.getId()))
                .object(createOrderResponse(savedOrder))
                .build();
    }



    public Page<OrderResponse> getAll(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if(Objects.equals(type, "desc")){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return orderRepository.findAll(pageable).map(this::createOrderResponse);
    }


    public ResponseEntity<List<Order>> getDateAfter(LocalDateTime date) {
        List<Order> orders = orderRepository.findByDateAfter(date);
        return ResponseEntity.ok(orders);
    }



    private Order crateOrder (OrderRequest orderRequest){

        customerService.getById(orderRequest.getCustomerId());

        return Order.builder()
                .totalprice(orderRequest.getTotalprice())
                .customer(customerService.getById(orderRequest.getCustomerId()))
                .date(LocalDateTime.now())
                .build();
    }


    private OrderResponse createOrderResponse(Order order){

        return OrderResponse.builder()
                .totalprice(order.getTotalprice())
                .localDate(order.getDate())
                .customerId(order.getCustomer().getId())
                .build();
    }



}
