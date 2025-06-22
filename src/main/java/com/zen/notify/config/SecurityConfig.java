package com.zen.notify.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zen.notify.filters.JwtAuthenticationFilter;
import com.zen.notify.service.ZenUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {
	
	  @Autowired private ZenUserDetailsService userDetailsService;
	  
	  @Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;
	  
	  
	  @Bean
	  
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	  http.csrf().disable().authorizeHttpRequests(auth -> auth //
	  .requestMatchers("/**").permitAll()
	  //.requestMatchers("/api/authenticate").permitAll().requestMatchers(
	 // "/api/users/create", "/api/login") .permitAll()
	  .anyRequest().authenticated())
	  .sessionManagement(sess ->
	  sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	  .addFilterBefore(jwtAuthenticationFilter,
	  UsernamePasswordAuthenticationFilter.class);
	  
	  return http.build(); }
	  
	  
	  @Bean public WebMvcConfigurer corsConfigurer() { return new
	  WebMvcConfigurer() {
	  
	  @Override public void addCorsMappings(CorsRegistry registry) {
	  registry.addMapping("/**") // allow all paths
	  .allowedOrigins("http://localhost:3000") // frontend origin
	  .allowedMethods("GET", "POST", "PUT", "DELETE",
	  "OPTIONS").allowedHeaders("*") .allowCredentials(true); } }; }
	  
	  @Bean public AuthenticationManager
	  authenticationManager(AuthenticationConfiguration config) throws Exception {
	  return config.getAuthenticationManager(); }
	  
	  @Bean public PasswordEncoder passwordEncoder() { return new
	  BCryptPasswordEncoder(); }
	 

}
