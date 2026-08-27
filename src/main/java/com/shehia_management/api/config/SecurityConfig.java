package com.shehia_management.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtConfig jwtConfig;

    public SecurityConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF (Mandatory for stateless REST APIs)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Set Stateless Session Management
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. Define URL Access Permissions
                .authorizeHttpRequests(auth -> auth
                        // =====================================================
                        // PUBLIC ENDPOINTS (No Authentication Required)
                        // =====================================================
                        // View public announcements, leadership, and public info
                        .requestMatchers("GET", "/api/v1/public/**").permitAll()
                        .requestMatchers("OPTIONS", "/api/v1/public/**").permitAll()
                        
                        // Authentication endpoints (Login & Register)
                        .requestMatchers("POST", "/api/v1/auth/login").permitAll()
                        .requestMatchers("POST", "/api/v1/auth/**").permitAll()
                        .requestMatchers("POST", "/api/v1/resident/register").permitAll()
                        
                        // =====================================================
                        // RESIDENT ENDPOINTS (RESIDENT or ADMIN role required)
                        // =====================================================
                        // View resident profile
                        .requestMatchers("GET", "/api/v1/resident/profile/**").hasAnyRole("RESIDENT", "ADMIN")
                        // Update profile (PUT, PATCH)
                        .requestMatchers("PUT", "/api/v1/resident/profile/**").hasAnyRole("RESIDENT", "ADMIN")
                        .requestMatchers("PATCH", "/api/v1/resident/profile/**").hasAnyRole("RESIDENT", "ADMIN")
                        
                        // Letter endpoints (RESIDENT only)
                        .requestMatchers("POST", "/api/v1/resident/letters/apply").hasRole("RESIDENT")
                        .requestMatchers("GET", "/api/v1/resident/letters/**").hasAnyRole("RESIDENT", "ADMIN")
                        
                        // Issue endpoints (RESIDENT only)
                        .requestMatchers("POST", "/api/v1/resident/issues/report").hasRole("RESIDENT")
                        .requestMatchers("GET", "/api/v1/resident/issues/**").hasAnyRole("RESIDENT", "ADMIN")
                        
                        // =====================================================
                        // ADMIN ENDPOINTS (ADMIN or STAFF role required)
                        // =====================================================
                        
                        // Resident Management (ADMIN/STAFF)
                        .requestMatchers("GET", "/api/v1/admin/residents").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("PUT", "/api/v1/admin/residents/**").hasRole("ADMIN")
                        .requestMatchers("PATCH", "/api/v1/admin/residents/**").hasRole("ADMIN")
                        .requestMatchers("DELETE", "/api/v1/admin/residents/**").hasRole("ADMIN")
                        
                        // Letter Management (ADMIN/STAFF)
                        .requestMatchers("GET", "/api/v1/admin/letters").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("PUT", "/api/v1/admin/letters/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("PATCH", "/api/v1/admin/letters/**").hasAnyRole("ADMIN", "STAFF")
                        
                        // Issue Management (ADMIN/STAFF)
                        .requestMatchers("GET", "/api/v1/admin/issues").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("PUT", "/api/v1/admin/issues/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("PATCH", "/api/v1/admin/issues/**").hasAnyRole("ADMIN", "STAFF")
                        
                        // Announcement Management (ADMIN only)
                        .requestMatchers("POST", "/api/v1/admin/announcements").hasRole("ADMIN")
                        .requestMatchers("PUT", "/api/v1/admin/announcements/**").hasRole("ADMIN")
                        .requestMatchers("PATCH", "/api/v1/admin/announcements/**").hasRole("ADMIN")
                        .requestMatchers("DELETE", "/api/v1/admin/announcements/**").hasRole("ADMIN")
                        
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                // Add JWT filter before the default authentication filter
                .addFilterBefore(new JwtAuthenticationFilter(jwtConfig), 
                               UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
