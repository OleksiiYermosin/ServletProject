package ua.training.controller.commands;

import javax.servlet.http.HttpServletRequest;

public class LoadInitialPageCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        return "/common/main.jsp";
    }
}
