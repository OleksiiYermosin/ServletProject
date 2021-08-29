package ua.training.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.model.dao.mappers.UserMapper;
import ua.training.model.entities.User;
import ua.training.model.exceptions.NotEnoughMoneyException;
import ua.training.model.services.UserService;

import java.math.BigDecimal;
import java.sql.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private final UserService userService = new UserService();

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    CallableStatement callableStatement;

    @Mock
    ResultSet resultSet;

    @Test
    public void testUserSaving() throws SQLException {
        when(connection.prepareStatement("INSERT INTO users(username, password, name, surname, phone, role_id)" +
                " VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        User user = new User("name", "surname", "username" , "phone", "pass");
        Assert.assertTrue(userService.saveUser(user, connection, true));
    }

    @Test
    public void testFindingUserByUsername() throws SQLException {
        when(connection.prepareCall("SELECT * FROM users WHERE username = ?")).thenReturn(callableStatement);
        when(callableStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("name")).thenReturn("name");
        when(resultSet.getString("surname")).thenReturn("surname");
        when(resultSet.getString("username")).thenReturn("username");
        when(resultSet.getString("password")).thenReturn("pass");
        when(resultSet.getString("phone")).thenReturn("phone");
        User user = new User("name", "surname", "username" , "phone", "pass");
        user.setId(0L);
        user.setRole(0L);
        System.out.println(userService.findUserByUsername("username", connection, true));
        Assert.assertEquals(user, userService.findUserByUsername("username", connection, true));
    }

    @Test
    public void testFindingUserById() throws SQLException {
        when(connection.prepareCall("SELECT * FROM users WHERE id = ?")).thenReturn(callableStatement);
        when(callableStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("name");
        when(resultSet.getString("surname")).thenReturn("surname");
        when(resultSet.getString("username")).thenReturn("username");
        when(resultSet.getString("password")).thenReturn("pass");
        when(resultSet.getString("phone")).thenReturn("phone");
        User user = new User("name", "surname", "username" , "phone", "pass");
        user.setId(1L);
        user.setRole(0L);
        Assert.assertEquals(user, userService.findUserById(1L, connection, true));
    }

    @Test
    public void testUserBalanceUpdate() throws SQLException {
        when(connection.prepareCall("SELECT * FROM users WHERE id = ?")).thenReturn(callableStatement);
        when(connection.prepareStatement("UPDATE users SET username = ?, password = ?, name = ?, surname = ?," +
                " phone = ?, balance = ?, discount = ? WHERE id = ?")).thenReturn(preparedStatement);
        when(callableStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getBigDecimal("balance")).thenReturn(BigDecimal.valueOf(50));
        Assert.assertTrue(userService.updateUserBalance(1, BigDecimal.valueOf(100), connection, true));
    }

    @Test
    public void testDiscountRise() {
        try {
            when(connection.prepareCall("SELECT * FROM users WHERE id = ?")).thenReturn(callableStatement);
            when(connection.prepareStatement("UPDATE users SET username = ?, password = ?, name = ?, surname = ?," +
                    " phone = ?, balance = ?, discount = ? WHERE id = ?")).thenReturn(preparedStatement);
            when(callableStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getBigDecimal("discount")).thenReturn(BigDecimal.valueOf(29.9));
            Assert.assertTrue(userService.increaseDiscount(1L, connection, true));
        }catch (Exception exception){
            Assert.fail();
        }
    }

    @Test
    public void testDiscountNotRise() {
        try {
            when(connection.prepareCall("SELECT * FROM users WHERE id = ?")).thenReturn(callableStatement);
            when(connection.prepareStatement("UPDATE users SET username = ?, password = ?, name = ?, surname = ?," +
                    " phone = ?, balance = ?, discount = ? WHERE id = ?")).thenReturn(preparedStatement);
            when(callableStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getBigDecimal("discount")).thenReturn(BigDecimal.valueOf(30.0));
            Assert.assertFalse(userService.increaseDiscount(1L, connection, true));
        }catch (Exception exception){
            Assert.fail();
        }
    }

    @Test
    public void testSuccessfulGettingMoneyFromUser() {
        try {
            when(connection.prepareCall("SELECT * FROM users WHERE id = ?")).thenReturn(callableStatement);
            when(connection.prepareStatement("UPDATE users SET username = ?, password = ?, name = ?, surname = ?," +
                    " phone = ?, balance = ?, discount = ? WHERE id = ?")).thenReturn(preparedStatement);
            when(callableStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getBigDecimal("balance")).thenReturn(BigDecimal.valueOf(30.0));
            Assert.assertTrue(userService.getMoneyFromUser(BigDecimal.valueOf(30.0), 1L, connection, true));
        }catch (Exception exception){
            Assert.fail();
        }
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testFailureGettingMoneyFromUser() {
        try {
            when(connection.prepareCall("SELECT * FROM users WHERE id = ?")).thenReturn(callableStatement);
            when(connection.prepareStatement("UPDATE users SET username = ?, password = ?, name = ?, surname = ?," +
                    " phone = ?, balance = ?, discount = ? WHERE id = ?")).thenReturn(preparedStatement);
            when(callableStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getBigDecimal("balance")).thenReturn(BigDecimal.valueOf(25.0));
            userService.getMoneyFromUser(BigDecimal.valueOf(30.0), 1L, connection, true);
        }catch (SQLException exception){
            Assert.fail();
        }
    }
}
