package com.example.customerorder.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class OrderAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // Provide a default value for the auditor if you're not using Spring Security
        return Optional.of("Balram");
    }
}
