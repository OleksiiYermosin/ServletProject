package ua.training.controller.commands;

import ua.training.model.entities.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public class LogOutCommand implements Command{

    @Override
    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request) {
        Set<String> users = (HashSet<String>) request.getServletContext().getAttribute("loggedUsers");
        String username = ((User) request.getSession().getAttribute("user")).getUsername();
        users.remove(username);
        request.getServletContext().setAttribute("loggedUsers", users);
        request.getSession().invalidate();
        return "redirect:/index";
    }

}
