package ua.training.controller.commands;

import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.User;
import ua.training.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static ua.training.model.utils.RegularExpressions.*;

public class RechargeBalanceCommand implements Command {

    private final UserService userService;

    public RechargeBalanceCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String sumString = request.getParameter("value");
        if (request.getMethod().equals("GET")) {
            return "/WEB-INF/users/addmoney.jsp";
        }
        if (sumString == null || !sumString.matches(VALUE_REGEX)) {
            request.setAttribute("validationError", true);
            return "/WEB-INF/users/addmoney.jsp";
        }
        BigDecimal sum = new BigDecimal(sumString);
        userService.updateUserBalance(((User) request.getSession().getAttribute("user")).getId(), sum, ConnectionManager.getConnection(), true);
        return "redirect:/user";
    }

}
