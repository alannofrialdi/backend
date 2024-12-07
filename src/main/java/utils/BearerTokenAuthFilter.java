package utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

@Component
public class BearerTokenAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    public BearerTokenAuthFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = extractUsernameFromToken(token); // Implementasikan metode ini sesuai JWT Anda

            if (username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (isTokenValid(token, userDetails)) { // Validasi token di sini
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isTokenValid(String token, UserDetails userDetails) {
        // Tambahkan logika validasi token Anda
        return true;
    }

    private String extractUsernameFromToken(String token) {
        // Implementasikan logika ekstraksi username dari token JWT
        return "exampleUsername"; // Ganti dengan logika Anda
    }
}
