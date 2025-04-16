package org.bisha.ecommercefinal.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.bisha.ecommercefinal.dtos.UserDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class AdminCheckFilter extends OncePerRequestFilter {

    private static final List<String> ADMIN_ONLY_PATHS = Arrays.asList(
            "api/products/add",
            "api/products/update",
            "api/products/delete",
            "api/categories/add",
            "api/categories/update",
            "api/categories/delete"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            UserDto userDto = (UserDto) session.getAttribute("user");

            if (ADMIN_ONLY_PATHS.stream().anyMatch(path -> request.getRequestURI().startsWith(path))) {
                if (!"ROLE_ADMIN".equals(userDto.getRole())) {
                    response.sendRedirect("/home");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}