package com.vma.booksbase.config;

import com.vma.booksbase.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final DataSource dataSource;

    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/about").permitAll()
                .antMatchers(HttpMethod.GET, "/books", "/book/**", "/authors", "/author/**", "/about", "/findbooks?**").permitAll()
                .antMatchers(HttpMethod.POST, "/users/**", "/user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/**", "/user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/**", "/user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/author/**", "/book/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/author/**", "/book/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/author/**", "/book/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/books", true)
                    .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

