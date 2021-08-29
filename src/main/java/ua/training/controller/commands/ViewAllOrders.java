package ua.training.controller.commands;

import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.Order;
import ua.training.model.entities.User;
import ua.training.model.services.OrderService;
import ua.training.model.utils.Page;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.time.LocalDate;

import static ua.training.model.utils.MyConstants.PAGE_SIZE;

public class ViewAllOrders implements Command{

    private final OrderService orderService;

    public ViewAllOrders(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String sortDirection = request.getParameter("sortDirection") == null ? "DESC" : request.getParameter("sortDirection");
        String sort = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
        int page = request.getParameter("page") == null ? 0 : Integer.parseInt(request.getParameter("page"));
        boolean isSearchByDate = request.getParameter("searchByDate") != null && request.getParameter("searchByDate").equals("on");
        boolean isSearchByName = request.getParameter("searchByName") != null && request.getParameter("searchByName").equals("on");
        Connection connection = ConnectionManager.getConnection();
        try {
            Page<Order> orderPage = orderService.getPaginatedOrders(null, PAGE_SIZE,
                    page, sort, sortDirection, isSearchByDate, request.getParameter("date"), isSearchByName,
                    request.getParameter("name"), request.getParameter("surname"), connection);
            ConnectionManager.close(connection);
            request.setAttribute("orderPage", orderPage);
        }catch (RuntimeException exception){
            ConnectionManager.close(connection);
            return "/WEB-INF/error.jsp";
        }
        return "/WEB-INF/admins/orders.jsp";
    }

}
