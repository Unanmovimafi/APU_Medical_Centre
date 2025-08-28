/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.user;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Login authentication filter to check if users are properly authenticated
 * before accessing protected resources.
 * 
 * @author khong
 */
@WebFilter("/*")
public class LoginCheck implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get the current session (don't create a new session if it doesn't exist)
        HttpSession session = httpRequest.getSession(false);

        // Get the request URI and context path
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // Remove context path to get the relative URI
        String relativePath = requestURI.substring(contextPath.length());

        // Define public resources that don't require authentication
        boolean isPublicResource = isPublicResource(relativePath);

        if (isPublicResource) {
            // Allow access to public resources
            chain.doFilter(request, response);
        } else {
            // Check if user is authenticated
            boolean isAuthenticated = isUserAuthenticated(session);

            if (isAuthenticated) {
                // User is authenticated, check if they have access to the requested resource
                if (hasAccessToResource(session, relativePath)) {
                    chain.doFilter(request, response);
                } else {
                    // User doesn't have access to this resource, redirect to appropriate dashboard
                    redirectToUserDashboard(session, httpResponse, contextPath);
                }
            } else {
                // User is not authenticated, redirect to login page
                httpResponse.sendRedirect(contextPath + "/login.jsp");
            }
        }
    }

    /**
     * Check if the requested resource is public (doesn't require authentication)
     */
    private boolean isPublicResource(String relativePath) {
        return relativePath.equals("/") ||
                relativePath.equals("/login.jsp") ||
                relativePath.equals("/index.html") ||
                relativePath.startsWith("/login") ||
                relativePath.startsWith("/assets/") ||
                relativePath.startsWith("/css/") ||
                relativePath.startsWith("/js/") ||
                relativePath.startsWith("/images/") ||
                relativePath.startsWith("/CreateUser") ||
                relativePath.contains(".css") ||
                relativePath.contains(".js") ||
                relativePath.contains(".png") ||
                relativePath.contains(".jpg") ||
                relativePath.contains(".jpeg") ||
                relativePath.contains(".gif") ||
                relativePath.contains(".ico");
    }

    /**
     * Check if user is authenticated by checking session attributes
     */
    private boolean isUserAuthenticated(HttpSession session) {
        if (session == null) {
            return false;
        }

        // Check if any user type is logged in
        return session.getAttribute("customerSession") != null ||
                session.getAttribute("managerSession") != null ||
                session.getAttribute("counterStaffSession") != null ||
                session.getAttribute("doctorSession") != null;
    }

    /**
     * Check if the authenticated user has access to the requested resource
     */
    private boolean hasAccessToResource(HttpSession session, String relativePath) {
        // Customer access
        if (session.getAttribute("customerSession") != null) {
            return relativePath.startsWith("/customer/") ||
                    relativePath.startsWith("/logout");
        }

        // Manager access
        if (session.getAttribute("managerSession") != null) {
            return relativePath.startsWith("/manager/") ||
                    relativePath.startsWith("/logout");
        }

        // Counter Staff access
        if (session.getAttribute("counterStaffSession") != null) {
            return relativePath.startsWith("/staff/") ||
                    relativePath.startsWith("/logout");
        }

        // Doctor access
        if (session.getAttribute("doctorSession") != null) {
            return relativePath.startsWith("/doctor/") ||
                    relativePath.startsWith("/logout");
        }

        return false;
    }

    /**
     * Redirect user to their appropriate dashboard based on their role
     */
    private void redirectToUserDashboard(HttpSession session, HttpServletResponse response, String contextPath)
            throws IOException {

        if (session.getAttribute("customerSession") != null) {
            response.sendRedirect(contextPath + "/customer/dashboard");
        } else if (session.getAttribute("managerSession") != null) {
            response.sendRedirect(contextPath + "/manager/dashboard");
        } else if (session.getAttribute("counterStaffSession") != null) {
            response.sendRedirect(contextPath + "/staff/dashboard");
        } else if (session.getAttribute("doctorSession") != null) {
            response.sendRedirect(contextPath + "/doctor/dashboard");
        } else {
            response.sendRedirect(contextPath + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}
