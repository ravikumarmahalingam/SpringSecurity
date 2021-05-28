package com.paypal.service;

import com.paypal.model.Customers;
import com.paypal.model.SecurityCustomer;
import com.paypal.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// This class is for customizing our own tables with user values.
@Service
public class SecurityCustomerUserDetails implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<Customers> customerList = new ArrayList<>();
        customerList = customerRepository.findByEmail(email);
        if(customerList.isEmpty()){
            throw new UsernameNotFoundException("Email is not found");
        }
        return new SecurityCustomer(customerList.get(0));
    }
}
