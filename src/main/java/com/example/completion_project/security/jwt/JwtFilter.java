package com.example.completion_project.security.jwt;

import com.example.completion_project.exception.JwtExceptionCustom;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);

            if (token != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                jwtProvider.validateToken(token);

                String username = jwtProvider.getUsernameFromToken(token);

                UserDetails userDetail =
                        userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetail,
                                null,
                                userDetail.getAuthorities()
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (JwtExceptionCustom ex) {
            handlerExceptionResolver.resolveException(
                    request,
                    response,
                    null,
                    ex
            );
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || authorization.isBlank()) {
            return null;
        }

        if (authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7).trim();

            if (token.isBlank()) {
                throw new JwtExceptionCustom("Token rỗng hoặc null");
            }

            return token;
        }

        return null;
    }
}