package ua.training.controller.commands;


import ua.training.model.entities.User;
import ua.training.model.services.UserService;

import javax.servlet.http.HttpServletRequest;

import static ua.training.model.utils.RegularExpressions.*;

public class RegistrationCommand implements Command{

    private UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if(request.getMethod().equals("GET")){
            System.out.println("GET");
            return "/common/registration.jsp";
        }
        return tryToSaveUser(request);
    }

    private String tryToSaveUser(HttpServletRequest request){
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        if( (name != null && name.matches(NAME_REGEX)) && (surname != null && surname.matches(SURNAME_REGEX)) &&
                (username != null && username.matches(NICK_REGEX)) && (phone != null && phone.matches(PHONE_REGEX))){
            User user = new User(name, surname, username, phone, password);
            System.out.println(user);
            userService.saveUser(user);
            System.out.println("User has been saved");
        }
        return "/common/registration.jsp";
    }

}
