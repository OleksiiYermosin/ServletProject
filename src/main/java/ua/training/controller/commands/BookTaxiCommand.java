package ua.training.controller.commands;

import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.Order;
import ua.training.model.entities.Taxi;
import ua.training.model.entities.User;
import ua.training.model.services.OrderService;
import ua.training.model.services.TaxiService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class BookTaxiCommand implements Command {


    private final TaxiService taxiService;

    private final OrderService orderService;

    public BookTaxiCommand(TaxiService taxiService, OrderService orderService) {
        this.taxiService = taxiService;
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return "/WEB-INF/users/ordertaxi.jsp";
        }
        String initialAddress = request.getParameter("first-street");
        String finishAddress = request.getParameter("second-street");
        if (initialAddress.equals(finishAddress)) {
            request.setAttribute("validationError", true);
            return "/WEB-INF/users/ordertaxi.jsp";
        }
        Set<Taxi> taxis = new HashSet<>();
        String taxiClass = request.getParameter("taxi-class");
        int capacity = Integer.parseInt(request.getParameter("human-capacity"));
        boolean multipleOrders = false;
        taxiService.findSuitableCar(taxiClass, "AVAILABLE", capacity, ConnectionManager.getConnection(), true).ifPresent(taxis::add);
        if (taxis.isEmpty()) {
            taxis = taxiService.findTaxiOfAnotherClass("AVAILABLE", capacity, ConnectionManager.getConnection(), true);
            multipleOrders = !taxis.isEmpty();
        }
        if (taxis.isEmpty()) {
            taxis = taxiService.findSeveralTaxi(capacity, ConnectionManager.getConnection(), true);
        }
        List<Order> orders = new ArrayList<>();
        if (!taxis.isEmpty()) {
            User user = (User) request.getSession().getAttribute("user");
            if (!multipleOrders) {
                orders.add(orderService.prepareOrder(initialAddress, finishAddress, capacity, taxis, user));
            } else {
                taxis.forEach(t -> orders.add(orderService.prepareOrder(initialAddress, finishAddress, capacity, Collections.singleton(t), user)));
            }
        }
        request.getSession().setAttribute("orders", orders);
        return "redirect:/user/order-taxi/view-new-order";
    }

}
