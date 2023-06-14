package com.example.cashcard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
* Configuration class for security settings.
*/
@Configuration
public class SecurityConfig {

  /**
   * Configures the security filter chain.
   *
   * @param http the HttpSecurity object to configure
   * @return the configured SecurityFilterChain object
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/cashcards/**").hasRole("CARD-OWNER"))
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(httpBasic -> { });

    return http.build();
  }

  /**
   * Creates a BCryptPasswordEncoder bean for password encoding.
   *
   * @return the BCryptPasswordEncoder bean
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Creates a UserDetailsService bean with test users.
   *
   * @param passwordEncoder the PasswordEncoder bean
   * @return the UserDetailsService bean
   */
  @Bean
  public UserDetailsService testOnlyUsers(final PasswordEncoder passwordEncoder) {
    User.UserBuilder users = User.builder();
    UserDetails sarah = users
            .username("sarah1")
            .password(passwordEncoder.encode("abc123"))
            .roles("CARD-OWNER")
            .build();
    UserDetails hankOwnsNoCards = users
            .username("hank-owns-no-cards")
            .password(passwordEncoder.encode("qrs456"))
            .roles("NON-OWNER")
            .build();
    UserDetails kumar = users
            .username("kumar2")
            .password(passwordEncoder.encode("xyz789"))
            .roles("CARD-OWNER")
            .build();
    return new InMemoryUserDetailsManager(sarah, hankOwnsNoCards, kumar);
  }
}
