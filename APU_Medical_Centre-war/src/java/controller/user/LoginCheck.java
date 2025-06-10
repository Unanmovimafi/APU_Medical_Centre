/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.user;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author khong
 */
@WebFilter("/*")
public class LoginCheck implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // You can add initialization logic here if needed
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get the current session (don't create a new session if it doesn't exist)
        HttpSession session = httpRequest.getSession(false);  // false means don't create a session if none exists

        // Get the request URI (the page the user is trying to access)
        String requestURI = httpRequest.getRequestURI();

        // Exclude login, logout, and static resources like CSS, JS, and images from the login check
        if (requestURI.endsWith("login") || requestURI.endsWith("login.jsp") ||
            requestURI.endsWith("logout") || requestURI.contains("/css/") || requestURI.contains("/js/") || requestURI.contains("/images/")) {
            chain.doFilter(request, response);  // Allow the request to continue
        } else {
            // Check if the session exists and contains the user object
            if (session == null || session.getAttribute("userSession") == null) {
                // User is not logged in, redirect to the login page
                httpResponse.sendRedirect("login.jsp"); // Redirect to login page
            } else {
                // User is logged in, continue with the request
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}
