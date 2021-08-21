package ua.training.controller.commands;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class CommandUtility {

    /*public static void setUserRole(HttpServletRequest request,
                            User.ROLE role, String name) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        context.setAttribute("userName", name);
        session.setAttribute("role", role);
    }*/

    @SuppressWarnings("unchecked")
    static boolean checkUserIsLogged(HttpServletRequest request, String userName){
        Set<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute("loggedUsers");
        if(loggedUsers.stream().anyMatch(userName::equals)){
            return true;
        }
        loggedUsers.add(userName);
        request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
        return false;
    }

}
