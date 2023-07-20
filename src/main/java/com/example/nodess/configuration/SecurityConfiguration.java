package com.example.nodess.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    //private final AuthService  service;
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests().
                requestMatchers("/api/1.0/auth","/api/1.0/logout", "/node")
                .authenticated().and().authorizeHttpRequests().anyRequest().permitAll()
                .and().exceptionHandling().authenticationEntryPoint((request, response, authException) ->
                         response.sendError(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase()));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(tokenFilterConfiguration(), UsernamePasswordAuthenticationFilter.class);

        return http.build();


    }


//        @Autowired
//        public void configure(AuthenticationManagerBuilder auth) {
//            try {
//                auth.userDetailsService(userAuthService).passwordEncoder(new BCryptPasswordEncoder());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }



    @Bean
     PasswordEncoder bypassEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    TokenFilterConfiguration tokenFilterConfiguration(){
        return new TokenFilterConfiguration();
    }
}
