package com.paypal.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// This class is for customizing our own tables with user values.
public class SecurityCustomer implements UserDetails {

    private static final long serialVersionUID = -1629753116842946056L;

    public Customers customers;

    public SecurityCustomer(Customers customers){
        this.customers = customers;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(customers.getRole()));
        return grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return customers.getPwd();
    }

    @Override
    public String getUsername() {
        return customers.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
