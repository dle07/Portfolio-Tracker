package com.PortfolioTracker.Security;

import javax.servlet.SessionTrackingMode;

import com.PortfolioTracker.DAO.UserDAO;
import com.PortfolioTracker.Services.ApplicationUserDetailsService;
import com.fasterxml.jackson.databind.deser.ValueInstantiator.Gettable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
      
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/test/**").permitAll()
            .antMatchers("/user/authenticate").permitAll()
            .antMatchers("/user/register").permitAll()  //Allows everyone to access this URL to authenticate
            .anyRequest().authenticated()  //every other request must be authenticated
            .and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // Tells Spring Security to not create session ids
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Tells Spring security to call this filter before the UsernamePasswordAuthenticationFilter is called
    }


    // Spring Security used to provide a AuthenticationManager as a bean, but not anymore so we have to create our own.
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    

    @Bean
    public DaoAuthenticationProvider authProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(applicationUserDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;


    }
}
