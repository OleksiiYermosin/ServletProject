package ua.training.controller.commands;

import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.Order;
import ua.training.model.entities.User;
import ua.training.model.services.OrderService;
import ua.training.model.utils.Page;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

import static ua.training.model.utils.MyConstants.*;

public class ViewUserOrdersCommand implements Command {

    private final OrderService orderService;

    public ViewUserOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String sort = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
        String sortDirection = request.getParameter("sortDirection") == null ? "DESC" : request.getParameter("sortDirection");
        int page = request.getParameter("page") == null ? 0 : Integer.parseInt(request.getParameter("page"));
        Connection connection = ConnectionManager.getConnection();
        try {
            Page<Order> orderPage = orderService.getPaginatedOrders((User) request.getSession().getAttribute("user"), PAGE_SIZE,
                    page, sort, sortDirection, false, null, false, null, null, connection);
            ConnectionManager.close(connection);
            request.setAttribute("orderPage", orderPage);
        }catch (RuntimeException exception){
            ConnectionManager.close(connection);
            return "/WEB-INF/error.jsp";
        }
        return "/WEB-INF/users/vieworders.jsp";
    }

}
