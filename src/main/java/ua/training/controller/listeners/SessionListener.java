package ua.training.controller.listeners;

import ua.training.model.entities.User;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;
import java.util.Set;

public class SessionListener implements HttpSessionListener {

    @Override
    @SuppressWarnings("unchecked")
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        Set<String> loggedUsers = (HashSet<String>) httpSessionEvent.getSession().getServletContext().getAttribute("loggedUsers");
        User user = (User) httpSessionEvent.getSession().getAttribute("user");
        loggedUsers.remove(user.getUsername());
        httpSessionEvent.getSession().setAttribute("loggedUsers", loggedUsers);
    }

}
