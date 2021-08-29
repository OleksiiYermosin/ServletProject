package ua.training.model.dao.impl;

import ua.training.model.dao.TaxiDAO;
import ua.training.model.dao.mappers.EagerTaxiMapper;
import ua.training.model.entities.Taxi;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class JDBCTaxiDAO implements TaxiDAO {

    private final Connection connection;

    public JDBCTaxiDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Taxi entity) {
        return false;
    }

    @Override
    public Taxi findById(Long id) {
        Taxi result = null;
        try(PreparedStatement statement = connection.prepareCall("SELECT taxi.id, taxi.info, taxi.capacity, \n" +
                "taxi_classes.id AS class_id, taxi_classes.name AS class_name, taxi_classes.multiplier AS class_multiplier,\n" +
                "taxi_statuses.id AS status_id, taxi_statuses.name AS status_name,\n" +
                "drivers.id AS driver_id, drivers.name AS driver_name, drivers.surname AS driver_surname, drivers.phone AS driver_phone\n" +
                "FROM taxi\n" +
                "JOIN taxi_statuses ON taxi.taxi_status_id=taxi_statuses.id AND taxi.id=?\n" +
                "JOIN taxi_classes ON taxi.taxi_class_id=taxi_classes.id\n" +
                "JOIN drivers ON taxi.driver_id=drivers.id;")){
            statement.setLong( 1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                result = new EagerTaxiMapper().extractFromResultSet(rs);
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return result;
    }

    public Integer findCapacitySum(){

        Integer result = null;
        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT SUM(capacity) FROM taxi WHERE " +
                    "taxi_status_id=(SELECT id FROM taxi_statuses WHERE name='AVAILABLE');");
            if (rs.next()){
                result = rs.getInt("sum");
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public Set<Taxi> findAllTaxiByStatus(String taxiStatus){
        Set<Taxi> result = new HashSet<>();
        try(PreparedStatement ps = connection.prepareCall("SELECT taxi.id, taxi.info, taxi.capacity, \n" +
                "taxi_classes.id AS class_id, taxi_classes.name AS class_name, taxi_classes.multiplier AS class_multiplier,\n" +
                "taxi_statuses.id AS status_id, taxi_statuses.name AS status_name,\n" +
                "drivers.id AS driver_id, drivers.name AS driver_name, drivers.surname AS driver_surname, drivers.phone AS driver_phone\n" +
                "FROM taxi\n" +
                "JOIN taxi_statuses ON taxi.taxi_status_id=taxi_statuses.id\n" +
                "AND taxi_status_id=(SELECT id FROM taxi_statuses WHERE name=?) \n" +
                "JOIN taxi_classes ON taxi.taxi_class_id=taxi_classes.id\n" +
                "JOIN drivers ON taxi.driver_id=drivers.id\n" +
                "ORDER BY taxi.capacity DESC;")){
            ps.setString( 1, taxiStatus);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                result.add(new EagerTaxiMapper().extractFromResultSet(rs));
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public Set<Taxi> findTaxiOfAnotherClass(String taxiStatus, int capacity){
        Set<Taxi> result = new HashSet<>();
        try(PreparedStatement ps = connection.prepareCall("SELECT taxi.id, taxi.info, taxi.capacity, \n" +
                "taxi_classes.id AS class_id, taxi_classes.name AS class_name, taxi_classes.multiplier AS class_multiplier,\n" +
                "taxi_statuses.id AS status_id, taxi_statuses.name AS status_name,\n" +
                "drivers.id AS driver_id, drivers.name AS driver_name, drivers.surname AS driver_surname, drivers.phone AS driver_phone\n" +
                "FROM taxi\n" +
                "JOIN taxi_statuses ON taxi.taxi_status_id=taxi_statuses.id\n" +
                "AND taxi_status_id=(SELECT id FROM taxi_statuses WHERE name=?) AND capacity>=?\n" +
                "JOIN taxi_classes ON taxi.taxi_class_id=taxi_classes.id\n" +
                "JOIN drivers ON taxi.driver_id=drivers.id\n" +
                "ORDER BY taxi_classes.id, taxi.capacity;")){
            ps.setString( 1, taxiStatus);
            ps.setInt( 2, capacity);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                result.add(new EagerTaxiMapper().extractFromResultSet(rs));
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public Set<Taxi> findByOrderId(Long orderId){
        Set<Taxi> result = new HashSet<>();
        try(PreparedStatement ps = connection.prepareCall("SELECT taxi.id, taxi.info, taxi.capacity, \n" +
                "taxi_classes.id AS class_id, taxi_classes.name AS class_name, taxi_classes.multiplier AS class_multiplier,\n" +
                "taxi_statuses.id AS status_id, taxi_statuses.name AS status_name,\n" +
                "drivers.id AS driver_id, drivers.name AS driver_name, drivers.surname AS driver_surname, drivers.phone AS driver_phone\n" +
                "FROM taxi\n" +
                "JOIN taxi_statuses ON taxi.taxi_status_id=taxi_statuses.id AND taxi.id IN (SELECT taxi_id FROM order_taxi WHERE order_taxi.order_id=?)\n" +
                "JOIN taxi_classes ON taxi.taxi_class_id=taxi_classes.id\n" +
                "JOIN drivers ON taxi.driver_id=drivers.id;")){
            ps.setLong( 1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                result.add(new EagerTaxiMapper().extractFromResultSet(rs));
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public Optional<Taxi> findSuitableCab(String taxiClassString, String taxiStatusString, int capacity) {
        Optional<Taxi> result = Optional.empty();
        try(PreparedStatement ps = connection.prepareCall("SELECT taxi.id, taxi.info, taxi.capacity, \n" +
                "taxi_classes.id AS class_id, taxi_classes.name AS class_name, taxi_classes.multiplier AS class_multiplier,\n" +
                "taxi_statuses.id AS status_id, taxi_statuses.name AS status_name,\n" +
                "drivers.id AS driver_id, drivers.name AS driver_name, drivers.surname AS driver_surname, drivers.phone AS driver_phone\n" +
                "FROM taxi\n" +
                "JOIN taxi_statuses ON taxi.taxi_status_id=taxi_statuses.id AND taxi_class_id=(SELECT id FROM taxi_classes WHERE name=?)\n" +
                "AND taxi_status_id=(SELECT id FROM taxi_statuses WHERE name=?) AND capacity>=?\n" +
                "JOIN taxi_classes ON taxi.taxi_class_id=taxi_classes.id\n" +
                "JOIN drivers ON taxi.driver_id=drivers.id\n" +
                "ORDER BY capacity LIMIT 1;")){
            ps.setString( 1, taxiClassString);
            ps.setString( 2, taxiStatusString);
            ps.setInt( 3, capacity);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                result = Optional.of(new EagerTaxiMapper().extractFromResultSet(rs));
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public List<Taxi> findAll() {
        return null;
    }

    @Override
    public boolean update(Taxi entity) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("UPDATE taxi SET info=?, taxi_class_id=?, capacity=?, " +
                    "taxi_status_id=?, driver_id=? WHERE id = ?;");
            preparedStatement.setString(1, entity.getInfo());
            preparedStatement.setLong(2, entity.getTaxiClass().getId());
            preparedStatement.setInt(3, entity.getCapacity());
            preparedStatement.setLong(4, entity.getTaxiStatus().getId());
            preparedStatement.setLong(5, entity.getDriver().getId());
            preparedStatement.setLong(6, entity.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException exception){
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void delete(Long id) {

    }

}
