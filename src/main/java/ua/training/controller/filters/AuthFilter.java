package ua.training.controller.filters;

import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.User;
import ua.training.model.services.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

public class AuthFilter implements Filter {

    private final UserService userService = new UserService();

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
            throw new RuntimeException("auth.error.message");
        }
        if(userFromSession != null){
            User updatedUser = userService.findUserById(userFromSession.getId(), ConnectionManager.getConnection(), true);
            ((HttpServletRequest) servletRequest).getSession().setAttribute("user", updatedUser);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
