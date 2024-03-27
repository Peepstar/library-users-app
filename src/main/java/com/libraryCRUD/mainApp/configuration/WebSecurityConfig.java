package com.libraryCRUD.mainApp.configuration;

import com.libraryCRUD.mainApp.enums.Permissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionMngConfig -> sessionMngConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers(HttpMethod.GET, "/api/libraryusers/**").hasAuthority(Permissions.READ_ONE_USER.name());
                    auth.anyRequest().permitAll();
                   /* auth.requestMatchers(HttpMethod.GET, "/api/libraryusers").permitAll();
                    auth.requestMatchers("/error").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/libraryusers/**").hasAuthority(Permissions.READ_ONE_USER.name());
                    auth.requestMatchers(HttpMethod.POST, "/api/libraryusers").hasAuthority(Permissions.CREATE_ONE_USER.name());
                    auth.requestMatchers(HttpMethod.PUT, "/api/libraryusers/**").hasAuthority(Permissions.UPDATE_ONE_USER.name());
                    auth.requestMatchers(HttpMethod.DELETE, "/api/libraryusers/**").hasAuthority(Permissions.DELETE_ONE_USER.name()); */
                })
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .build();
    }



}
