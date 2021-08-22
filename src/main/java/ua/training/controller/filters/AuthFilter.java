package ua.training.controller.filters;

import ua.training.model.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        User userFromSession = (User) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        String[] pages = {"/service/login", "service/registration"};
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        boolean isAllowedPages = Arrays.asList(pages).contains(requestURI);
        if ((userFromSession == null && !isAllowedPages) || (userFromSession != null && isAllowedPages)) {
            servletRequest.getRequestDispatcher("/WEB-INF/error.jsp").forward(servletRequest, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
