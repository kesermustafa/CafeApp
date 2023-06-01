package com.enoca.repository;

import com.enoca.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByDateBefore(LocalDateTime dateTime);

    List<Order> findByDateAfter(LocalDateTime dateTime);

}
