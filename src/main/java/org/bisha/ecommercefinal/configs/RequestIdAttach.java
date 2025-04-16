package org.bisha.ecommercefinal.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.bisha.ecommercefinal.dtos.UserDto;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestIdAttach extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            UserDto userDto = (UserDto) session.getAttribute("user");
            request.setAttribute("userId", userDto.getId());
        }
        filterChain.doFilter(request, response);
    }
}
