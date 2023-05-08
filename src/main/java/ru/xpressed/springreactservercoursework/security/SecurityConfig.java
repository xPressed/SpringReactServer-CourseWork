package ru.xpressed.springreactservercoursework.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.xpressed.springreactservercoursework.security.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/auth/**", "/docs", "/swagger", "/swagger-ui/**").permitAll()

                .antMatchers("/user/all" , "/user/id", "/user/id", "/user/params", "/user/update").hasAnyRole("TEACHER", "ADMIN")
                .antMatchers("/user/delete").hasRole("ADMIN")

                .antMatchers("/performance/add", "/performance/all", "/performance/id", "/performance/params", "/performance/delete", "/performance/update").hasAnyRole("TEACHER", "ADMIN")
                .antMatchers("/attendance/add", "/attendance/all", "/attendance/id", "/attendance/params", "/attendance/delete", "/attendance/update").hasAnyRole("TEACHER", "ADMIN")
                .anyRequest().authenticated()


                .and()
                .cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
