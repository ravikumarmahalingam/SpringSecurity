package com.paypal.authentication;

import com.paypal.model.Customers;
import com.paypal.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
// This class is designed to implement the authentication provider interface.
// With this in place we restrict spring framework not to use the userDetailsService and other stuffs for authentication.
public class AuthenticationPro implements AuthenticationProvider {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
//    The password encoder that we use here actually autowire here comes from the SecurityConfig.java file. Since
//    we have given BCryptPasswordEncoder in the SecurityConfig class the matchs function here is called in the BCryptPasswordEncoder class
    PasswordEncoder passwordEncoder;

    @Override
//    This particular method does not call any userDetails service and so we are actually breaking the spring security
//    not to user the UserDetails interface and other stuffs.
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        These values are the values from the browser that is coming up from the customer while logging in.
        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        List<Customers> customerList = customerRepository.findByEmail(email);
        if(customerList != null){
            if(passwordEncoder.matches(pwd, customerList.get(0).getPwd())){
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                grantedAuthorities.add(new SimpleGrantedAuthority(customerList.get(0).getRole()));
                return new UsernamePasswordAuthenticationToken(email, pwd, grantedAuthorities);
            }else{
                throw new BadCredentialsException("Invalid password!!");
            }
        }else{
            throw new BadCredentialsException("No user registered with this details");
        }
    }

    @Override
//    This method here is important and it is used to tell the spring framework that only for UsernamePasswordAuthenticationToken
//    use this provider and for any other authentication types like fingerPrint and OTP use the default one provided by spring framework
    public boolean supports(Class<?> authType) {
        return authType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
