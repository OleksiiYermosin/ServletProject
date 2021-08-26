package ua.training.model.dao.impl;

import ua.training.model.dao.TaxiClassDAO;
import ua.training.model.entities.TaxiClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCTaxiClassDAO implements TaxiClassDAO {

    private final Connection connection;

    public JDBCTaxiClassDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(TaxiClass entity) {
        return false;
    }

    @Override
    public TaxiClass findById(Long id) {
        return null;
    }

    @Override
    public Optional<Long> findTaxiClassIdByName(String name) {
        Optional<Long> result = Optional.empty();
        try(PreparedStatement ps = connection.prepareCall("SELECT id FROM taxi_classes WHERE name = ?")){
            ps.setString( 1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                result = Optional.of(rs.getLong("id"));
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public List<TaxiClass> findAll() {
        return null;
    }

    @Override
    public boolean update(TaxiClass entity) {
        return false;
    }

    @Override
    public void delete(Long id) {

    }

}
