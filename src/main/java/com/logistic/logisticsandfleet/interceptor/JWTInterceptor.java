package com.logistic.logisticsandfleet.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import com.logistic.logisticsandfleet.config.JWTHelper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    private static final List<String> ADMIN_API_PATHS = List.of("/shipment/*/status");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Missing or invalid token");
            return false;
        }

        String token = authorizationHeader.substring(7);

        try {
            String role = JWTHelper.getRoleFromToken(token);
            String requestURI = request.getRequestURI();

            System.out.println("----Role: ----------" + role);

            // Check if the path requires admin role
            // Check if the path requires admin role
            for (String path : ADMIN_API_PATHS) {
                if (new AntPathMatcher().match(path, requestURI) && !role.equals("ADMIN")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Forbidden: Admin access only");
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid token");
            return false;
        }
    }
}
