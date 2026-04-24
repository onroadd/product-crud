package com.example.productcrud.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Tandai bahwa ini login pertama kali, agar kita bisa redirect ke katalog
            session.setAttribute("SHOW_CATALOG_FIRST", true);
            response.sendRedirect("/products?catalog=true");
        } else if (authentication != null && authentication.isAuthenticated()) {
            response.sendRedirect("/products?catalog=true");
        } else {
            response.sendRedirect("/auth/login");
        }
    }
}

