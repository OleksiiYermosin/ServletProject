package ua.training.controller.commands;

import ua.training.model.entities.Order;
import ua.training.model.services.OrderService;

import javax.servlet.http.HttpServletRequest;
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
        List<Order> orders = (List<Order>) request.getSession().getAttribute("orders");
        switch (orderService.saveOrder(orders.get(index))){
            case ORDER_SAVED:
                request.getSession().removeAttribute("orders");
                return "redirect:/user/order-taxi/view-new-order";
            case NOT_ENOUGH_MONEY:
                return "redirect:/user/recharge-balance";
            case DATA_DEPRECATED:
                request.getSession().removeAttribute("orders");
                request.setAttribute("orderDeprecated", true);
                return "redirect:/user/order-taxi/view-new-order";
        }
        return "redirect:/user/order-taxi/view-new-order";
    }
}
