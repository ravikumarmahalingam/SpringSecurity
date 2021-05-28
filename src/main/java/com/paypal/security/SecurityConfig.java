package com.paypal.security;

import com.paypal.filter.ReferenceFilter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Configuration
//    This class is to override the default security configuration of the spring framework.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    protected void configure(HttpSecurity http) throws Exception {

        http.
//      The below configurations are specific to CORS(Cross Origin Resource Sharing)
//      Basically if you want ur backEnd app to be accessed by any other application on a different origin
//      then all these configurations are required.
//      Different origin --> http method, domain or the port
                cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest httpServletRequest) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(Collections.singletonList(""));
                corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                corsConfiguration.setMaxAge(3600L);
                return corsConfiguration;
            }
        }).and().
//      The below config is to generate a CSRF token everytime and send it to the UI so that UI can use this token
//      query anything from the backEnd. UI code has to catch the token generated here and then append with
//      any request that it is going to make to the backEnd.
                csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().
//      The below config is to disable the CSRF completely
//      csrf().disable().
//      In cases where we do not want the CSRF to take place for particular methods then we can do the following
//      csrf().ignoringAntMatchers("</pathOfMethodToDisableCSRF>")
        addFilterAfter(new ReferenceFilter(), BasicAuthenticationFilter.class).

                authorizeRequests((requests) -> {
//        This way we can configure or restrict our endpoints with the authorities and roles as below
            ((AuthorizedUrl)requests.antMatchers("/display")).hasAuthority("user");
//            ((AuthorizedUrl)requests.antMatchers("/reference")).hasAuthority("user");
//            ((AuthorizedUrl)requests.antMatchers("/reference")).hasAnyAuthority("user", "admin");
            ((AuthorizedUrl)requests.antMatchers("/display")).hasRole("user");
//            ((AuthorizedUrl)requests.antMatchers("/reference")).hasAnyRole("user", "admin");
            ((AuthorizedUrl)requests.antMatchers("/show")).permitAll();
        });
        http.formLogin();
        http.httpBasic();
    }

//    In memory authentication
//    Two ways by which the username and passwords can be configured internally using inmemory
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("ravi").password("ravii").authorities("admin").and()
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }

//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        UserDetails user = User.withUsername("ravi").password("ravii").authorities("admin").build();
//        UserDetails user1 = User.withUsername("kani").password("kanii").authorities("admin").build();
//        userDetailsManager.createUser(user);
//        userDetailsManager.createUser(user1);
//        auth.userDetailsService(userDetailsManager);
//    }

//    This method is important if we are going to maintain users with the above strategy.
//     We have to have this method in place for all the authentication techniques If not we are going to get password encoder null issue
//    @Bean
//    public PasswordEncoder getPasswordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }

//    JdbcUserDetailsManager authentication. Fetching user details from DB
//    This method can be used if we are planning to use JdbcUserDetailsManager class without any modification.
//    But we have to use the same tableName, columnName and the values (users table and authorities table)
//    Add the two dependencies and then add the below method and the password encoder as well. This will fetch the details
//    of the user from the tables like users and authorities
//    @Bean
//    public UserDetailsManager getUserDetailsManager(DataSource dataSource){
//        return new JdbcUserDetailsManager(dataSource);
//    }

//    For creating our own tables and implementation of the userDetails service. Need to provide proper annotations.
//    Else the application start up would generate a default password and it will be of no use. So use appropriate annotations like
//    @Repository, @Service in respective classes
//    @EnableJpaRepositories, @EntityScan in the main spring boot class


//    Now trying to use BCryptPasswordEncoder instead of BCryptPasswordEncoder
//    To use BCryptPasswordEncoder just the instance here has to be changed. Another thing that has to be done is
//    to Bcrypt the password in the DB. If this is not done then you will be getting an error saying like
//    "Encoded password does not look like BCrypt"
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}