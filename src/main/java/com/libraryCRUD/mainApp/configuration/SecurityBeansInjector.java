package com.libraryCRUD.mainApp.configuration;

import com.libraryCRUD.mainApp.exceptionhandling.exceptions.LibraryUserNotFoundException;
import com.libraryCRUD.mainApp.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// --> Beans for Spring Security <-- \\
@Component
public class SecurityBeansInjector {
    @Autowired  // Repository field to findByEmail an UserDetailsService
    LibraryRepository libraryRepository;

    @Bean  // ProviderManager implements Authentication manager for injection
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean  // AuthenticationProvider for Users authentication
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean  // Password encoder bean to encode password through application
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean  // User details service for authentication provider
    UserDetailsService userDetailsService(){
        return userName -> libraryRepository.findByEmail(userName).
                orElseThrow(() ->  new LibraryUserNotFoundException("LibraryUser not found"));
    }

}
