package com.zen.notify.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zen.notify.service.ZenUserDetailsService;
import com.zen.notify.utility.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private ZenUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		
		  final String authorizationHeader = request.getHeader("Authorization");
		  
		  String username = null; String jwt = null;
		  
		  if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
		  { jwt = authorizationHeader.substring(7); try { username =
		  jwtUtil.extractUsername(jwt); } catch (Exception e) {
		  response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		  response.getWriter().write("Invalid JWT token"); return; } }
		  
		  if (username != null &&
		  SecurityContextHolder.getContext().getAuthentication() == null) {
		  ZenUserDetails userDetails = userDetailsService.loadUserByUsername(username);
		  
		  if (jwtUtil.validateToken(jwt, userDetails.getUsername())) { if
		  (!userDetails.isFirstLogin() &&
		  !request.getRequestURI().contains("/reset-password")) {
		  response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		  response.getWriter().write("First-time login: password reset required");
		  return; }
		  
		  UsernamePasswordAuthenticationToken authentication = new
		  UsernamePasswordAuthenticationToken( userDetails, null,
		  userDetails.getAuthorities());
		  
		  authentication.setDetails(new
		  WebAuthenticationDetailsSource().buildDetails(request));
		  SecurityContextHolder.getContext().setAuthentication(authentication); } }
		 

		filterChain.doFilter(request, response);
	}
}
