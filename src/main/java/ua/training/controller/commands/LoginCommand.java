package ua.training.controller.commands;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        request.getSession().removeAttribute("loginError");
        if (username == null || username.equals("") || pass == null || pass.equals("")) {
            return "/common/login.jsp";
        }
        if(!(username.equals("user")&&pass.equals("asdasd"))){
            request.getSession().setAttribute("loginError", true);
            return "/common/login.jsp";
        }
        System.out.println(username + " " + pass);
        //todo: check login with DB

        if (CommandUtility.checkUserIsLogged(request, username)) {
            return "/WEB-INF/error.jsp";
        }

        /*if (name.equals("Admin")){
            CommandUtility.setUserRole(request, User.ROLE.ADMIN, name);
            return "/WEB-INF/admin/adminbasis.jsp";
        } else if(name.equals("User")) {
            CommandUtility.setUserRole(request, User.ROLE.USER, name);
            return "/WEB-INF/user/userbasis.jsp";
        } else {
            CommandUtility.setUserRole(request, User.ROLE.UNKNOWN, name);
            return "/login.jsp";
        }*/
        return "/common/login.jsp";
    }

}
