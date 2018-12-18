package com.example.demo.config;

import com.example.demo.security.ApiKeyAuthenticationFilter;
import com.example.demo.security.ApiKeyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by pubudu welagedara on 12/17/18.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                httpBasic().disable().
                csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.
                antMatcher("/public/**").
                authorizeRequests().
                anyRequest().permitAll();

        http.
                antMatcher("/protected/**").
                authorizeRequests().
                anyRequest().authenticated().
                and().
                addFilterBefore(new ApiKeyAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(apiKeyAuthenticationProvider);
    }
}
