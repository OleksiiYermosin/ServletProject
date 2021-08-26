package ua.training.model.dao.impl;

import ua.training.model.dao.TaxiStatusDAO;
import ua.training.model.dao.mappers.TaxiStatusMapper;
import ua.training.model.entities.TaxiStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCTaxiStatusDAO implements TaxiStatusDAO {

    private final Connection connection;

    public JDBCTaxiStatusDAO(Connection connection) {

        this.connection = connection;
    }

    @Override
    public boolean create(TaxiStatus entity) {
        return false;
    }

    @Override
    public TaxiStatus findById(Long id) {
        return null;
    }

    @Override
    public Optional<TaxiStatus> findTaxiStatusByName(String name) {
        Optional<TaxiStatus> result = Optional.empty();
        try(PreparedStatement ps = connection.prepareCall("SELECT * FROM taxi_statuses WHERE name = ?")){
            ps.setString( 1, name);
            ResultSet rs = ps.executeQuery();
            TaxiStatusMapper mapper = new TaxiStatusMapper();
            if (rs.next()){
                result = Optional.of(mapper.extractFromResultSet(rs));
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public List<TaxiStatus> findAll() {
        return null;
    }

    @Override
    public boolean update(TaxiStatus entity) {
        return false;
    }

    @Override
    public void delete(Long id) {

    }

}
