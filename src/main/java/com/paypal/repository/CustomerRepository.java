package com.paypal.repository;

import com.paypal.model.Customers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// This class is for customizing our own tables with user values.
@Repository
public interface CustomerRepository extends CrudRepository<Customers, Integer> {
    List<Customers> findByEmail(String email);
}
