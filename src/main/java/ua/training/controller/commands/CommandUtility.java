package ua.training.controller.commands;

import javax.servlet.http.HttpServletRequest;
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
    static boolean checkUserIsLogged(HttpServletRequest request, String username){
        Set<String> loggedUsers = (HashSet<String>) request.getServletContext().getAttribute("loggedUsers");
        if(loggedUsers.stream().anyMatch(username::equals)){
            return true;
        }
        loggedUsers.add(username);
        request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
        return false;
    }

}
