package ua.training.controller.commands;

import ua.training.model.entities.User;

import javax.servlet.http.HttpServletRequest;

public class AfterLoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        if (((User) request.getSession().getAttribute("user")).getRole() == 1L) {
            return "/WEB-INF/users/user.jsp";
        }else{
            return "/WEB-INF/admins/admin.jsp";
        }
    }

}
