package ua.training.controller.commands;

import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.User;
import ua.training.model.services.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoadDefaultPageCommand implements Command {

    private final UserService userService;

    public LoadDefaultPageCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            return "/common/main.jsp";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user.getRole() == 1L) {
            return "/WEB-INF/users/user.jsp";
        } else {
            return "/WEB-INF/admins/admin.jsp";
        }
    }
}
