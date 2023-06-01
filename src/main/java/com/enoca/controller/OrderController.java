package com.enoca.controller;

import com.enoca.domain.Order;
import com.enoca.payload.request.OrderRequest;
import com.enoca.payload.response.OrderResponse;
import com.enoca.payload.response.ResponseMessage;
import com.enoca.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;


    @PostMapping("/save")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseMessage<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest){
        return orderService.saveOrder(orderRequest);
    }


    @GetMapping("/{id}")
    public ResponseMessage<OrderResponse> getOrderById(@PathVariable("id") Long id){
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById (@PathVariable("id") Long id){

        return orderService.deleteOrderById(id);
    }

    @PutMapping("/{id}")
    public ResponseMessage<OrderResponse> updateOrder (@PathVariable("id") Long id,
                                                       @Valid @RequestBody OrderRequest orderRequest){

        return orderService.updateOrder(id, orderRequest);
    }

    @GetMapping("/getAll")
    public Page<OrderResponse> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         @RequestParam(value = "sort", defaultValue = "id") String sort,
                                         @RequestParam(value = "type", defaultValue = "desc") String type){

        return orderService.getAll(page, size, sort, type);
    }


    @GetMapping("/date/{date}")
    public ResponseEntity<List<Order>> getDateAfter(@PathVariable("date")
                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime date){

          return orderService.getDateAfter(date);
    }



}
