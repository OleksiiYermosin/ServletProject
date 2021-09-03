package ua.training.controller.commands;


import ua.training.model.dao.impl.ConnectionManager;
import ua.training.model.entities.User;
import ua.training.model.services.UserService;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;

import static ua.training.model.utils.RegularExpressions.*;

public class RegistrationCommand implements Command {

    private final UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return "/common/registration.jsp";
        }
        return tryToSaveUser(request);
    }

    private String tryToSaveUser(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        User user = null;
        if ((name != null && name.matches(NAME_REGEX)) && (surname != null && surname.matches(SURNAME_REGEX)) &&
                (username != null && username.matches(NICK_REGEX)) && (phone != null && phone.matches(PHONE_REGEX))) {
            user = new User(name, surname, username, phone, password);
        } else {
            request.setAttribute("isValidationFailed", true);
            return "/common/registration.jsp";
        }
        if (userService.saveUser(user, ConnectionManager.getConnection(), true)) {
            return "redirect:/service/login";
        }
        throw new RuntimeException("username.duplicate.message");

    }

}
