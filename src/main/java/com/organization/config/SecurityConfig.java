package com.organization.config;

import com.organization.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()

                // EMPLOYER can access everything
                .requestMatchers("/api/departments/**").hasRole("EMPLOYER")
                .requestMatchers("/api/employees/**").hasRole("EMPLOYER")

                // DEPARTMENT_HEAD can manage their departments
                .requestMatchers("/api/department-head/**").hasAnyRole("DEPARTMENT_HEAD", "EMPLOYER")

                // SUB_DEPARTMENT_HEAD can manage their sub-departments
                .requestMatchers("/api/sub-department-head/**").hasAnyRole("SUB_DEPARTMENT_HEAD", "EMPLOYER")

                // TEAM_LEADER can manage only their team
                .requestMatchers("/api/team-leader/**").hasAnyRole("TEAM_LEADER", "EMPLOYER")

                // EMPLOYEE can only view their own profile
                .requestMatchers("/api/employees/me").hasAnyRole("EMPLOYEE", "EMPLOYER")

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }
}


//./mvnw spring-boot:run
//  taskkill /PID 26852 /F