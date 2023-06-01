package com.enoca.repository;

import com.enoca.domain.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE lower(c.name) LIKE %:pName%")
    List<Customer> findAllByNameLike(@Param("pName") String name);

    List<Customer> findCustomerByOrders_Empty();


}
