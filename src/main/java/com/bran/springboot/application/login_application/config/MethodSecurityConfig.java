package com.bran.springboot.application.login_application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(
    prePostEnabled = true
)
public class MethodSecurityConfig {

}
