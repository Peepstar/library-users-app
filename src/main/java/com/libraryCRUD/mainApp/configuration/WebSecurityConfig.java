package com.libraryCRUD.mainApp.configuration;

import com.libraryCRUD.mainApp.enums.Permissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

// --> Spring Security Configuration <-- \\
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired  // Authentication provider to inject bean from SecurityBeansInjector
    private AuthenticationProvider authenticationProvider;

    @Bean  // SecurityFilterChain for security restrictions in Spring Security
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionMngConfig -> sessionMngConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests( auth -> {
                   auth.requestMatchers(HttpMethod.GET, "/api/libraryusers").permitAll();
                    auth.requestMatchers("/error").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/libraryusers/**").hasAuthority(Permissions.READ_ONE_USER.name());
                    auth.requestMatchers(HttpMethod.POST, "/api/libraryusers").hasAuthority(Permissions.CREATE_ONE_USER.name());
                    auth.requestMatchers(HttpMethod.PUT, "/api/libraryusers/**").hasAuthority(Permissions.UPDATE_ONE_USER.name());
                    auth.requestMatchers(HttpMethod.DELETE, "/api/libraryusers/**").hasAuthority(Permissions.DELETE_ONE_USER.name());
                })
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .build();
    }

}
