package com.midas.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    System.out.println("====================================");
    System.out.println("URI: " + request.getRequestURI());
    System.out.println("Authorization Header: " + authHeader);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        System.out.println("NO TOKEN FOUND");
        filterChain.doFilter(request, response);
        return;
    }

    String jwt = authHeader.substring(7);

    String userEmail = jwtService.extractUsername(jwt);

    System.out.println("EMAIL FROM TOKEN = " + userEmail);

    UserDetails userDetails =
            userDetailsService.loadUserByUsername(userEmail);

    if (jwtService.isTokenValid(jwt, userDetails)) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

        authToken.setDetails(
                new WebAuthenticationDetailsSource()
                        .buildDetails(request));

        SecurityContextHolder.getContext()
                .setAuthentication(authToken);

        System.out.println("AUTH SUCCESS");
    }

    filterChain.doFilter(request, response);
}
}