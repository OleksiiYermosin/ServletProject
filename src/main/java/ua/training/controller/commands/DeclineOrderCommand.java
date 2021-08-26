package ua.training.controller.commands;

import ua.training.model.entities.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeclineOrderCommand implements Command{

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
        orders.remove(index);
        if(orders.isEmpty()){
            request.getSession().removeAttribute("orders");
        }
        return "redirect:/user/order-taxi/view-new-order";
    }

}
