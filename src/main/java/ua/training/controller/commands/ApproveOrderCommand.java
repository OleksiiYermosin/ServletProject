package ua.training.controller.commands;

import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.Order;
import ua.training.model.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

import static ua.training.model.utils.MyConstants.*;

public class ApproveOrderCommand implements Command{

    private final OrderService orderService;

    public ApproveOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request) {
        int index;
        try{
            index = Integer.parseInt(request.getParameter("index"));
        }catch (NullPointerException | NumberFormatException ex){
            return "redirect:/user/order-taxi/view-new-order";
        }
        Connection connection = ConnectionManager.getConnection();
        List<Order> orders = (List<Order>) request.getSession().getAttribute("orders");
        int status = orderService.saveOrder(orders.get(index), connection);
        ConnectionManager.close(connection);
        switch (status){
            case ORDER_SAVED:
                request.getSession().removeAttribute("orders");
                return "redirect:/user/orders";
            case NOT_ENOUGH_MONEY:
                return "redirect:/user/recharge-balance";
            case DATA_DEPRECATED:
                request.getSession().removeAttribute("orders");
                request.setAttribute("orderDeprecated", true);
                return "redirect:/user/order-taxi/view-new-order";
            default:
                return "WEB-INF/error.jsp";
        }
    }
}
