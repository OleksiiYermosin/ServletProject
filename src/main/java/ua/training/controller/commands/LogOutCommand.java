package ua.training.controller.commands;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public class LogOutCommand implements Command{

    @Override
    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request) {

        /*Set<String> users = (HashSet<String>) request.getServletContext().getAttribute("loggedUsers");
        users.remove(String.valueOf(request.getSession().getAttribute("nickname")));
        request.getSession().invalidate();*/
        System.out.println("Logout");
        /*CommandUtility.setUserRole(request, User.ROLE.UNKNOWN, "Guest");*/
        return "redirect:/index.jsp";
    }

}
