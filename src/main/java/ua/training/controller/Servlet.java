package ua.training.controller;

import ua.training.controller.commands.*;
import ua.training.model.services.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {

    private final Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){
        servletConfig.getServletContext().setAttribute("loggedUsers", new HashSet<String>());
        UserService userService = new UserService();
        TaxiService taxiService = new TaxiService();
        QueryService queryService = new QueryService();
        OrderService orderService = new OrderService(new CalculationService(), userService, taxiService, queryService);
        commands.put("login", new LoginCommand(userService));
        commands.put("registration", new RegistrationCommand(userService));
        commands.put("logout", new LogOutCommand());
        commands.put("user", new LoadDefaultPageCommand(userService));
        commands.put("user/recharge-balance", new RechargeBalanceCommand(userService));
        commands.put("user/order-taxi", new BookTaxiCommand(taxiService, orderService));
        commands.put("user/order-taxi/view-new-order", new ViewOrdersCommand());
        commands.put("user/order-taxi/new-order", new ApproveOrderCommand(orderService));
        commands.put("user/order-taxi/cancel-order", new DeclineOrderCommand());
        commands.put("user/orders", new ViewUserOrdersCommand(orderService));
        commands.put("user/orders/finish", new ProcessOrderCommand(orderService));
        commands.put("user/orders/cancel", new ProcessOrderCommand(orderService));
        commands.put("admin", new LoadDefaultPageCommand(userService));
        commands.put("admin/orders", new ViewAllOrders(orderService));
        commands.put("admin/orders/finish", new ProcessOrderCommand(orderService));
        commands.put("index", new LoadDefaultPageCommand(userService));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/service/" , "");
        Command command = commands.getOrDefault(path, (r) -> "redirect:/index");
        String page = command.execute(request);
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", "/service"));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

}
