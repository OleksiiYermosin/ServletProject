package ua.training.model.dao.mappers;

import ua.training.model.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User>{

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setBalance(rs.getBigDecimal("balance"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setPhone(rs.getString("phone"));
        user.setDiscount(rs.getBigDecimal("discount"));
        user.setRole(rs.getLong("role_id"));
        return user;
    }

}
