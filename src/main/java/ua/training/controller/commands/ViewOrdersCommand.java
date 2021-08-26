package ua.training.controller.commands;

import javax.servlet.http.HttpServletRequest;

public class ViewOrdersCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return "/WEB-INF/users/viewdetails.jsp";
        }
        return "/WEB-INF/error.jsp";
    }

}
