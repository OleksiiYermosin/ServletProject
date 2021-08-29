package ua.training.controller.commands;

import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.User;
import ua.training.model.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

public class ProcessOrderCommand implements Command {

    private final OrderService orderService;

    public ProcessOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long id, userId;
        User user;
        id = Long.parseLong(request.getParameter("id"));
        boolean mode = Boolean.parseBoolean(request.getParameter("mode"));
        if (request.getParameter("user_id") != null) {
            userId = Integer.parseInt(request.getParameter("user_id"));
        } else if ((user = (User) request.getSession().getAttribute("user")) != null) {
            userId = user.getId();
        } else {
            throw new RuntimeException();
        }
        Connection connection = ConnectionManager.getConnection();
        orderService.processOrder(id, userId, connection, mode);
        ConnectionManager.close(connection);
        if (((User) request.getSession().getAttribute("user")).getRole() == 1L) {
            return "redirect:/user/orders";
        } else {
            return "redirect:/admin/orders";
        }
    }
}
