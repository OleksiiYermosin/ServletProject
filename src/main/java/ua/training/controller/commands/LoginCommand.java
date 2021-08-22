package ua.training.controller.commands;

import ua.training.model.entities.User;
import ua.training.model.services.UserService;

import javax.servlet.http.HttpServletRequest;

import static ua.training.model.utils.RegularExpressions.*;

public class LoginCommand implements Command {

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return "/common/login.jsp";
        }
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        if (!username.matches(NICK_REGEX) || (pass == null || pass.equals(""))) {
            request.setAttribute("loginError", true);
            return "/common/login.jsp";
        }
        try {
            User userFromDB = userService.findUserByUsername(username);
            if (!userFromDB.getPassword().equals(pass)){
                request.setAttribute("loginError", true);
                return "/common/login.jsp";
            }
            if (CommandUtility.checkUserIsLogged(request, userFromDB.getUsername())) {
                throw new IllegalArgumentException();
            }
            request.getSession().setAttribute("user", userFromDB);
            if (userFromDB.getRole() == 1L) {
                return "redirect:/user";
            } else {
                //return "redirect:/WEB-INF/admins/admin.jsp";
                System.out.println("Admin " + userFromDB.getUsername());
            }
            return "/common/login.jsp";
        } catch (IllegalArgumentException exception) {
            request.setAttribute("isAlreadyLogged", true);
            return "/WEB-INF/error.jsp";
        }
    }

}
