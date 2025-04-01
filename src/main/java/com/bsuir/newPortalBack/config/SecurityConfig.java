package com.bsuir.newPortalBack.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Slf4j
@Configuration
public class SecurityConfig {
  @PostConstruct
  public void init() {
    log.info("✅ SecurityConfig загружен!");
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable()) // Отключаем CSRF (иначе POST-запросы могут блокироваться)
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/register").permitAll() // Разрешаем регистрацию без авторизации
        .anyRequest().authenticated()
      )
      .httpBasic(withDefaults());
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
