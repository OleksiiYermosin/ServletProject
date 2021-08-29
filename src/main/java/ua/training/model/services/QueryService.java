package ua.training.model.services;

import ua.training.model.entities.User;


public class QueryService {

    public String prepareUserFilterQuery(User user, boolean isSearchByName, String name, String surname) {
        if(user!=null){
            return "=" + user.getId();
        }
        if (!isSearchByName || (name == null || name.isEmpty()) || (surname == null || surname.isEmpty())) {
            return "IS NOT NULL";
        }
        return "=(SELECT id FROM users WHERE name='" + name + "' AND surname='" + surname + "')";
    }

    public String prepareDateFilterQuery(boolean isSearchByDate, String date) {
        if (!isSearchByDate || (date == null || date.isEmpty())) {
            return "IS NOT NULL";
        }
        return "='"+date+"'";
    }

}
