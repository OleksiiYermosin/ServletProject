package ua.training.controller.commands;

import ua.training.model.services.OrderService;

import javax.servlet.http.HttpServletRequest;

public class ViewAllOrdersCommand implements Command{

    private final OrderService orderService;

    public ViewAllOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return "/WEB-INF/users/vieworders.jsp";
        }
        return "/WEB-INF/error.jsp";
    }

}
